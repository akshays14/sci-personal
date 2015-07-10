package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to fields entity.
 *
 * @author akshay
 * @version 0.1
 */
public class CitationFields {

	private long id;
	private String doiFromPaper;
	private String doiToPaper;
	private long idFromPaper;
	private long idToPaper;
	private int year;
	private int month;
	private float confidenceScore;
	private int confidenceLevel;
	private String source;
	private String dateImported;
	
	/**
	 * @param id
	 * @param doiFromPaper
	 * @param doiToPaper
	 * @param idFromPaper
	 * @param idToPaper
	 * @param year
	 * @param month
	 * @param confidenceScore
	 * @param confidenceLevel
	 * @param source
	 * @param dateImported
	 */
	public CitationFields(long id, String doiFromPaper, String doiToPaper,
			long idFromPaper, long idToPaper, int year, int month,
			float confidenceScore, int confidenceLevel, String source,
			String dateImported) {
		this.id = id;
		this.doiFromPaper = doiFromPaper;
		this.doiToPaper = doiToPaper;
		this.idFromPaper = idFromPaper;
		this.idToPaper = idToPaper;
		this.year = year;
		this.month = month;
		this.confidenceScore = confidenceScore;
		this.confidenceLevel = confidenceLevel;
		this.source = source;
		this.dateImported = dateImported;
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
	 * @return the doiFromPaper
	 */
	public final String getDoiFromPaper() {
		return doiFromPaper;
	}

	/**
	 * @param doiFromPaper the doiFromPaper to set
	 */
	public final void setDoiFromPaper(String doiFromPaper) {
		this.doiFromPaper = doiFromPaper;
	}

	/**
	 * @return the doiToPaper
	 */
	public final String getDoiToPaper() {
		return doiToPaper;
	}

	/**
	 * @param doiToPaper the doiToPaper to set
	 */
	public final void setDoiToPaper(String doiToPaper) {
		this.doiToPaper = doiToPaper;
	}

	/**
	 * @return the idFromPaper
	 */
	public final long getIdFromPaper() {
		return idFromPaper;
	}

	/**
	 * @param idFromPaper the idFromPaper to set
	 */
	public final void setIdFromPaper(long idFromPaper) {
		this.idFromPaper = idFromPaper;
	}

	/**
	 * @return the idToPaper
	 */
	public final long getIdToPaper() {
		return idToPaper;
	}

	/**
	 * @param idToPaper the idToPaper to set
	 */
	public final void setIdToPaper(long idToPaper) {
		this.idToPaper = idToPaper;
	}

	/**
	 * @return the year
	 */
	public final int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public final void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public final int getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public final void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the confidenceScore
	 */
	public final float getConfidenceScore() {
		return confidenceScore;
	}

	/**
	 * @param confidenceScore the confidenceScore to set
	 */
	public final void setConfidenceScore(float confidenceScore) {
		this.confidenceScore = confidenceScore;
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
	 * @return the source
	 */
	public final String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public final void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the dateImported
	 */
	public final String getDateImported() {
		return dateImported;
	}

	/**
	 * @param dateImported the dateImported to set
	 */
	public final void setDateImported(String dateImported) {
		this.dateImported = dateImported;
	}
	
}
