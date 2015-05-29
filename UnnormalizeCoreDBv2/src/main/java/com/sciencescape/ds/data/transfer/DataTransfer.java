package com.sciencescape.ds.data.transfer;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.DataTransferConstants;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;
 */

/**
 * Class to de-normalize and transfer data from MySQL to HBase.
 *
 * @author akshay
 * @version 0.1
 */
public final class DataTransfer {

	/**!< output hbase table */
	private String hbaseTable;
	/**!< target publication year */
	private String targetYear;
	/**!< logger object */
	private Logger logger = LoggerFactory.getLogger(DataTransfer.class);

	/**
	 * Main constructor for {@code DataTransfer} mainly for reading and
	 * verifying provided CLAs.
	 *
	 * @param args list of command line arguments
	 */
	public DataTransfer(final String[] args) {
		/* specify expected command line arguments */
		ArgumentParser parser = ArgumentParsers
				.newArgumentParser(Constants.CLA.PROGRAM_NAME)
				.defaultHelp(true)
				.description(Constants.CLA.PROGRAM_DESCRIPTION);
		// output hbase table
		parser.addArgument(Constants.CLA.OUTPUT_TABLE_OPT_SHORT,
				Constants.CLA.OUTPUT_TABLE_OPT_LONG).required(true)
				.help(Constants.CLA.OUTPUT_TABLE_OPT_DESCRIPTION);
		// target year
		parser.addArgument(Constants.CLA.PAPER_PUBLICATION_YEAR_OPT_SHORT,
				Constants.CLA.PAPER_PUBLICATION_YEAR_OPT_LONG)
				.help(Constants.CLA.PAPER_PUBLICATION_YEAR_OPT_DESCRIPTION);

		// parse the arguments
		Namespace ns = null;
		try {
			ns = parser.parseArgs(args);
		} catch (ArgumentParserException e) {
			throw new IllegalArgumentException(e);
		}

		/* retrieve parameters and set class variable */
		// set hbase input table
		hbaseTable = ns.getString(Constants.CLA.OUTPUT_TABLE_ARG);
		if (hbaseTable == null) {
			throw (new IllegalArgumentException(
					"Output hbase table must be provided"));
		}
		logger.info("Output HBase Table : {}.", hbaseTable);

		// set the target number of year
		targetYear = ns.getString(Constants.CLA.PAPER_PUBLICATION_ARG);
		if (targetYear == null) {
			throw (new IllegalArgumentException(
					"Target year of publicatoin table must be specified"));
		}
		logger.info("Target year of publication : {}.", targetYear);
	}

	/**
	 * Main function to de-normalize and transfer data to Hbase
	 * from MySQL.
	 *
	 * @param args command line arguments
	 */
	public static void main(final String[] args) {
		// Create logger object
		/**!< logger object */
		Logger logger = LoggerFactory.getLogger(DataTransfer.class);
		// Create DataTransfer object
		try {
			DataTransfer dataTransfer = new DataTransfer(args);
		} catch (IllegalArgumentException e) {
			logger.error("Unexpected arguments to the program", e);
		}
		/*
		 * create necessary objects
		 */
		// Create executor service
		/*	ExecutorService executorService = Executors.newFixedThreadPool(
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


		 * start the producer and consumer thread

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


		 * clean-ups

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
		 */}
}
