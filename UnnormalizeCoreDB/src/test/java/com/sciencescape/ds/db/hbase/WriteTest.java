package test.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;
import java.lang.reflect.Field;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;

public class WriteTest {
	public static void main(String[] args) {
		//HBAse column types
		Class types[] = {Integer.class, String.class};
		//HBase columns
		String hbaseCols[] =  {"f1:app","f2:apps"};
		//MySQL columns
		//String mySQLCols[] =  {"PERS_ID","PERS_DESC"};

		//HBaseSource hb = new HBaseSource("sample",hbaseCols, types);
		HbaseHandler hh = new HbaseHandler("10.100.0.120", "t1", hbaseCols, types);
		//MySQLSource my = new MySQLSource("PERSON",mySQLCols);

		/* data to be inserted */
		String values1[] = {"rowx", "valuex"};
		String values2[] = {"rowy", "valuey"};

		try {
			hh.connect();
			//my.connect();
			DataRecord rec = new DataRecord(values1);
			if (rec != null) {
				hh.writeRecord(rec);
				//rec = my.readRecord();
			}

		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
	}

	static <T> Object[] getFieldValues(final Class<T> type, final T instance) {

		final Field[] fields = type.getDeclaredFields(); // includes private fields

		final Object[] values = new Object[fields.length];

		for (int i = 0; i < fields.length; i++) {
			if (!fields[i].isAccessible()) {
				fields[i].setAccessible(true); // enables private field accessing.
			}
			try {
				values[i] = fields[i].get(instance);
			} catch (IllegalAccessException iae) {
				// @@?
			}
		}

		return values;
	}
}

