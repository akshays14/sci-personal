package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.util.List;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOpException;

public class DataTransfer  {
	public static void main(String[] args) {
		long range = 5000000;
		long chunkLength = 1000;
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
		// Connect to HBase
		HbaseHandler hh = new HbaseHandler("hadoop1:60000", "denormalizedData", null, null);
		try {
			hh.connect();
		} catch (IOException e1) {
			System.err.println("Could not conenct to HBase");
			e1.printStackTrace();
			return;
		}
		long startTime = System.currentTimeMillis();
		// put the data in hbase in chunks (chunkLength at a time)
		for (long i = 0; i <=range-chunkLength; i = i + chunkLength) {
			List<DenormalizedFields> dfList = null;
			try {
				dfList = core.getDenormalizedFields(i, i + chunkLength);
			} catch (CoreDBOpException e) {
				System.err.println(e.getMessage());
				continue;	// let the operation go on
			}
			// push to hbase
			for (DenormalizedFields df : dfList) {
				//df.printFields(System.out);
				try {
					hh.writeRecord(df);
				} catch (IOException ex) {
					ex.printStackTrace();
					return;
				}
			}
		}
		System.out.println("Time taken : " + (System.currentTimeMillis() - startTime));
	}
}
