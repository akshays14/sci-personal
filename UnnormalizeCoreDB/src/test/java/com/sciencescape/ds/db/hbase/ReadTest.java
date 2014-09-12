package test.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;
import java.lang.reflect.Field;

import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;

public class ReadTest {
	public static void main(String[] args) {
		//MySQL columns
		String mySQLCols[] =  {"PERS_ID","PERS_DESC"};
		MySQLHandler my = null;
		try {
			my = new MySQLHandler("127.0.0.1", 3306, "root", "P@rots", 
					"hbase_test", "PERSON", mySQLCols);
		} catch (JDBCException e) {
			System.err.println("Could not create MySQLHandler : " + e.getMessage());
			e.printStackTrace();
			return;
		}
		try {
			my.connect();
			DataRecord rec = my.readRecord();
			while (rec != null) {
				for(int i = 0; i < mySQLCols.length; ++i) {
					System.out.println(mySQLCols[i] + " : " + rec.getDataAt(i));
				}
				rec = my.readRecord();
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
				// TODO: deal with it properly
			}
		}
		return values;
	}
}
