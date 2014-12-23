package com.sciencescape.ds.db.util;

/**
 * Exception class for all utility related errors.
 *
 * @author akshay
 */
public class UtilityException extends Exception {

	/**
	 * Default version Id to satisfy interface.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Default constructor without any parameter.
	 */
	public UtilityException() {
		super();
	}

	/**
	 * Constructor for FormatNotSupported.
	 *
	 * @param msg string message
	 */
	public UtilityException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor for FormatNotSupported with formatted message.
	 *
	 * @param msg string pattern
	 * @param args variable arguments
	 */
	public UtilityException(final String msg,
			final String... args) {
		super(String.format(msg, (Object[]) args));
	}
}
