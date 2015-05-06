package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to institution entity.
 *
 * @author akshay
 * @version 0.1
 */
public class InstitutionFields {

	/**!< raw affiliation string */
	private String rawAffiliationName;
	/**!< normalized affiliation string */
	private String normalizedAffiliationName;

	/**
	 * Creates an {@code InstitutionFields} object with given parameters.
	 *
	 * @param pRawAffiliationName raw affiliation string
	 * @param pNormalizedAffiliationName normalized affiliation string
	 */
	public InstitutionFields(final String pRawAffiliationName,
			final String pNormalizedAffiliationName) {
		this.rawAffiliationName = pRawAffiliationName;
		this.normalizedAffiliationName = pNormalizedAffiliationName;
	}

	/**
	 * @return the rawAffiliationName
	 */
	public final String getRawAffiliationName() {
		return rawAffiliationName;
	}

	/**
	 * @param pRawAffiliationName the rawAffiliationName to set
	 */
	public final void setRawAffiliationName(final String pRawAffiliationName) {
		this.rawAffiliationName = pRawAffiliationName;
	}

	/**
	 * @return the normalizedAffiliationName
	 */
	public final String getNormalizedAffiliationName() {
		return normalizedAffiliationName;
	}

	/**
	 * @param pNormalizedAffiliationName the normalizedAffiliationName to set
	 */
	public final void setNormalizedAffiliationName(
			final String pNormalizedAffiliationName) {
		this.normalizedAffiliationName = pNormalizedAffiliationName;
	}
}
