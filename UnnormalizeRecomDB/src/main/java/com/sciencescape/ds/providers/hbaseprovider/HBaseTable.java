package com.sciencescape.ds.providers.hbaseprovider;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;


/**
 * <h1>Wrapper for HBase table</h1>
 * Provides abstraction for HBase table object.
 *
 * @author  singha57
 * @version 1.0
 * @since   2014-12-21
 */
public class HBaseTable {

	/**!< HBase table name to open connection to */
	private String tableName;
	/**!< Hadoop Configuration object to be passed by {@link HBaseProvider} */
	private Configuration config;
	/**!< HBase table object from HBase Client API */
	private HTable htable;

	public HBaseTable(final HBaseProvider hbaseProvider,
			final String tableName) throws HBaseProviderException {
		this.tableName = tableName;
		/* check if HbaseProvider object is proper */
		if (hbaseProvider == null) {
			throw new HBaseProviderException();
		}
	}

}
