package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	 * @brief function to denormalize and transfer data to Hbase
	 * @param args command line arguments
	 *
	 * Main function to denormalize and transfer data to Hbase
	 * from MySQWL.
	 */
	public static void main(final String[] args) {
		long range = 20000;
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
			hh.connect(true);
		} catch (IOException e) {
			System.err.println("Could not conenct to HBase" + e.getMessage());
			e.printStackTrace();
			return;
		}
		// Create consumers and producers
		DataRecordProducer queueProducer = null;
		DataRecordConsumer queueConsumer = null;
		try {
			queueProducer = new DataRecordProducer(queue, range, my);
			queueConsumer = new DataRecordConsumer(queue, range, hh);
		} catch (CoreDBOpException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		// start the timer
		long startTime = System.currentTimeMillis();
		// start the producer and consumer
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
		// shutdown executor service
		executorService.shutdown();
		// spit out total time taken
		System.out.println("Time taken (ms) : " +
				(System.currentTimeMillis() - startTime));
	}
}