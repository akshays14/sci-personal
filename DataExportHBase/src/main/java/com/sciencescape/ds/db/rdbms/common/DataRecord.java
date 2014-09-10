package main.java.com.sciencescape.ds.db.rdbms.common;

/**
 * @interface DataRecord DataRecord.java
 * @brief Object representing a data-record 
 * @author akshay
 *
 * Generic structure for representing a data-record.
 * For example, in case of a RDBMS it would represent
 * row-data.
 */
public class DataRecord {

	private Object[] _rData = null;
	
	/**
	 * @brief constructor taking object-array
	 * @param data object array containing data-values
	 * 
	 * Constructor to take a list of objects as argument,
	 * where each object represents value of a column.
	 * 
	 * @note the order of the columns should be preserver
	 * in every read/write operation and should be same
	 * as the order of columns provided while creating a 
	 * document (or table) in the data-source.
	 */
	public DataRecord(Object[] data) {
		_rData = data;
	}

	/**
	 * @brief retrieve value of a particular object
	 * @param index index of the target object (column)
	 * @return Object containing value of the specified column.
	 * 
	 * Method to retrieve value of a particular object
	 * with in the {@link DataContainer). So in case of 
	 * RDBMS it retrieves the value of column (specified
	 * by index) from the row.
	 */
	public Object getDataAt(int index) {
		return _rData[index];
	}
}