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
	public static class HBaseConfig {
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
	
	public static class HBaseClusterConstants {
		public static final String HBASE_MASTER = "dev1:60000";
		public static final String ZK_QUOROM = "dev1:2181,dev2:2181,dev3:2181";
		public static final String ZK_CLIENT_PORT = "2181";
		public static final String HBASE_TABLE_NAME = "denormalizedData_dev";
		public static final String HBASE_DISTRIBUTED_MODE = "true";
	}

	public static class ColumnFamilies {
		public static final String PMID = "PMID";
		public static final String DOI = "DOI";
		public static final String TITLE = "TITLE";
		public static final String DATE_PM_RELEASED = "DATE_PM_RELEASED";
		public static final String ISSN = "ISSN";
		public static final String DATE = "DATE";
		public static final String ISO = "ISO";
		public static final String ISSUE = "ISSUE";
		public static final String LANGUAGE = "LANGUAGE";
		public static final String TYPE = "TYPE";
		public static final String JOURNAL = "JOURNAL";
		public static final String EIGENFACTOR = "EF";
		public static final String LOCATION = "LOCATION";
		public static final String VOULME = "VOLUME";
		public static final String NLM = "NLM";
		public static final String MD = "MD";
		public static final String VENUE = "VENUE";
		public static final String AUTHORS = "AUTHORS";
		public static final String INSTITUTION = "INSTITUTION";
		public static final String ABTSRACT = "ABSTRACT";
		public static final String SECTIONS = "SECTIONS";
		public static final String PRODUCTS = "PRODUCTS";
		public static final String FIELDS = "FIELDS";
		public static final String CITATIONS = "CITATIONS";
		public static final String IMPORT_INFO = "IMPORTINFO";
	}

	public static class Columns {
		public static final String ID = "id";
		public static final String VALUE = "value";
		public static final String YEAR = "year";
		public static final String MONTH = "month";
		public static final String DAY = "day";
		public static final String COUNTRY = "country";
		public static final String SOURCE = "source";
		public static final String PUBLISHER = "publisher";
	}

	public static class ColumnKeyWords {
		public static final String AUTHOR_NAME = "name_";
		public static final String RAW_AFFILIATION = "rawAff_";
		public static final String NORM_AFFILIATION = "normAff_";
		public static final String FIELD_NAME = "name_";
		public static final String IMPORT_DATE = "date";
	}
}
