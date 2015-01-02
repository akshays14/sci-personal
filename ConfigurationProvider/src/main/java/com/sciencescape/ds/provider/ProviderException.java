package com.sciencescape.ds.provider;

/**
 * Exception class for all provider related errors.
 *
 * @author akshay
 */
public class ProviderException extends Exception {

	/**
	 * Default version Id to satisfy interface.
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Default constructor without any parameter.
	 */
	public ProviderException() {
		super();
	}

	/**
	 * Constructor for simple string message.
	 *
	 * @param msg string message
	 */
	public ProviderException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor for formatted message.
	 *
	 * @param msg string pattern
	 * @param args variable arguments
	 */
	public ProviderException(final String msg,
			final String... args) {
		super(String.format(msg, (Object[]) args));
	}
}
