package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.lang.reflect.Field;

import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;

public class DataTransfer  {
	public static void main(String[] args) {
		//MySQL columns
		String mySQLCols[] =  {"id_venue","key", "value"};
		MySQLHandler my = null;
		try {
			my = new MySQLHandler("54.81.251.153", 3306, "ds-team", "DsTeamSQL", 
					"core_db");
			
		} catch (JDBCException e) {
			System.err.println("Could not create MySQLHandler : " + e.getMessage());
			e.printStackTrace();
			return;
		}
		
/*		try {
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
*/	}
}
