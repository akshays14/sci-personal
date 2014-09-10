package main.java.com.sciencescape.ds.db.rdbms.common;

import java.io.IOException;

/**
 * @interface DataWriter DataWriter.java 
 * @brief Object providing writing of data
 * @author akshay
 *
 * Generic interface for any data-source that
 * want to support writes.
 */
public interface DataWriter extends DataContainer {
	/**
	 * @brief write record to a data-source
	 * @param rec DataRecord to be written to data-source
	 * @throws IOException
	 */
	public void writeRecord(DataRecord rec) throws IOException;
}