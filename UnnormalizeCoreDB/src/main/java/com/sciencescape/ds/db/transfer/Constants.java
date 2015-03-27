package main.java.com.sciencescape.ds.db.transfer;

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
		public static final String PROGRAM_DESCRIPTION = "De-normalize "
				+ "core-db tables to update paper table in HBase";
		/**!< Output hbase table short option */
		public static final String OUTPUT_TABLE_OPT_SHORT = "-ot";
		/**!< Output hbase table long option */
		public static final String OUTPUT_TABLE_OPT_LONG =
				"--outputHBaseTable";
		public static final String OUTPUT_TABLE_OPT_DESCRIPTION = "HBase table to write de-normalized data to";

		public static final String PAPER_PUBLICATION_YEAR_OPT_SHORT = "-y";
		public static final String PAPER_PUBLICATION_YEAR_OPT_LONG= "-yearOfPaperPublication";
		public static final String PAPER_PUBLICATION_YEAR_OPT_DESCRIPTION = "Calendar year in which papers (to be imported) were published ";
	}
}
