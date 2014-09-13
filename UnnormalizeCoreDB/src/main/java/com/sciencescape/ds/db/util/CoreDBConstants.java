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
		public static final String VENUE = "venue";
		public static final Object PAPER_TO_AUTHOR = "paper_to_author";
		public static final Object AUTHOR = "author";
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
	
	/**
	 * @brief enlist fields of venue table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in venue table in core-db.
	 */
	public static class VenueFields {
		public static final String PUBLISHER = "publisher";
		public static final String VENUE_ID = "id";
	}

	/**
	 * @brief enlist fields of paper_to_author table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in paper_to_author table in core-db.
	 */
	public static class PaperToAuthorFields {
		public static final String PAPER_ID = "id_paper";
		public static final String AUTHOR_ID = "id_author";
	}

	/**
	 * @brief enlist fields of author table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of author in venue table in core-db.
	 */
	public static class AuthorFields {
		public static final String ID = "id";
		public static final String NAME = "name";
	}

	/**
	 * @brief enlist all the user messages for CoreDB
	 * @author akshay
	 *
	 * Class enlisting all the user messages while 
	 * interacting with Core DB.
	 */
	public static class Messages {
		public static final String RESULTSET_NULL_MSG = "Provided result set is null";
		public static final String MSG_SEPERATOR = " : ";
		public static final String MSG_STRING_FIELD_FORMAT = "%s : %s%n";
		public static final String MSG_INT_FIELD_FORMAT = "%s : %d%n";
		public static final String MSG_FLOAT_FIELD_FORMAT = "%s : %f%n";
		public static final String MSG_AUTHOR_ID_FORMAT = "Author-ID : %d%n";
		public static final String MSG_AUTHOR_NAME_FORMAT = "\t%s%n";
	}
}
