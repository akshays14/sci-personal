package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;

public class DataRecordConsumer implements Runnable{

	private BlockingQueue<DenormalizedFields> queue;
	private HbaseHandler hh;
	private long numOfRecords;
	private long recordsProcessed;

	public DataRecordConsumer(final BlockingQueue<DenormalizedFields> queue,
			final long numOfRecords, final HbaseHandler hh)
					throws CoreDBOpException {
		if (queue == null) {
			throw new CoreDBOpException("Given queue object is null");
		}
		this.queue = queue;
		this.hh = hh;
		this.numOfRecords = numOfRecords;
		this.recordsProcessed = 0L;
	}

	@Override
	public void run() {
		DenormalizedFields df = null;
		//consuming messages until expected numOfRecords are pulled from queue
		while (recordsProcessed < numOfRecords) {
			try {
				// pull from queue
				df = queue.take();
				// insert to hbase
				hh.writeRecord(df);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} finally {
				recordsProcessed++;
			}
		}
	}
}
