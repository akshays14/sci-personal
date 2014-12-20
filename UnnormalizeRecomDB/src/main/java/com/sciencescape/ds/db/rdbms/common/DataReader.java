package main.java.com.sciencescape.ds.db.rdbms.common;

import java.io.IOException;

/**
 * @interface DataReader DataReader.java 
 * @brief Object providing reading of data
 * @author akshay
 *
 * Generic interface for any data-source (or 
 * {@link DataContainer} that want to support reads.
 */
public interface DataReader extends DataContainer {
	/**
	 * @brief read records from a data-source
	 * @return DataRecord retrieved from the data-source
	 * @throws IOException
	 */
	public DataRecord readRecord() throws IOException;
}