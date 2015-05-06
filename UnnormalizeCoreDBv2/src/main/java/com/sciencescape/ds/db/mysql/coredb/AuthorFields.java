package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to author entity.
 *
 * @author akshay
 * @version 0.1
 */
public class AuthorFields {

	/**!< name of the author */
	private String name;

	/**
	 * Creates an {@code AuthorFields} object with given parameters.
	 *
	 * @param pName author name
	 */
	public AuthorFields(final String pName) {
		this.name = pName;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param pName the name to set
	 */
	public final void setName(final String pName) {
		this.name = pName;
	}
}
