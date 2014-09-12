package main.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import main.java.com.sciencescape.ds.db.rdbms.common.DataContainer;
import main.java.com.sciencescape.ds.db.rdbms.common.DataReader;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.common.DataWriter;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;

/**
 * @class HbaseHandler HbaseHandler.java
 * @brief handler for HBase
 * @author Akshay
 *
 * Class for handling all the operations to HBase. 
 */
public class HbaseHandler implements DataReader, DataWriter {

	private String _tableName = null;
	private String[] _hbaseCols = null;
	private Class[] _colTypes = null;
	private Configuration _config = null;
	private HTable _table = null;
	private String _hbaseMaster = null;

	/**
	 * @brief constructor for HbaseHandler
	 * @param hbaseMaster host running HMaster process
	 * @param table HBase's table name 
	 * @param hbaseCols value of a HBase cell
	 * @param types type of the values passed
	 */
	public HbaseHandler(String hbaseMaster, String table, String hbaseCols[], 
			Class types[]) {
		_hbaseMaster = hbaseMaster;
		_tableName = table;
		_hbaseCols = hbaseCols;
		_colTypes = types;
	}

	/**
	 * @brief method to set configuration for non-local HBase cluster
	 * @param config Apache Hadoop Configuration to be set
	 * 
	 * Method to set the fields required to connect to an external
	 * HBase cluster.
	 */
	private void configureExternalHBase (Configuration config) {
		config.clear();
		//specify ZK setup
		config.set(NoSQLConstants.HBaseConfigConstants.ZK_QUOROM, _hbaseMaster);
		config.set(NoSQLConstants.HBaseConfigConstants.ZK_PORT, 
				NoSQLConstants.ZooKeeperConstants.ZK_PORT_DEFAULT);
		// specify HBase setup
		config.set(NoSQLConstants.HBaseConfigConstants.MASTER, 
				NoSQLConstants.HBaseClusterConstants.HBASE_MASTER);
		config.set(NoSQLConstants.HBaseConfigConstants.ROOT_DIR, 
				NoSQLConstants.HBaseClusterConstants.HBASE_HDFS_DIR);
		config.set(NoSQLConstants.HBaseConfigConstants.DISTRIBUTED_MODE, 
				NoSQLConstants.HBaseClusterConstants.HBASE_DISTRIBUTED_MODE);	
	}

	/**
	 * Implements connect method of {@link DataContainer}.
	 * By default, it creates configuration to connect to local
	 * HBase cluster. Call configureExternalHBase to be
	 * able to connect to a non-local HBase cluster.
	 */
	@Override
	public void connect() throws IOException {
		_config = HBaseConfiguration.create();
		// un-comment this for external HBase
		/* configureExternalHBase(_config); */
	}

	/**
	 * Implements close method of {@link DataContainer}.
	 * @todo implement it.
	 */
	@Override
	public void close() throws IOException {

	}

	/**
	 * @brief initialize Writer for HbaseHandler
	 * @throws IOException
	 * 
	 * Method to initialize writer for HBase. Created a
	 * handle for the HBase table.
	 */
	private void initalizeWriter() throws IOException {
		_table = new HTable(_config, _tableName);
	}

	/**
	 * @brief write given record to HBase table
	 * @param DataRecord data-record to be written
	 * @throws IOException throws IOException on writing error 
	 * 
	 * Function to write a record to a HBase cluster.
	 */
	@Override
	public void writeRecord(DataRecord rec) throws IOException {
		if (_table == null) {
			initalizeWriter();
		}

		//We will use the first item as the key
		Put p = new Put(Bytes.toBytes((String) rec.getDataAt(0)));
		for (int i = 1; i < _hbaseCols.length; i++) {
			String col = _hbaseCols[i];
			int index = col.indexOf(":");
			byte data[] = null;
			if (_colTypes[i] == Integer.class) {
				data = Bytes.toBytes((int) rec.getDataAt(i));

			} else if (_colTypes[i] == String.class) {
				data = Bytes.toBytes((String) rec.getDataAt(i));
			}
			if (index > 0) {
				p.add(Bytes.toBytes(col.substring(0, index)), Bytes.toBytes(col.substring(index + 1)), data);
			} else {
				p.add(Bytes.toBytes(col), null, data);
			}
			/* finally put in the table */
			_table.put(p);
		}
	}

	/**
	 * Implements readRecord method of DataReader
	 * @todo to be implemented 
	 */
	@Override
	public DataRecord readRecord() throws IOException {
		return null;
	}
}
