package com.sciencescape.ds.db.mysql.coredb;

/**
 * Class to contain fields related to paper entity.
 *
 * @author akshay
 * @version 0.1
 */
public class PaperFields {
    /** !< paper id */
    private long id;
    /** !< pubmed id */
    private long pmId;
    /** !< doi of the paper */
    private String doi;
    /** !< title of the paper */
    private String title;
    /** !< date paper meta-data was released */
    private String datePmReleased;
    /** !< issn number */
    private String issn;
    /** !< publication year */
    private int year;
    /** !< publication month */
    private int month;
    /** !< publication day */
    private int day;
    /** !< iso number */
    private String iso;
    /** !< issue number */
    private String issue;
    /** !< paper's language */
    private String language;
    /** !< paper's language */
    private String type;
    /** !< name of the journal */
    private String journalName;
    /** !< paper's eigen-factor */
    private float eigenFactor;
    /** !< country of publishing */
    private String country;
    /** !< volumne of journal */
    private String volume;
    /** !< nlm Id */
    private long nlmId;
    /** !< source of meta-data */
    private String metadataSource;
    /** !< paper's abstract */
    private String abstractText;
    /** !< fields of the paper */
    private String fields;
    /** !< date paper was imported */
    private String dateImported;

    /**
     * @param pId paper id
     * @param pPmId pubmed id
     * @param pDoi doi of the paper
     * @param pTitle title of the paper
     * @param pDatePmReleased date paper meta-data was released
     * @param pIssn issn number
     * @param pYear publication year
     * @param pMonth publication month
     * @param pDay publication day
     * @param pIso iso number
     * @param pIssue issue number
     * @param pLanguage paper's language
     * @param pType paper's language
     * @param pJournalName name of the journal
     * @param pEigenFactor paper's eigen-factor
     * @param pCountry country of publishing
     * @param pVolume volumne of journal
     * @param pNlmId nlm Id
     * @param pmetadataSource source of meta-data
     * @param pAbstractText paper's abstract
     * @param pFields fields of the paper
     * @param pDateImported date paper was imported
     */
    public PaperFields(final long pId, final long pPmId, final String pDoi,
            final String pTitle, final String pDatePmReleased,
            final String pIssn, final int pYear, final int pMonth,
            final int pDay, final String pIso, final String pIssue,
            final String pLanguage, final String pType,
            final String pJournalName, final float pEigenFactor,
            final String pCountry, final String pVolume, final long pNlmId,
            final String pmetadataSource, final String pAbstractText,
            final String pFields, final String pDateImported) {
        this.id = pId;
        this.pmId = pPmId;
        this.doi = pDoi;
        this.title = pTitle;
        this.datePmReleased = pDatePmReleased;
        this.issn = pIssn;
        this.year = pYear;
        this.month = pMonth;
        this.day = pDay;
        this.iso = pIso;
        this.issue = pIssue;
        this.language = pLanguage;
        this.type = pType;
        this.journalName = pJournalName;
        this.eigenFactor = pEigenFactor;
        this.country = pCountry;
        this.volume = pVolume;
        this.nlmId = pNlmId;
        this.metadataSource = pmetadataSource;
        this.abstractText = pAbstractText;
        this.fields = pFields;
        this.dateImported = pDateImported;
    }

    /**
     * @return the id
     */
    public final long getId() {
        return id;
    }

    /**
     * @param pId the id to set
     */
    public final void setId(final long pId) {
        this.id = pId;
    }

    /**
     * @return the pmId
     */
    public final long getPmId() {
        return pmId;
    }

    /**
     * @param pPmId the pmId to set
     */
    public final void setPmId(final long pPmId) {
        this.pmId = pPmId;
    }

    /**
     * @return the doi
     */
    public final String getDoi() {
        return doi;
    }

    /**
     * @param pDoi the doi to set
     */
    public final void setDoi(final String pDoi) {
        this.doi = pDoi;
    }

    /**
     * @return the title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @param pTitle the title to set
     */
    public final void setTitle(final String pTitle) {
        this.title = pTitle;
    }

    /**
     * @return the datePmReleased
     */
    public final String getDatePmReleased() {
        return datePmReleased;
    }

    /**
     * @param pDatePmReleased the datePmReleased to set
     */
    public final void setDatePmReleased(final String pDatePmReleased) {
        this.datePmReleased = pDatePmReleased;
    }

    /**
     * @return the issn
     */
    public final String getIssn() {
        return issn;
    }

    /**
     * @param pIssn the issn to set
     */
    public final void setIssn(final String pIssn) {
        this.issn = pIssn;
    }

    /**
     * @return the year
     */
    public final int getYear() {
        return year;
    }

    /**
     * @param pYear the year to set
     */
    public final void setYear(final int pYear) {
        this.year = pYear;
    }

    /**
     * @return the month
     */
    public final int getMonth() {
        return month;
    }

    /**
     * @param pMonth the month to set
     */
    public final void setMonth(final int pMonth) {
        this.month = pMonth;
    }

    /**
     * @return the day
     */
    public final int getDay() {
        return day;
    }

    /**
     * @param pDay the day to set
     */
    public final void setDay(final int pDay) {
        this.day = pDay;
    }

    /**
     * @return the iso
     */
    public final String getIso() {
        return iso;
    }

    /**
     * @param pIso the iso to set
     */
    public final void setIso(final String pIso) {
        this.iso = pIso;
    }

    /**
     * @return the issue
     */
    public final String getIssue() {
        return issue;
    }

    /**
     * @param pIssue the issue to set
     */
    public final void setIssue(final String pIssue) {
        this.issue = pIssue;
    }

    /**
     * @return the language
     */
    public final String getLanguage() {
        return language;
    }

    /**
     * @param pLanguage the language to set
     */
    public final void setLanguage(final String pLanguage) {
        this.language = pLanguage;
    }

    /**
     * @return the type
     */
    public final String getType() {
        return type;
    }

    /**
     * @param pType the type to set
     */
    public final void setType(final String pType) {
        this.type = pType;
    }

    /**
     * @return the journalName
     */
    public final String getJournalName() {
        return journalName;
    }

    /**
     * @param pJournalName the journalName to set
     */
    public final void setJournalName(final String pJournalName) {
        this.journalName = pJournalName;
    }

    /**
     * @return the eigenFactor
     */
    public final float getEigenFactor() {
        return eigenFactor;
    }

    /**
     * @param pEigenFactor the eigenFactor to set
     */
    public final void setEigenFactor(final float pEigenFactor) {
        this.eigenFactor = pEigenFactor;
    }

    /**
     * @return the country
     */
    public final String getCountry() {
        return country;
    }

    /**
     * @param pCountry the country to set
     */
    public final void setCountry(final String pCountry) {
        this.country = pCountry;
    }

    /**
     * @return the volume
     */
    public final String getVolume() {
        return volume;
    }

    /**
     * @param pVolume the volume to set
     */
    public final void setVolume(final String pVolume) {
        this.volume = pVolume;
    }

    /**
     * @return the nlmId
     */
    public final long getNlmId() {
        return nlmId;
    }

    /**
     * @param pNlmId the nlmId to set
     */
    public final void setNlmId(final long pNlmId) {
        this.nlmId = pNlmId;
    }

    /**
     * @return the metadataSource
     */
    public final String getMetadataSource() {
        return metadataSource;
    }

    /**
     * @param pMetadataSource the metadataSource to set
     */
    public final void setMetadataSource(final String pMetadataSource) {
        this.metadataSource = pMetadataSource;
    }

    /**
     * @return the abstractText
     */
    public final String getAbstractText() {
        return abstractText;
    }

    /**
     * @param pAbstractText the abstractText to set
     */
    public final void setAbstractText(final String pAbstractText) {
        this.abstractText = pAbstractText;
    }

    /**
     * @return the fields
     */
    public final String getFields() {
        return fields;
    }

    /**
     * @param pFields the fields to set
     */
    public final void setFields(final String pFields) {
        this.fields = pFields;
    }

    /**
     * @return the dateImported
     */
    public final String getDateImported() {
        return dateImported;
    }

    /**
     * @param pDateImported the dateImported to set
     */
    public final void setDateImported(final String pDateImported) {
        this.dateImported = pDateImported;
    }
}
