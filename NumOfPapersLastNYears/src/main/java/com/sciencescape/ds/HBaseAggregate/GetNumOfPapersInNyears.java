package com.sciencescape.ds.HBaseAggregate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.ResourceBundle;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sciencescape.ds.Exception.UnexpectedArgumentsException;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Provide mapper and reducer for retrieving all papers for a author.
 *
 * @author Akshay
 */
public final class GetNumOfPapersInNyears {

	/**
	 * Enum for type of output target.
	 *
	 * @author akshay
	 */
	private static enum OutputType {
		/**!< output target is HBase */
		HBASE,
		/**!< output target is FileSystem */
		FILESYSTEM
	}

	/**!< input HBase table having de-normalized data */
	private static String inputHBaseTable;
	/**!< output type for summary target */
	private static OutputType outputType;
	/**!< output HBase table to contain summary data */
	private static String outputHBaseTable;
	/**!< output Filesystem file to contain summary data */
	private static String outputFilesystemFile;

	/**!< Logger object */
	private static Logger logger = LoggerFactory.getLogger(GetNumOfPapersInNyears.class);
	/**!< ResourceBundle object to handle user messages */
	private static ResourceBundle messages = null;

	/**
	 * Private constructor to disable instantiation.
	 */
	private GetNumOfPapersInNyears() { }

	/**
	 * Method to set user messages configurations.
	 */
	public static void configureUserMessages() {
		Locale currentLocale = new Locale(
				Constants.UserMessages.DEFAULT_LANGUAGE,
				Constants.UserMessages.DEFAULT_COUNTRY);
		messages = ResourceBundle.getBundle(Constants.UserMessages.RB_BASE_NAME,
				currentLocale);
	}

	/**
	 * Class providing mapper method for HBase source.
	 */
	static class HBaseMapper extends TableMapper<ImmutableBytesWritable,
	ImmutableBytesWritable> {

		/**!< Total number of records processed by this class */
		private int numRecords = 0;

		/**
		 * {@link HBaseMapper} map function.
		 *
		 * @param row paper-id of the paper
		 * @param values set of all columns in Scan object
		 * @param context MapContext for the job
		 * @throws IOException if not able to write to context
		 * @throws InterruptedException if interrupted while writing to context
		 */
		@Override
		public void map(final ImmutableBytesWritable row, final Result values,
				final Context context)
						throws IOException, InterruptedException {
			// get the publication year of the paper
			ImmutableBytesWritable paperYear =
					new ImmutableBytesWritable(values.getValue(
							Bytes.toBytes(Constants.HBaseDB.DATE_COLUMN_FAMILY),
							Bytes.toBytes(Constants.HBaseDB.YEAR_COLUMN)));

			/*
			 *  get the authors of this paper from result object
			 *  and write to context with year of publication of paper
			 */
			NavigableMap<byte[], byte[]> authorsMap =
					values.getFamilyMap(Bytes.toBytes(
							Constants.HBaseDB.AUTHORS_COLUMN_FAMILY));
			for (Entry<byte[], byte[]> entry : authorsMap.entrySet()) {
				ImmutableBytesWritable author = new
						ImmutableBytesWritable(entry.getKey());
				context.write(author, paperYear);
			}

			// update number of records processed
			numRecords++;
			// update status once in a while
			if ((numRecords % Constants.MapReduce.STATUS_UPDATE_FREQUENCY)
					== 0) {
				String statusMessage = String.format(
						Constants.MapReduce.MAPPER_STATUS_MSG_FORMAT,
						numRecords);
				context.setStatus(statusMessage);
				logger.info(statusMessage);
			}
		}
	}

	/**
	 * Class providing reducer method for HBase destination.
	 */
	static class HBaseReducer
	extends TableReducer<ImmutableBytesWritable, ImmutableBytesWritable,
	ImmutableBytesWritable> {
		/**
		 * {@link HBaseReducer} reduce function.
		 *
		 * @param key author-id of the author
		 * @param values list of IDs of papers by this author
		 * @param context ReduceContext for the job
		 * @throws IOException if not able to write to context
		 * @throws InterruptedException if interrupted while writing to context
		 */
		@Override
		public void reduce(final ImmutableBytesWritable key,
				final Iterable<ImmutableBytesWritable> values,
				final Context context)
						throws IOException, InterruptedException {
			List<Integer> publYearList = new ArrayList<Integer>();
			Iterator<ImmutableBytesWritable> valIter = values.iterator();
			while (valIter.hasNext()) {
				publYearList.add(Bytes.toInt(valIter.next().get()));
			}
			int minYear = Collections.min(publYearList);
			int maxYear = Collections.max(publYearList);
			// write minimum and maximum year
			Put put = new Put(key.get());
			put.add(Bytes.toBytes(Constants.HBaseDB.YEARS_COLUMN_FAMILY),
					Bytes.toBytes(Constants.HBaseDB.MIN_YEAR_COLUMN_QUALIFIER),
					Bytes.toBytes(minYear));
			put.add(Bytes.toBytes(Constants.HBaseDB.YEARS_COLUMN_FAMILY),
					Bytes.toBytes(Constants.HBaseDB.MAX_YEAR_COLUMN_QUALIFIER),
					Bytes.toBytes(maxYear));
			context.write(key, put);
		}
	}

	/**
	 * Class providing reducer method for output on FS.
	 */
	static class FSReducer extends Reducer<ImmutableBytesWritable,
	ImmutableBytesWritable, Text, Text> {
		/**
		 * {@link Reduce} reduce function.
		 *
		 * @param key the input key
		 * @param values the list of values to be reduced
		 * @param context ReduceContext for the job
		 * @throws IOException if not able to submit results to output
		 * @throws InterruptedException if interrupted while writing to context
		 */
		@Override
		public void reduce(final ImmutableBytesWritable key,
				final Iterable<ImmutableBytesWritable> values,
				final Context context)
					throws IOException, InterruptedException {
			List<Integer> publYearList = new ArrayList<Integer>();
			Iterator<ImmutableBytesWritable> valIter = values.iterator();
			while (valIter.hasNext()) {
				publYearList.add(Bytes.toInt(valIter.next().get()));
			}
			int minYear = Collections.min(publYearList);
			int maxYear = Collections.max(publYearList);

			StringBuilder activeYears = new StringBuilder(
					Constants.ReduceAggregationFormat.LIST_PREFIX);
			activeYears.append(minYear);
			activeYears.append(Constants.ReduceAggregationFormat.DELIMITER);
			activeYears.append(maxYear);
			context.write(new Text(key.get()),
					new Text(Bytes.toBytes(activeYears.toString())));
		}
	}

	/**
	 * Function to set the mapper and reducer for output on FileSystem.
	 *
	 * @param job MapReduce job object
	 * @param scan HBase scan object for scan operation
	 * @throws IOException when setting mapper function
	 */
	static void runFSReducer(final Job job, final Scan scan)
			throws IOException {
		logger.info(messages.getString("running_fs_reducer"));
		// map-phase
		TableMapReduceUtil.initTableMapperJob(inputHBaseTable, scan,
				HBaseMapper.class, ImmutableBytesWritable.class,
				ImmutableBytesWritable.class, job);

		// reduce-phase, output to local FS
		job.setReducerClass(FSReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(outputFilesystemFile));
	}

	/**
	 * Function to set the mapper and reducer for output on FileSystem.
	 *
	 * @param job MapReduce job object
	 * @param scan HBase scan object for scan operation
	 * @throws IOException when setting mapper function
	 */
	static void runHBaseReducer(final Job job, final Scan scan)
			throws IOException {
		logger.info(messages.getString("running_habse_reducer"));
		// map-phase
		TableMapReduceUtil.initTableMapperJob(inputHBaseTable, scan,
				HBaseMapper.class, ImmutableBytesWritable.class,
				ImmutableBytesWritable.class, job);

		// reduce-phase, output to HBase
		TableMapReduceUtil.initTableReducerJob(outputHBaseTable,
				HBaseReducer.class, job);
	}

	/**
	 * Method to check provided command line arguments.
	 *
	 * @param args list of command line arguments
	 * @throws UnexpectedArgumentsException if arguments are unexpected
	 */
	static void retrieveArguments(final String[] args)
			throws UnexpectedArgumentsException {
		/* specify expected command line arguments */
		ArgumentParser parser = ArgumentParsers
				.newArgumentParser(Constants.CLA.PROGRAM_NAME)
				.defaultHelp(true)
				.description(Constants.CLA.PROGRAM_DESCRIPTION);
		// input hbase table
		parser.addArgument(Constants.CLA.INPUT_TABLE_OPT_SHORT,
				Constants.CLA.INPUT_TABLE_OPT_LONG)
				.help(Constants.CLA.INPUT_TABLE_OPT_DESCRIPTION);
		// target output
		parser.addArgument(Constants.CLA.OUTPUT_OPT_SHORT,
				Constants.CLA.OUTPUT_OPT_LONG)
				.choices(Constants.CLA.OUTPUT_OPT_HBASE_CHOICE,
						Constants.CLA.OUTPUT_OPT_FS_CHOICE)
						.setDefault(Constants.CLA.OUTPUT_OPT_FS_CHOICE)
						.help(Constants.CLA.OUTPUT_OPT_DESCRIPTION);
		// output hbase table
		parser.addArgument(Constants.CLA.OUTPUT_TABLE_OPT_SHORT,
				Constants.CLA.OUTPUT_TABLE_OPT_LONG)
				.help(Constants.CLA.OUTPUT_TABLE_OPT_DESCRIPTION);
		// output filesystem file
		parser.addArgument(Constants.CLA.OUTPUT_FILE_OPT_SHORT,
				Constants.CLA.OUTPUT_FILE_OPT_LONG)
				.help(Constants.CLA.OUTPUT_FILE_OPT_DESCRIPTION);

		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			System.exit(1);
		}

		/* retrieve parameters and set class variable */
		// set hbase input table
		inputHBaseTable = ns.getString(Constants.CLA.INPUT_TABLE_ARG);
		logger.info(messages.getString("in_hbase_table"), inputHBaseTable);
		// set output type
		String opType = ns.getString(Constants.CLA.OUTPUT_FORMAT_ARG);
		if (opType == null) {
			throw (new UnexpectedArgumentsException(
					messages.getString("output_frmt_null")));
		}
		if (opType.compareTo(Constants.CLA.OUTPUT_OPT_HBASE_CHOICE) == 0) {
			outputType = OutputType.HBASE;
		} else if (opType.compareTo(Constants.CLA.OUTPUT_OPT_FS_CHOICE) == 0) {
			outputType = OutputType.FILESYSTEM;
		} else {
			throw (new UnexpectedArgumentsException(
					messages.getString("output_frmt_invalid"), opType));
		}
		// set output table name
		outputHBaseTable = ns.getString(Constants.CLA.OUTPUT_TABLE_ARG);
		// set output file name
		outputFilesystemFile = ns.getString(Constants.CLA.OUTPUT_FILE_ARG);

		/* check for validity of provided arguments */
		// check if outputHBaseTable is specified if output is HBase
		if (outputType == OutputType.HBASE) {
			if (outputHBaseTable == null) {
				throw (new UnexpectedArgumentsException(
						messages.getString("provide_op_table")));
			}
		}
		// check if outputFilesystemFile is specified if output is filesystem
		if (outputType == OutputType.FILESYSTEM) {
			if (outputFilesystemFile == null) {
				throw (new UnexpectedArgumentsException(
						messages.getString("provide_op_file")));
			}
		}
	}

	/**
	 * Main method to start map-reduce job.
	 *
	 * @param args command line arguments (none needed as of now)
	 */
	public static void main(final String[] args) {
		// set the user-messages
		configureUserMessages();

		// process command line arguments
		try {
			retrieveArguments(args);
		} catch (UnexpectedArgumentsException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		// create configuration for job and job
		Configuration conf = HBaseConfiguration.create();
		Job job = null;
		try {
			job = Job.getInstance(conf, Constants.CLA.PROGRAM_NAME);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		// create scan object
		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes("1"));
		scan.setStopRow(Bytes.toBytes("15"));
		scan.addFamily(Bytes.toBytes(Constants.HBaseDB.AUTHORS_COLUMN_FAMILY));
		scan.addColumn(Bytes.toBytes(Constants.HBaseDB.DATE_COLUMN_FAMILY),
				Bytes.toBytes(Constants.HBaseDB.YEAR_COLUMN));
		// set the class of the job
		job.setJarByClass(GetNumOfPapersInNyears.class);
		// choose the right output target
		switch (outputType) {
		case HBASE:
			try {
				runHBaseReducer(job, scan);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			break;
		case FILESYSTEM:
			try {
				runFSReducer(job, scan);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			break;
		default:
			// should never come here as case is handled while reading arguments
			System.err.format(messages.getString("output_frmt_invalid"),
					outputType);
			System.exit(1);
		}
		// run the job
		try {
			System.exit(job.waitForCompletion(true) ? 0 : 1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
