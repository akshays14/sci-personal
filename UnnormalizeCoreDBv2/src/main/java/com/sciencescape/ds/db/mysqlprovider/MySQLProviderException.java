package com.sciencescape.ds.db.mysqlprovider;

/**
 * Exception for MySQL specific errors.
 *
 * @author akshay
 */
public class MySQLProviderException extends Exception {
	/**
	 * just to satisfy the interface.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Private constructor to avoid instantiation without message
	 * string or throwable instance.
	 */
	@SuppressWarnings("unused")
	private MySQLProviderException() { }

	/**
	 * @brief constructor for {@link MySQLProviderException} class
	 * @param message error message in string format
	 *
	 * Constructor for {@link MySQLProviderException} class.
	 */
	public MySQLProviderException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code MySQLProviderException} with specified
	 * nested {@code Throwable}.
	 *
	 * @param cause the exception or error that caused this exception
	 */
	public MySQLProviderException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code MySQLProviderException} with specified
	 * detail message and nested {@code Throwable}.
	 *
	 * @param message the error message
	 * @param cause the exception or error that caused this exception
	 */
	public MySQLProviderException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
