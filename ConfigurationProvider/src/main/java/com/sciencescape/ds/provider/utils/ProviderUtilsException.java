package com.sciencescape.ds.provider.utils;

import com.sciencescape.ds.provider.ProviderException;

public class ProviderUtilsException extends ProviderException {

	/**
	 * Default version Id to satisfy interface.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for simple string message.
	 *
	 * @param msg string message
	 */
	public ProviderUtilsException(final String msg) {
		super(msg);
	}

	/**
	 * Constructor for formatted message.
	 *
	 * @param msg string pattern
	 * @param args variable arguments
	 */
	public ProviderUtilsException(final String msg,
			final String... args) {
		super(msg, args);
	}
}
