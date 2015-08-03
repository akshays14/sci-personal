package com.sciencescape.ds.data.transfer;

/**
 * Class to contain all the constants related to
 * com.sciencescape.ds.data.transfer package.
 *
 * @author akshay
 * @version 0.2
 */
public class Constants {
	/**
	 * Class having constants specific to Command Line Arguments,
	 * and associated processing.
	 *
	 * @author akshay
	 * @version 0.2
	 */
	public static class CLA {
		/**!< Program name */
		public static final String PROGRAM_NAME = "UnnormalizeCoreDB";
		/**!< Program description */
		public static final String PROGRAM_DESCRIPTION =
				"De-normalize core-db tables to update paper table in HBase";

		/**!< Output hbase table short option */
		public static final String OUTPUT_TABLE_OPT_SHORT = "-t";
		/**!< Output hbase table long option */
		public static final String OUTPUT_TABLE_OPT_LONG =
				"--HBaseTable";
		/**!< Output hbase table option description */
		public static final String OUTPUT_TABLE_OPT_DESCRIPTION =
				"HBase table to write de-normalized data to";
		/**!< Output hbase table argument (for reading) */
		public static final String OUTPUT_TABLE_ARG = "HBaseTable";

		/**!< Paper publication year short option */
		public static final String PAPER_PUBLICATION_YEAR_OPT_SHORT = "-y";
		/**!< Paper publication year long option */
		public static final String PAPER_PUBLICATION_YEAR_OPT_LONG =
				"--yearOfPaperPublication";
		/**!< Paper publication year option description  */
		public static final String PAPER_PUBLICATION_YEAR_OPT_DESCRIPTION =
				"Calendar year in which papers (to be imported) were "
				+ "published ";
		/**!< Paper publication year argument */
		public static final String PAPER_PUBLICATION_YEAR_ARG =
				"yearOfPaperPublication";
		
		/**!< Paper publication month short option */
        public static final String PAPER_PUBLICATION_MONTH_OPT_SHORT = "-m";
        /**!< Paper publication month long option */
        public static final String PAPER_PUBLICATION_MONTH_OPT_LONG =
                "--monthOfPaperPublication";
        /**!< Paper publication month option description  */
        public static final String PAPER_PUBLICATION_MONTH_OPT_DESCRIPTION =
                "Calendar month in which papers (to be imported) were "
                + "published ";
        /**!< Paper publication month argument */
        public static final String PAPER_PUBLICATION_MONTH_ARG =
                "monthOfPaperPublication";
        
        /**!< Paper publication day short option */
        public static final String PAPER_PUBLICATION_DAY_OPT_SHORT = "-d";
        /**!< Paper publication day long option */
        public static final String PAPER_PUBLICATION_DAY_OPT_LONG =
                "--dayOfPaperPublication";
        /**!< Paper publication day option description  */
        public static final String PAPER_PUBLICATION_DAY_OPT_DESCRIPTION =
                "Calendar day in which papers (to be imported) were "
                + "published ";
        /**!< Paper publication day argument */
        public static final String PAPER_PUBLICATION_DAY_ARG =
                "dayOfPaperPublication";

	}
	
	/**
	 * Class for constants specific to data transfer to HBase from MySQL.
	 *
	 * @author akshay
	 * @version 0.1
	 */
	public static class DataTransfer {
		/**!< For executor server thread pool */
		public static final int THREAD_POOL_SIZE = 5;
		/**!< Blocking queue size for data-ingestion */
		public static final int BLOCKING_QUEUE_SIZE = 10000;
		/**!< Dummy pmid for empty records */
		public static final long EMPTY_RECORD_PMID = -1L;
	}
	
	/**
	 * Class for constants specific to Environment variables.
	 *
	 * @author akshay
	 * @version 0.2
	 */
	public static class EnvironmentVariables {
		/**!< For deployment environment */
		public static final String DEPLOY_ENV = "DEPLOY_ENV";
		/**!< For source directory */
		public static final String SOURCE_DIRECTORY = "SRCDIR";
	}
}
