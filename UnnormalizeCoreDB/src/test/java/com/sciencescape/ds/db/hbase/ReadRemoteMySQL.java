package test.java.com.sciencescape.ds.db.hbase;

import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOpException;
import main.java.com.sciencescape.ds.db.transfer.CoreDBOpException;
import main.java.com.sciencescape.ds.db.transfer.CoreDBOperations;

public class ReadRemoteMySQL {
	public static void main(String[] args) {
		// Create connection to CoreDB
		MySQLHandler my = null;
		try {
			my = new MySQLHandler("54.81.251.153", 3306, "ds-team", "DsTeamSQL", "core_db");
		} catch (JDBCException e) {
			System.err.println("Could not create MySQLHandler : " + e.getMessage());
			e.printStackTrace();
			return;
		}
		// Get CoreDBOperatoin object to deal with core-db
		CoreDBOperations core = null;
		try {
			core = new CoreDBOperations(my);
		} catch (MySQLOpException e) {
			System.err.println(e.getMessage());;
			e.printStackTrace();
			return;
		}
		// get first (2) denormalized fields from CoreDB
		try {
			core.getDenormalizedFields(2);
		} catch (CoreDBOpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
