package com.sciencescape.ds.data.transfer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import com.sciencescape.ds.db.hbase.HbaseHandler;
import com.sciencescape.ds.db.mysql.coredb.CoreDBOpException;
import com.sciencescape.ds.db.mysql.coredb.DenormalizedFields;

public class DataRecordConsumer implements Runnable {

	private BlockingQueue<DenormalizedFields> queue;
	private HbaseHandler hh;
	private long recordsProcessed;

	public DataRecordConsumer(final BlockingQueue<DenormalizedFields> queue,
			final HbaseHandler hh) throws CoreDBOpException {
		if (queue == null) {
			throw new CoreDBOpException("Given queue object is null");
		}
		this.queue = queue;
		this.hh = hh;
		this.recordsProcessed = 0L;
	}

	public void run() {
		DenormalizedFields df = null;
		//consuming messages until expected numOfRecords are pulled from queue
		while (true) {
			try {
				// pull from queue
				df = queue.take();
				// check if this is the empty record
				// if it is we should terminate the loop
				if (df.get_pmId() == DataTransferConstants.DataTransfer.EMPTY_RECORD_PMID) {
					break;
				}
				// insert to hbase
				hh.writeRecord(df);
				recordsProcessed++;
			} catch (InterruptedException e) {
				System.out.println("Failed writing to HBasef for id : " + df.get_id());
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Failed writing to HBasef for id : " + df.get_id());
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		// print the number of records being fetched
		System.out.println("[COMPLETED] : Consumer done inserting " +
				this.recordsProcessed + " records to HBase");
	}
}
