package test.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;

public class WriteTest {
	public static void main(String[] args) {
		//HBAse column types
		Class types[] = {Long.class, Long.class, String.class, String.class, String.class, String.class, };
		//HBase columns
		String hbaseCols[] =  {"f1:app","f2:apps"};
		//MySQL columns
		//String mySQLCols[] =  {"PERS_ID","PERS_DESC"};

		//HBaseSource hb = new HBaseSource("sample",hbaseCols, types);
		HbaseHandler hh = new HbaseHandler("hadoop1:60000", "t1", hbaseCols, types);
		//MySQLSource my = new MySQLSource("PERSON",mySQLCols);

		/* data to be inserted */
		String values1[] = {"rowx", "valuex"};
		String values2[] = {"rowy", "valuey"};

		try {
			hh.connect();
			DataRecord rec = new DataRecord(values1);
			if (rec != null) {
				hh.writeRecord(rec);
			}
			DataRecord rec1 = new DataRecord(values2);
			if (rec1 != null) {
				hh.writeRecord(rec1);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
	}
}

