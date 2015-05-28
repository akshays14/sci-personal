package com.sciencescape.ds.db.hbase;

/**
 * Class to contain all the constants related to
 * com.sciencescape.ds.data.transfer package.
 *
 * @author akshay
 * @version 0.2
 */
public class Constants {

	/**
	 * Class for all hbase configuration constants.
	 *
	 * @author akshay
	 * @version 0.1
	 */
	public static class HBaseConfigConstants {
		/**!< zookeper quorom */
		public static final String ZK_QUOROM = "hbase.zookeeper.quorum";
		/**!< zookeper port */
		public static final String ZK_PORT =
				"hbase.zookeeper.property.clientPort";
		/**!< hbase master server */
		public static final String MASTER = "hbase.master";
		/**!< the directory into which HBase persists in region servers */
		public static final String ROOT_DIR = "hbase.rootdir";
		/**!< hbase distributed mode */
		public static final String DISTRIBUTED_MODE =
				"hbase.cluster.distributed";
	}

	/**
	 * Class for all default values for hbase.
	 *
	 * @author akshay
	 * @version 0.1
	 */
	public static class Defaults {
		/*public static final String HBASE_MASTER = "dev1:60000";
		public static final String ZK_QUOROM = "dev1:2181,dev2:2181,dev3:2181";
		public static final String ZK_CLIENT_PORT = "2181";
		public static final String HBASE_TABLE_NAME = "denormalizedData_dev";*/
		/**!< hbase distributed mode */
		public static final String HBASE_DISTRIBUTED_MODE = "true";
	}
}
