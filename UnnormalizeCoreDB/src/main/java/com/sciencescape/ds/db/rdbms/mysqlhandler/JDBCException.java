package main.java.com.sciencescape.ds.db.rdbms.mysqlhandler;

/**
 * @class JDBCException JDBCException.java
 * @brief exception for JDBC specific error 
 * @author akshay
 *
 * Exception for JDBC specific errors.
 */
public class JDBCException extends Exception {
	/**
	 * just to satisfy the interface
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @brief constructor for {@link JDBCException} class
	 * @param message error message in string format
	 * 
	 * Constructor for {@link JDBCException} class.
	 */
	public JDBCException(String message) {
		super(message);
	}
}