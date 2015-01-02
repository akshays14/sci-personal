package com.sciencescape.ds.provider.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import com.sciencescape.ds.provider.Constants;


/**
 * @author akshay
 *
 */
public final class Utilities {

	/**
	 * Method to set user messages configurations.
	 *
	 * @param language language code, or a language sub-tag
	 * @param country country code or an area code.
	 * @param baseName the base name of the resource bundle
	 * @return {@link ResourceBundle} object
	 * @throws ProviderUtilsException wrapping all native exceptions
	 */
	public static ResourceBundle configureUserMessages(final String language,
			final String country, final String baseName)
					throws ProviderUtilsException {
		Locale locale = new Locale(language, country);
		try {
			return ResourceBundle.getBundle(baseName, locale);
		} catch (Exception e) {
			throw new ProviderUtilsException(e.getMessage());
		}
	}

	/**
	 * Method to set user messages configurations.
	 *
	 * @throws ProviderUtilsException wrapping all native exceptions
	 */
	public static ResourceBundle configureUserMessages()
			throws ProviderUtilsException {
		return configureUserMessages(Constants.UserMessages.DEFAULT_LANGUAGE,
				Constants.UserMessages.DEFAULT_COUNTRY,
				Constants.UserMessages.DEFAULT_RB_BASE_NAME);
	}
}