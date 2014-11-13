package com.sciencescape.ds.Exception;

/**
 * Exception for un-expcted argument.
 *
 * @author akshay
 */
public final class UnexpectedArgumentsException extends Exception {

	/**
	 * Default version Id to satisfy interface.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Private constructor to avoid instantiation without.
	 */
	@SuppressWarnings("unused")
	private UnexpectedArgumentsException() { }

	/**
	 * Constructor for FormatNotSupported.
	 *
	 * @param msg string message
	 */
	public UnexpectedArgumentsException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor for FormatNotSupported with formatted message.
	 *
	 * @param msg string pattern
	 * @param args variable arguments
	 */
	public UnexpectedArgumentsException(final String msg,
			final String... args) {
		super(String.format(msg, (Object[]) args));
	}
}
