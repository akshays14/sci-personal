package main.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import main.java.com.sciencescape.ds.db.rdbms.common.DataReader;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.common.DataWriter;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;

public class HbaseHandler implements DataReader, DataWriter {

	private String _tableName = null;
	private String[] _hbaseCols = null;
	private Class[] _colTypes = null;
	private Configuration _config = null;
	private HTable _table = null;
	private String _hbaseMaster = null;
	
	public HbaseHandler(String hbaseMaster, String table, String hbasecols[], 
			Class types[]) {
		_hbaseMaster = hbaseMaster;
	    _tableName = table;
	    _hbaseCols = hbasecols;
	    _colTypes = types;
	}
	
	@Override
	public void connect() throws IOException {
		_config = HBaseConfiguration.create();
		/* specify ZK setup */
	    _config.set("hbase.zookeeper.quorum", _hbaseMaster);
	    _config.set("hbase.zookeeper.property.clientPort", 
	    		NoSQLConstants.ZooKeeperConstants.ZK_PORT_DEFAULT);
	    /* specify HBase setup */
	    _config.set("hbase.rootdir", "hdfs://10.100.0.120:8020/hbase");
	    _config.set("hbase.cluster.distributed", "true");
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	private void initalizeWriter() throws IOException {
	    _table = new HTable(_config, _tableName);
	}
	
	@Override
	public void writeRecord(DataRecord rec) throws IOException {
		if (_table == null) {
			initalizeWriter();
		}
		//We will use the first item as the key
		Put p = new Put(Bytes.toBytes((int) rec.getDataAt(0)));
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

	@Override
	public DataRecord readRecord() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
