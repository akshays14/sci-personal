package com.sciencescape.ds.db.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author akshay
 *
 */
public final class Utilities {

	/**
	 * Private constructor to avoid instantiating this utility class.
	 */
	private Utilities() { }

	/**
	 *
	 * @param resourceFileBaseName resource file family name containing messages
	 * @return ResourceBundle object containing values from given resource file
	 * @throws UtilityException if file base name is null or empty
	 */
	public static ResourceBundle getResourceBundle(
			final String resourceFileBaseName) throws UtilityException {
		if (resourceFileBaseName == null || resourceFileBaseName.isEmpty()) {
			throw new UtilityException(
					"ResourceBundle file-base cannot be null or empty");
		}
		Locale currentLocale = new Locale(
				Constants.UserMessages.DEFAULT_LANGUAGE,
				Constants.UserMessages.DEFAULT_COUNTRY);
		return ResourceBundle.getBundle(resourceFileBaseName, currentLocale);
	}
}
