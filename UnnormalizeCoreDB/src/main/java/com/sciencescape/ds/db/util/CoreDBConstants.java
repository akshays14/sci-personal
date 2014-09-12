package main.java.com.sciencescape.ds.db.util;

/**
 * @class CoreDBConstants CoreDBConstants.java
 * @author Akshay
 * 
 * Class to store all the constants related to 
 * Core-DB at ScienceScape.
 */
public class CoreDBConstants {
	
	/**
	 * @brief enlist table names in core-db
	 * @author Akshay
	 *
	 * Class having table names in core-db.
	 */
	public static class Tables {
		public static final String PAPER = "paper";
	}
	
	/**
	 * @brief enlist fields of paper table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of paper table in core-db.
	 */
	public static class PaperFields {
		public static final String ID = "id";
		public static final String PMID = "pmid";
		public static final String VENUE_ID = "venue_id";
		public static final String DOI = "doi";
		public static final String TITLE = "title";
		public static final String PM_DATE_RELEASED = "date_pubmed_release";
		public static final String ISSN = "issn";
		public static final String YEAR = "year";
		public static final String MONTH = "month";
		public static final String DAY = "day";
		public static final String ISO = "iso";
		public static final String ISSUE = "issue";
		public static final String LANGUAGE = "language";
		public static final String TYPE = "type";
		public static final String JOURNAL_NAME = "journal_name_raw";
		public static final String EIGENFACTOR = "eigenfactor";
		public static final String COUNTRY = "country";
		public static final String VOLUME = "volume";
		public static final String NLM_ID = "nlm_id";
		public static final String METADATA_SOURCE = "source";
		public static final String ABSTRACT = "abstract";
		public static final String FIELDS = "fields";
	}
}
