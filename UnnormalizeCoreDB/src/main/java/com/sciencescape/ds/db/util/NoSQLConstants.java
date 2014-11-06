package main.java.com.sciencescape.ds.db.util;

public class NoSQLConstants {

	public static class HBaseConfigConstants {
		public static final String ZK_QUOROM = "hbase.zookeeper.quorum";
		public static final String ZK_PORT = "hbase.zookeeper.property.clientPort";
		public static final String MASTER= "hbase.master";
		public static final String ROOT_DIR ="hbase.rootdir";
		public static final String DISTRIBUTED_MODE = "hbase.cluster.distributed";
	}

	public static class HBaseClusterConstants {
		public static final String HBASE_MASTER = "local:60000";
		public static final String ZK_QUOROM = "localhost:2181";
		public static final String ZK_CLIENT_PORT = "2181";
		public static final String HBASE_TABLE_NAME = "dnData";
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
		public static final String AUTHOR_NAME = "_name";
		public static final String RAW_AFFILIATION = "_rawAff";
		public static final String NORM_AFFILIATION = "_normAff";
		public static final String FIELD_NAME = "_name";
		public static final String IMPORT_DATE = "date";
	}
}