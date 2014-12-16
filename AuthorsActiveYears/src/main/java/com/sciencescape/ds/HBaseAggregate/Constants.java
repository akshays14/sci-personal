package com.sciencescape.ds.HBaseAggregate;

/**
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
		/**!< program name to be displayed on CLI */
		public static final String PROGRAM_NAME = "NumOfPapersLastNYears";
		/**!< program description to be displayed on CLI */
		public static final String PROGRAM_DESCRIPTION =
				"Find number of papers in last N years for each author";
		/**!< input hbase table option short */
		public static final String INPUT_TABLE_OPT_SHORT = "-it";
		/**!< input hbase table option long */
		public static final String INPUT_TABLE_OPT_LONG = "--inputTable";
		/**!< input hbase table option description */
		public static final String INPUT_TABLE_OPT_DESCRIPTION =
				"HBase table to read data from";
		/**!< output type option short */
		public static final String OUTPUT_OPT_SHORT = "-o";
		/**!< output type option long */
		public static final String OUTPUT_OPT_LONG = "--output";
		/**!< output type option description */
		public static final String OUTPUT_OPT_DESCRIPTION =
				"Specify output target for reducer";
		/**!< output target type option for hbase */
		public static final String OUTPUT_OPT_HBASE_CHOICE = "hbase";
		/**!< output target type option for filesystem */
		public static final String OUTPUT_OPT_FS_CHOICE = "filesystem";
		/**!< output hbase table option short */
		public static final String OUTPUT_TABLE_OPT_SHORT = "-ot";
		/**!< output hbase table option long */
		public static final String OUTPUT_TABLE_OPT_LONG = "--outputTable";
		/**!< output hbase table option description */
		public static final String OUTPUT_TABLE_OPT_DESCRIPTION =
				"HBase table to write summary data to";
		/**!< output filesystem file option short */
		public static final String OUTPUT_FILE_OPT_SHORT = "-of";
		/**!< output filesystem file option long */
		public static final String OUTPUT_FILE_OPT_LONG = "--outputFile";
		/**!< output filesystem file option description */
		public static final String OUTPUT_FILE_OPT_DESCRIPTION =
				"FileSystem file to write summary data to";
		/**!< input hbase table argument */
		public static final String INPUT_TABLE_ARG = "inputTable";
		/**!< output type argument */
		public static final String OUTPUT_FORMAT_ARG = "output";
		/**!< output hbase table argument */
		public static final String OUTPUT_TABLE_ARG = "outputTable";
		/**!< output filesystem file argument */
		public static final String OUTPUT_FILE_ARG = "outputFile";
	}
}
