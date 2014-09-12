package main.java.com.sciencescape.ds.db.rdbms.common;

import java.io.IOException;

/**
 * @interface DataContainer DataContainer.java
 * @brief Encapsulates a source of data
 * @author akshay
 *
 * Generic interface for objects which encapsulates 
 * a data-source (e.g., databases).
 */
public interface DataContainer {
	/**
	 * @brief connects to a data-source 
	 * @throws IOException
	 * 
	 * Method to connect to a data-source.
	 */
    public void connect() throws IOException;
    
    /**
     * @brief close connection to a data-store
     * @throws IOException
     * 
     * Method to close connection to a data-source.
     */
    public void close() throws IOException;
}