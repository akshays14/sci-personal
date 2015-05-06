package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to fields entity.
 *
 * @author akshay
 * @version 0.1
 */
public class FieldsFields {

	/**!< name of the field */
	private String name;

	/**
	 * Creates an {@code FieldsFields} object with given parameters.
	 *
	 * @param pName field name
	 */
	public FieldsFields(final String pName) {
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
