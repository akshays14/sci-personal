package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.util.List;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.JDBCException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOpException;

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
		long chunkLength = 500;
		// Create connection to CoreDB
		MySQLHandler my = null;
		try {
			my = new MySQLHandler("54.81.251.153", 3306, "ds-team", "DsTeamSQL",
					"core_db");
		} catch (JDBCException e) {
			System.err.println("Could not create MySQLHandler : "
					+ e.getMessage());
			e.printStackTrace();
			return;
		}
		// Get CoreDBOperatoin object to deal with core-db
		CoreDBOperations core = null;
		try {
			core = new CoreDBOperations(my);
		} catch (MySQLOpException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		// Connect to HBase
		HbaseHandler hh = new HbaseHandler("localhost:60000", "dnData",
				null, null);
		try {
			hh.connect(true);
		} catch (IOException e1) {
			System.err.println("Could not conenct to HBase");
			e1.printStackTrace();
			return;
		}
		long startTime = System.currentTimeMillis();
		// put the data in hbase in chunks (chunkLength at a time)
		for (long i = 0; i <= range - chunkLength; i = i + chunkLength) {
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
		System.out.println("Time taken : " + (System.currentTimeMillis()
				- startTime));
	}
}
