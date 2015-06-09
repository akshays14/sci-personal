package com.sciencescape.ds.db.mysql.coredb;

/**
 * Exception for MySQL specific errors.
 *
 * @author akshay
 */
public class CoreDBOpException extends Exception {
	/**
	 * just to satisfy the interface.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Private constructor to avoid instantiation without message
	 * string or throwable instance.
	 */
	@SuppressWarnings("unused")
	private CoreDBOpException() { }

	/**
	 * @brief constructor for {@link CoreDBOpException} class
	 * @param message error message in string format
	 *
	 * Constructor for {@link CoreDBOpException} class.
	 */
	public CoreDBOpException(final String message) {
		super(message);
	}
	
	/**
	 * Constructs a new {@code CoreDBOpException} with specified
	 * nested {@code Throwable}.
	 *
	 * @param cause the exception or error that caused this exception
	 */
	public CoreDBOpException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code CoreDBOpException} with specified
	 * detail message and nested {@code Throwable}.
	 *
	 * @param message the error message
	 * @param cause the exception or error that caused this exception
	 */
	public CoreDBOpException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
