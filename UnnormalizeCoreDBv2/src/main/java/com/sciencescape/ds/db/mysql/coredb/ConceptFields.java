package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to fields entity.
 *
 * @author akshay
 * @version 0.1
 */
public class ConceptFields {

	private long id;
	private String idSource;
	private String name;
	//private String description;
	private boolean isActive;
	private boolean isGeneric;
	private float specificityScore;
	private String conceptSource;
	private String conceptDateImported;
	private String idConceptRaw;
	private String conceptSourceType;
	private String mappingSource;
	private int idSection;
	private int idSentence;
	private int count;
	private String rawMention;
	private float confidenceScore;
	private int confidenceLevel;
	private String mappingDateImported;
	/**
	 * @param id
	 * @param idSource
	 * @param name
	 * @param isActive
	 * @param isGeneric
	 * @param specificityScore
	 * @param conceptSource
	 * @param conceptDateImported
	 * @param idConceptRaw
	 * @param conceptSourceType
	 * @param mappingSource
	 * @param idSection
	 * @param idSentence
	 * @param count
	 * @param rawMention
	 * @param confidenceScore
	 * @param confidenceLevel
	 * @param mappingDateImported
	 */
	public ConceptFields(long id, String idSource, String name,
			boolean isActive, boolean isGeneric, float specificityScore,
			String conceptSource, String conceptDateImported,
			String idConceptRaw, String conceptSourceType,
			String mappingSource, int idSection, int idSentence, int count,
			String rawMention, float confidenceScore, int confidenceLevel,
			String mappingDateImported) {
		this.id = id;
		this.idSource = idSource;
		this.name = name;
		this.isActive = isActive;
		this.isGeneric = isGeneric;
		this.specificityScore = specificityScore;
		this.conceptSource = conceptSource;
		this.conceptDateImported = conceptDateImported;
		this.idConceptRaw = idConceptRaw;
		this.conceptSourceType = conceptSourceType;
		this.mappingSource = mappingSource;
		this.idSection = idSection;
		this.idSentence = idSentence;
		this.count = count;
		this.rawMention = rawMention;
		this.confidenceScore = confidenceScore;
		this.confidenceLevel = confidenceLevel;
		this.mappingDateImported = mappingDateImported;
	}
	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}
	/**
	 * @return the idSource
	 */
	public final String getIdSource() {
		return idSource;
	}
	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @return the isActive
	 */
	public final boolean isActive() {
		return isActive;
	}
	/**
	 * @return the isGeneric
	 */
	public final boolean isGeneric() {
		return isGeneric;
	}
	/**
	 * @return the specificityScore
	 */
	public final float getSpecificityScore() {
		return specificityScore;
	}
	/**
	 * @return the conceptSource
	 */
	public final String getConceptSource() {
		return conceptSource;
	}
	/**
	 * @return the conceptDateImported
	 */
	public final String getConceptDateImported() {
		return conceptDateImported;
	}
	/**
	 * @return the idConceptRaw
	 */
	public final String getIdConceptRaw() {
		return idConceptRaw;
	}
	/**
	 * @return the conceptSourceType
	 */
	public final String getConceptSourceType() {
		return conceptSourceType;
	}
	/**
	 * @return the mappingSource
	 */
	public final String getMappingSource() {
		return mappingSource;
	}
	/**
	 * @return the idSection
	 */
	public final int getIdSection() {
		return idSection;
	}
	/**
	 * @return the idSentence
	 */
	public final int getIdSentence() {
		return idSentence;
	}
	/**
	 * @return the count
	 */
	public final int getCount() {
		return count;
	}
	/**
	 * @return the rawMention
	 */
	public final String getRawMention() {
		return rawMention;
	}
	/**
	 * @return the confidenceScore
	 */
	public final float getConfidenceScore() {
		return confidenceScore;
	}
	/**
	 * @return the confidenceLevel
	 */
	public final int getConfidenceLevel() {
		return confidenceLevel;
	}
	/**
	 * @return the mappingDateImported
	 */
	public final String getMappingDateImported() {
		return mappingDateImported;
	}
}
