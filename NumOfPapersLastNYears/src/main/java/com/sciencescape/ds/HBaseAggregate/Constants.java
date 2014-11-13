package com.sciencescape.ds.HBaseAggregate;

/**
 * @class Constants Constants.java
 * @author akshay
 *
 * Class to store all the constants related to HBaseAggregate package.
 */
public class Constants {

	/**
	 * @class MapReduce Constants.java
	 * @author akshay
	 *
	 * Class having Hadoop's MapReduce specific constants.
	 */
	public static class MapReduce {
		/**!< update status of MapReduce job after records */
		public static final int STATUS_UPDATE_FREQUENCY = 100;
		/**!< context status message string format */
		public static final String MAPPER_STATUS_MSG_FORMAT =
				"Mapper processed %d records so far";
	}

	/**
	 * @class HBaseDB Constants.java
	 * @author akshay
	 *
	 * Class having constants specific to our HBase deployment.
	 */
	public static class HBaseDB {
		/* Output table constants */
		/**!< column family written by reducer job  */
		public static final String YEARS_COLUMN_FAMILY = "YEARS";
		/**!< column qualifier written by reducer job for minimum year */
		public static final String MIN_YEAR_COLUMN_QUALIFIER = "min";
		/**!< column qualifier written by reducer job for maximum year */
		public static final String MAX_YEAR_COLUMN_QUALIFIER = "max";
		/* Input table constants */
		/**!< author column family for scan object */
		public static final String AUTHORS_COLUMN_FAMILY = "AUTHORS";
		/**!< date column family for scan object */
		public static final String DATE_COLUMN_FAMILY = "DATE";
		/**!< year column for scan object */
		public static final String YEAR_COLUMN = "year";

	}

	/**
	 * @class ReduceAggregationFormat Constants.java
	 * @author akshay
	 *
	 * Class having constants specific to the aggregate format,
	 * used by reducer classes.
	 */
	public static class ReduceAggregationFormat {
		/**!< prefix for list after aggregation of keys */
		public static final String LIST_PREFIX = "Years : ";
		/**!< delimiter for aggregate list of keys */
		public static final String DELIMITER = " - ";
	}

	/**
	 * @class UserMessages Constants.java
	 * @author akshay
	 *
	 * Class having constants specific to user-messages.
	 * Mainly used in logging.
	 */
	public static class UserMessages {
		/**!< ResourceBundle base name */
		public static final String RB_BASE_NAME = "UserMessages";
		/**!< language for user messages */
		public static final String DEFAULT_LANGUAGE = "en";
		/**!< country-locale for user messages */
		public static final String DEFAULT_COUNTRY = "US";
	}

	/**
	 * @class CLA Constants.java
	 * @author akshay
	 *
	 * Class having constants specific to Command Line Arguments,
	 * and associated processing.
	 */
	public static class CLA {
		public static final String PROGRAM_NAME = "GetActiveYears";
		public static final String PROGRAM_DESCRIPTION = "Find active years for each author";
		public static final String INPUT_TABLE_OPT_SHORT = "-it";
		public static final String INPUT_TABLE_OPT_LONG = "--inputTable";
		public static final String INPUT_TABLE_OPT_DESCRIPTION = "HBase table to read data from";
		public static final String OUTPUT_OPT_SHORT = "-o";
		public static final String OUTPUT_OPT_LONG = "--output";
		public static final String OUTPUT_OPT_DESCRIPTION = "Specify output target for reducer";
		public static final String OUTPUT_OPT_HBASE_CHOICE = "hbase";
		public static final String OUTPUT_OPT_FS_CHOICE = "filesystem";

		public static final String OUTPUT_TABLE_OPT_SHORT = "-ot";
		public static final String OUTPUT_TABLE_OPT_LONG = "--outputTable";
		public static final String OUTPUT_TABLE_OPT_DESCRIPTION = "HBase table to write summary data to";

		public static final String OUTPUT_FILE_OPT_SHORT = "-of";
		public static final String OUTPUT_FILE_OPT_LONG = "--outputFile";
		public static final String OUTPUT_FILE_OPT_DESCRIPTION = "FileSystem file to write summary data to";

		public static final String INPUT_TABLE_ARG = "inputTable";
		public static final String OUTPUT_FORMAT_ARG = "output";
		public static final String OUTPUT_TABLE_ARG = "outputTable";
		public static final String OUTPUT_FILE_ARG = "outputFile";
	}
}
