package test.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;

public class WriteTest {
	public static void main(String[] args) {
		HbaseHandler hh = new HbaseHandler("hadoop1,hadoop2,hadoop3", "2181", "hadoop1:60000", "t1");
		/* format of data */
		String[] hbaseCols = {"key", "f1:a"};
		/* data to be inserted */
		String values1[] = {"rowx", "valuex"};
		String values2[] = {"rowy", "valuey"};

		try {
			hh.connect(true);
			// write first record
			DataRecord rec = new DataRecord(values1);
			if (rec != null) {
				hh.writeRecord(rec, hbaseCols);
			}
			// write second record
			DataRecord rec1 = new DataRecord(values2);
			if (rec1 != null) {
				hh.writeRecord(rec1, hbaseCols);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
	}
}