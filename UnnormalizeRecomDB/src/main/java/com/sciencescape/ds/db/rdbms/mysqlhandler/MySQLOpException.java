package main.java.com.sciencescape.ds.db.rdbms.mysqlhandler;

/**
 * @class MySQLOpException MySQLOpException.java
 * @brief exception for MySQL operation errors 
 * @author akshay
 *
 * Exception for MySQL operation errors.
 */
public class MySQLOpException extends Exception {
	/**
	 * just to satisfy the interface
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @brief constructor for {@link MySQLOpException} class
	 * @param message error message in string format
	 * 
	 * Constructor for {@link MySQLOpException} class.
	 */
	public MySQLOpException(String message) {
		super(message);
	}
}