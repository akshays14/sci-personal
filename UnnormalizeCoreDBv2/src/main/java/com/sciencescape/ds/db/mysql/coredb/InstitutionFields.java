package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to institution entity.
 *
 * @author akshay
 * @version 0.1
 */
public class InstitutionFields {
	private long id;
	private  String name;
	private  String externalName;
	private  String city;
	private  String externalCity;
	private  String zipcode;
	private  String country;
	private  String state;
	private  String type;
	private  long idParent;
	private  long idParentHighest;
	private  float eigenfactor;
	private  String institutionSource;
	private  String institutionDateImported;
	private int authorIndex;
	private int indexAffiliation;
	private String protoAffiliation;
	private float solrScore;
	private String crfSegmentation;
	private int confidenceLevel;
	private String mappingSource;
	private String mappingDateImported;
	/**
	 * @param id
	 * @param name
	 * @param externalName
	 * @param city
	 * @param externalCity
	 * @param zipcode
	 * @param country
	 * @param state
	 * @param type
	 * @param idParent
	 * @param idParentHighest
	 * @param eigenfactor
	 * @param institutionSource
	 * @param institutionDateImported
	 * @param authorIndex
	 * @param indexAffiliation
	 * @param protoAffiliation
	 * @param solrScore
	 * @param crfSegmentation
	 * @param confidenceLevel
	 * @param mappingSource
	 * @param mappingDateImported
	 */
	public InstitutionFields(long id, String name, String externalName,
			String city, String externalCity, String zipcode, String country,
			String state, String type, long idParent, long idParentHighest,
			float eigenfactor, String institutionSource,
			String institutionDateImported, int authorIndex,
			int indexAffiliation, String protoAffiliation, float solrScore,
			String crfSegmentation, int confidenceLevel, String mappingSource,
			String mappingDateImported) {
		this.id = id;
		this.name = name;
		this.externalName = externalName;
		this.city = city;
		this.externalCity = externalCity;
		this.zipcode = zipcode;
		this.country = country;
		this.state = state;
		this.type = type;
		this.idParent = idParent;
		this.idParentHighest = idParentHighest;
		this.eigenfactor = eigenfactor;
		this.institutionSource = institutionSource;
		this.institutionDateImported = institutionDateImported;
		this.authorIndex = authorIndex;
		this.indexAffiliation = indexAffiliation;
		this.protoAffiliation = protoAffiliation;
		this.solrScore = solrScore;
		this.crfSegmentation = crfSegmentation;
		this.confidenceLevel = confidenceLevel;
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
	 * @return the externalName
	 */
	public final String getExternalName() {
		return externalName;
	}
	/**
	 * @param externalName the externalName to set
	 */
	public final void setExternalName(String externalName) {
		this.externalName = externalName;
	}
	/**
	 * @return the city
	 */
	public final String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public final void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the externalCity
	 */
	public final String getExternalCity() {
		return externalCity;
	}
	/**
	 * @param externalCity the externalCity to set
	 */
	public final void setExternalCity(String externalCity) {
		this.externalCity = externalCity;
	}
	/**
	 * @return the zipcode
	 */
	public final String getZipcode() {
		return zipcode;
	}
	/**
	 * @param zipcode the zipcode to set
	 */
	public final void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * @return the country
	 */
	public final String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public final void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the state
	 */
	public final String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public final void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public final void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the idParent
	 */
	public final long getIdParent() {
		return idParent;
	}
	/**
	 * @param idParent the idParent to set
	 */
	public final void setIdParent(long idParent) {
		this.idParent = idParent;
	}
	/**
	 * @return the idParentHighest
	 */
	public final long getIdParentHighest() {
		return idParentHighest;
	}
	/**
	 * @param idParentHighest the idParentHighest to set
	 */
	public final void setIdParentHighest(long idParentHighest) {
		this.idParentHighest = idParentHighest;
	}
	/**
	 * @return the eigenfactor
	 */
	public final float getEigenfactor() {
		return eigenfactor;
	}
	/**
	 * @param eigenfactor the eigenfactor to set
	 */
	public final void setEigenfactor(float eigenfactor) {
		this.eigenfactor = eigenfactor;
	}
	/**
	 * @return the institutionSource
	 */
	public final String getInstitutionSource() {
		return institutionSource;
	}
	/**
	 * @param institutionSource the institutionSource to set
	 */
	public final void setInstitutionSource(String institutionSource) {
		this.institutionSource = institutionSource;
	}
	/**
	 * @return the institutionDateImported
	 */
	public final String getInstitutionDateImported() {
		return institutionDateImported;
	}
	/**
	 * @param institutionDateImported the institutionDateImported to set
	 */
	public final void setInstitutionDateImported(String institutionDateImported) {
		this.institutionDateImported = institutionDateImported;
	}
	/**
	 * @return the authorIndex
	 */
	public final int getAuthorIndex() {
		return authorIndex;
	}
	/**
	 * @param authorIndex the authorIndex to set
	 */
	public final void setAuthorIndex(int authorIndex) {
		this.authorIndex = authorIndex;
	}
	/**
	 * @return the indexAffiliation
	 */
	public final int getIndexAffiliation() {
		return indexAffiliation;
	}
	/**
	 * @param indexAffiliation the indexAffiliation to set
	 */
	public final void setIndexAffiliation(int indexAffiliation) {
		this.indexAffiliation = indexAffiliation;
	}
	/**
	 * @return the protoAffiliation
	 */
	public final String getProtoAffiliation() {
		return protoAffiliation;
	}
	/**
	 * @param protoAffiliation the protoAffiliation to set
	 */
	public final void setProtoAffiliation(String protoAffiliation) {
		this.protoAffiliation = protoAffiliation;
	}
	/**
	 * @return the solrScore
	 */
	public final float getSolrScore() {
		return solrScore;
	}
	/**
	 * @param solrScore the solrScore to set
	 */
	public final void setSolrScore(float solrScore) {
		this.solrScore = solrScore;
	}
	/**
	 * @return the crfSegmentation
	 */
	public final String getCrfSegmentation() {
		return crfSegmentation;
	}
	/**
	 * @param crfSegmentation the crfSegmentation to set
	 */
	public final void setCrfSegmentation(String crfSegmentation) {
		this.crfSegmentation = crfSegmentation;
	}
	/**
	 * @return the confidenceLevel
	 */
	public final int getConfidenceLevel() {
		return confidenceLevel;
	}
	/**
	 * @param confidenceLevel the confidenceLevel to set
	 */
	public final void setConfidenceLevel(int confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
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
