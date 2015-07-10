package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to author entity.
 *
 * @author akshay
 * @version 0.1
 */
public class AuthorFields {

	private long id;
	/**!< name of the author */
	private String name;
	private String homepage;
	private String authorSource;
	private String authorDateImported;
	private String orcidId;
	private int index;
	private String rawName;
	private String rawAffiliation;
	private float disambiguationScore;
	private String mappingSource;
	private String mappingDateImported;

	/**
	 * @param id
	 * @param name
	 * @param homepage
	 * @param authorSource
	 * @param authorDateImported
	 * @param orcidId
	 * @param index
	 * @param rawName
	 * @param rawAffiliation
	 * @param disambiguationScore
	 * @param mappingSource
	 * @param mappingDateImported
	 */
	public AuthorFields(long id, String name, String homepage,
			String authorSource, String authorDateImported, String orcidId,
			int index, String rawName, String rawAffiliation,
			float disambiguationScore, String mappingSource,
			String mappingDateImported) {
		this.id = id;
		this.name = name;
		this.homepage = homepage;
		this.authorSource = authorSource;
		this.authorDateImported = authorDateImported;
		this.orcidId = orcidId;
		this.index = index;
		this.rawName = rawName;
		this.rawAffiliation = rawAffiliation;
		this.disambiguationScore = disambiguationScore;
		this.mappingSource = mappingSource;
		this.mappingDateImported = mappingDateImported;
	}

	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the homepage
	 */
	public final String getHomepage() {
		return homepage;
	}

	/**
	 * @param homepage the homepage to set
	 */
	public final void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return the authorSource
	 */
	public final String getAuthorSource() {
		return authorSource;
	}

	/**
	 * @param authorSource the authorSource to set
	 */
	public final void setAuthorSource(String authorSource) {
		this.authorSource = authorSource;
	}

	/**
	 * @return the authorDateImported
	 */
	public final String getAuthorDateImported() {
		return authorDateImported;
	}

	/**
	 * @param authorDateImported the authorDateImported to set
	 */
	public final void setAuthorDateImported(String authorDateImported) {
		this.authorDateImported = authorDateImported;
	}

	/**
	 * @return the orcidId
	 */
	public final String getOrcidId() {
		return orcidId;
	}

	/**
	 * @param orcidId the orcidId to set
	 */
	public final void setOrcidId(String orcidId) {
		this.orcidId = orcidId;
	}

	/**
	 * @return the index
	 */
	public final int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public final void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the rawName
	 */
	public final String getRawName() {
		return rawName;
	}

	/**
	 * @param rawName the rawName to set
	 */
	public final void setRawName(String rawName) {
		this.rawName = rawName;
	}

	/**
	 * @return the rawAffiliation
	 */
	public final String getRawAffiliation() {
		return rawAffiliation;
	}

	/**
	 * @param rawAffiliation the rawAffiliation to set
	 */
	public final void setRawAffiliation(String rawAffiliation) {
		this.rawAffiliation = rawAffiliation;
	}

	/**
	 * @return the disambiguationScore
	 */
	public final float getDisambiguationScore() {
		return disambiguationScore;
	}

	/**
	 * @param disambiguationScore the disambiguationScore to set
	 */
	public final void setDisambiguationScore(float disambiguationScore) {
		this.disambiguationScore = disambiguationScore;
	}

	/**
	 * @return the mappingSource
	 */
	public final String getMappingSource() {
		return mappingSource;
	}

	/**
	 * @param mappingSource the mappingSource to set
	 */
	public final void setMappingSource(String mappingSource) {
		this.mappingSource = mappingSource;
	}

	/**
	 * @return the mappingDateImported
	 */
	public final String getMappingDateImported() {
		return mappingDateImported;
	}

	/**
	 * @param mappingDateImported the mappingDateImported to set
	 */
	public final void setMappingDateImported(String mappingDateImported) {
		this.mappingDateImported = mappingDateImported;
	}
}
