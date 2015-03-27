package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.DataTransferConstants;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;

/**
 * @class {@link DataTransfer} DataTransfer.java
 * @brief entry point to initiate data-transfer
 * @author akshay
 *
 * Class containing method to transfer data from
 * MySQL to HBase.
 */
public final class DataTransfer {

	/**
	 * private constructor to avoid initialization of the class.
	 */
	private DataTransfer() {
	}

	/**
	 * Method to check provided command line arguments.
	 *
	 * @param args list of command line arguments
	 * @throws UnexpectedArgumentsException if arguments are unexpected
	 */
	static void retrieveArguments(final String[] args)
			throws IllegalArgumentException {
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
		// number of years (to find papers in last 'n' years)
		parser.addArgument(Constants.CLA.NUM_OF_YEARS_OPT_SHORT,
				Constants.CLA.NUM_OF_YEARS_OPT_LONG)
				.help(Constants.CLA.NUM_OF_YEARS_OPT_DESCRIPTION);

		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			parser.handleError(e);
			logger.error(e.getMessage());
			System.exit(1);
		}

		/* give usage and exit if no parameter is provided */
		if (args.length == 0) {
			System.err.println(parser.formatUsage());
			System.exit(1);
		}

		/* retrieve parameters and set class variable */
		// set hbase input table
		inputHBaseTable = ns.getString(Constants.CLA.INPUT_TABLE_ARG);
		if (inputHBaseTable == null) {
			throw (new UnexpectedArgumentsException(
					messages.getString("input_table_null")));
		}
		logger.info(messages.getString("in_hbase_table"), inputHBaseTable);
		// set output type (defaults to FS, so cannot be null)
		String opType = ns.getString(Constants.CLA.OUTPUT_FORMAT_ARG);
		logger.info(messages.getString("output_frmt_type"), opType);
		// set output type option
		if (opType.compareTo(Constants.CLA.OUTPUT_OPT_HBASE_CHOICE) == 0) {
			outputType = OutputType.HBASE;
		} else if (opType.compareTo(Constants.CLA.OUTPUT_OPT_FS_CHOICE) == 0) {
			outputType = OutputType.FILESYSTEM;
		} else {
			throw (new UnexpectedArgumentsException(
					messages.getString("output_frmt_invalid"), opType));
		}
		// set the numOfYears
		//numOfYears = ns.getInt(C)


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
	 * @brief function to denormalize and transfer data to Hbase
	 * @param args command line arguments
	 *
	 * Main function to denormalize and transfer data to Hbase
	 * from MySQL.
	 */
	public static void main(final String[] args) {
		/* get publication year */
		if (args.length != 1) {
			System.err.println("Please provide publication year of papers to be imported");
			System.exit(1);
		}
		int publicationYear = Integer.parseInt(args[0]);
		/*
		 * create necessary objects
		 */
		// Create executor service
		ExecutorService executorService = Executors.newFixedThreadPool(
				DataTransferConstants.DataTransfer.THREAD_POOL_SIZE);
		// Get a blocking queue
		BlockingQueue<DenormalizedFields> queue =
				new ArrayBlockingQueue<DenormalizedFields>(
						DataTransferConstants.DataTransfer.BLOCKING_QUEUE_SIZE);
		// Create connection to CoreDB
		MySQLHandler my = null;
		try {
			my = new MySQLHandler(
					CoreDBConstants.DBServer.DEV_DB_SLAVE_SERVER,
					CoreDBConstants.DBServer.DB_PORT,
					CoreDBConstants.DBServer.DEV_DB_USER,
					CoreDBConstants.DBServer.DEV_DB_PASSWORD,
					CoreDBConstants.DBServer.CORE_DB);
		} catch (JDBCException e) {
			System.err.println("Could not create MySQLHandler : "
					+ e.getMessage());
			e.printStackTrace();
			return;
		}
		// Connect to HBase
		HbaseHandler hh = new HbaseHandler(
				NoSQLConstants.HBaseClusterConstants.ZK_QUOROM,
				NoSQLConstants.HBaseClusterConstants.ZK_CLIENT_PORT,
				NoSQLConstants.HBaseClusterConstants.HBASE_MASTER,
				NoSQLConstants.HBaseClusterConstants.HBASE_TABLE_NAME);
		try {
			hh.connect(false);
		} catch (IOException e) {
			System.err.println("Could not conenct to HBase" + e.getMessage());
			e.printStackTrace();
			return;
		}
		// Create consumers and producers objects
		DataRecordProducer queueProducer = null;
		DataRecordConsumer queueConsumer = null;
		try {
			queueProducer = new DataRecordProducer(queue, my, publicationYear);
			queueConsumer = new DataRecordConsumer(queue, hh);
		} catch (CoreDBOpException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}

		// start the timer
		long startTime = System.currentTimeMillis();

		/*
		 * start the producer and consumer thread
		 */
		Future<?> producer = executorService.submit(queueProducer);
		Future<?> consumer = executorService.submit(queueConsumer);
		try {
			// wait for producer to finish
			if (producer.get() == null) {
				System.out.println("[COMPLETED] : "
						+ "Producer done fetching records from MySQL ");
			}
			// wait for consumer to finish
			if (consumer.get() == null) {
				System.out.println("[COMPLETED] : "
						+ "Consumer done inserting records to HBase");
			}
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		/*
		 * clean-ups
		 */
		// shutdown executor service
		executorService.shutdown();
		// clean up producer object
		try {
			queueProducer.clean();
		} catch (CoreDBOpException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		// close the connection to MySQL DB
		try {
			my.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		// close the connection to HBase
		try {
			hh.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		// spit out total time taken
		System.out.println("Time taken (ms) : " +
				(System.currentTimeMillis() - startTime));
	}
}