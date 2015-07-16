package com.sciencescape.ds.db.mysql.coredb;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @class DenormalizedFields DenormalizedFields.java
 * @brief container class for all de-normalized fields
 * @author Akshay
 *
 */
	// Fields from paper table
public class DenormalizedFields {

	private long _id;
	private long _pmId;
	private String _pmcId;
	private String _doi;
	private String _title;
	private String _datePmReleased;
	private String _issn;
	private int _year;
	private int _month;
	private int _day;
	private String _iso;
	private String _issue;
	private String _language;
	private String _type;
	private String _journalName;
	private float _eigenFactor;
	private String _country;
	private String _volume;
	private String _nlmId;
	private String _meshTerms;
	private String _keywords;
	private String _conceptsRaw;
	private int _numOfReferences;
	private String _grants;
	private String _source;
	
	// Fields from venue table
	private long _venueId;
	private String _vIsoAbbreviation;
	private String _vType;
	private String _vTitle;
	private String _vTitleWithoutDiacritics;
	//ignoring image for now
	private int _vYearStart;
	private int _vYearEnd;
	private String _vFrequency;
	private String _vLanguage;
	private String _vPublisher;
	private String _vSource;
	private String _vIsoWithoutDiacritic;
	private float _vEFMedianRaw;
	private float _vEFMedianNormalized;
	private String _vDateImported;
	private String _vMapSource;
	private float _vMapConfidenceScore;
	private int _vMapConfidenceLevel;
	private String _vMapDateImported;
	
	// Fields from author table
	private Map<Long, AuthorFields> _authors;
	// Field from institute table
	private Map<Long, InstitutionFields> _institution;
	// Fields from paper table
	private String _abstract;
	private String _abstractOther;
	// Fields from full-text
	private String[] _sections;
	// Fields from fields table
	private Map<Long, ConceptFields> _concepts;
	// Fields from incoming citation
	private Map<Long, CitationFields> _inCitations;
	// Fields from outgoing citation
	private Map<Long, CitationFields> _outCitations;
	// Field from paper table
	private String _dateImported;

	public void printFields(PrintStream stream) {
		if (stream == null) {
			return;
		}
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.ID, _id);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.PMID, _pmId);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.DOI, _doi);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.TITLE, _title);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.PM_DATE_RELEASED, _datePmReleased);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.ISSN, _issn);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.YEAR, _year);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.MONTH, _month);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.DAY, _day);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.ISO, _iso);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.ISSUE, _issue);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.LANGUAGE, _language);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.TYPE, _type);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.JOURNAL_NAME, _journalName);
		stream.printf(CoreDBConstants.Messages.MSG_FLOAT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.EIGENFACTOR, _eigenFactor);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.COUNTRY, _country);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.VOLUME, _volume);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT,
				CoreDBConstants.PaperFields.NLM_ID, _nlmId);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.METADATA_SOURCE, _source);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.VenueFields.PUBLISHER, _vPublisher);
		// print authors
		printAuthors(stream);
		// print institution info
		printInstitution(stream);
		// print fields info
		printFieldsInfo(stream);
	}

	public void printAuthors(PrintStream stream) {
		if (stream == null || _authors == null) {
			return;
		}
		for (Map.Entry<Long, AuthorFields> entry : _authors.entrySet()) {
			stream.printf(CoreDBConstants.Messages.MSG_AUTHOR_ID_FORMAT,
					entry.getKey());
			stream.printf(CoreDBConstants.Messages.MSG_AUTHOR_NAME_FORMAT,
					(entry.getValue()).getName());
		}
	}

	public void printInstitution(PrintStream stream) {
		if (stream == null || _institution == null) {
			return;
		}
		for (Map.Entry<Long, InstitutionFields> entry : _institution.entrySet()) {
			stream.printf(CoreDBConstants.Messages.MSG_INSTITUTION_ID_FORMAT,
					entry.getKey());
			/*stream.printf(
					CoreDBConstants.Messages.MSG_INSTITUTION_RAW_AFFILIATION_FORMAT,
					(entry.getValue()).getRawAffiliationName());
			stream.printf(
					CoreDBConstants.Messages.MSG_INSTITUTION_NORM_AFFILIATION_FORMAT,
					(entry.getValue()).getNormalizedAffiliationName());*/
		}
	}

	public void printFieldsInfo(PrintStream stream) {
		if (stream == null || _concepts == null) {
			return;
		}
		for (Map.Entry<Long, ConceptFields> entry : _concepts.entrySet()) {
			stream.printf(CoreDBConstants.Messages.MSG_FIELD_ID_FORMAT,
					entry.getKey());
			stream.printf(CoreDBConstants.Messages.MSG_FIELD_NAME_FORMAT,
					(entry.getValue()).getName());
		}
	}

	
	public void populatePaperFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		try {
			_id = rs.getLong(CoreDBConstants.PaperFields.ID);
			_pmId = rs.getLong(CoreDBConstants.PaperFields.PMID);
			_pmcId = rs.getString(CoreDBConstants.PaperFields.PMC_ID);
			_doi = rs.getString(CoreDBConstants.PaperFields.DOI);
			_title = rs.getString(CoreDBConstants.PaperFields.TITLE);
			_year = rs.getInt(CoreDBConstants.PaperFields.YEAR);
			_month = rs.getInt(CoreDBConstants.PaperFields.MONTH);
			_day = rs.getInt(CoreDBConstants.PaperFields.DAY);
			_issn = rs.getString(CoreDBConstants.PaperFields.ISSN);
			_iso = rs.getString(CoreDBConstants.PaperFields.ISO);
			_volume = rs.getString(CoreDBConstants.PaperFields.VOLUME);
			_issue = rs.getString(CoreDBConstants.PaperFields.ISSUE);
			_type = rs.getString(CoreDBConstants.PaperFields.TYPE);
			_abstract = rs.getString(CoreDBConstants.PaperFields.ABSTRACT);
			_abstractOther =
					rs.getString(CoreDBConstants.PaperFields.ABSTRACT_OTHER);
			// not doing 'authors_raw'
			_journalName = rs
						.getString(CoreDBConstants.PaperFields.JOURNAL_NAME);
			_nlmId = rs.getString(CoreDBConstants.PaperFields.NLM_ID);
			_meshTerms =
					rs.getString(CoreDBConstants.PaperFields.MESH_TERMS_RAW);
			_keywords = rs.getString(CoreDBConstants.PaperFields.KEYWORDS);
			_conceptsRaw = rs.getString(CoreDBConstants.PaperFields.CONCEPTS_RAW);
			_numOfReferences =
					rs.getInt(CoreDBConstants.PaperFields.NUM_OF_REFS);
			_grants =  rs.getString(CoreDBConstants.PaperFields.GRANTS);
			_country = rs.getString(CoreDBConstants.PaperFields.COUNTRY);
			_language = rs.getString(CoreDBConstants.PaperFields.LANGUAGE);
			_source = rs
					.getString(CoreDBConstants.PaperFields.METADATA_SOURCE);
			_eigenFactor = rs.getFloat(CoreDBConstants.PaperFields.EIGENFACTOR);
			_datePmReleased = rs
						.getString(CoreDBConstants.PaperFields.PM_DATE_RELEASED);
			_dateImported = rs
					.getString(CoreDBConstants.PaperFields.DATE_IMPORTED);
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void populateVenueFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		try {
			// move resultSet to first row
			while (rs.next()) {
				_venueId = rs
						.getLong(CoreDBConstants.VenueFields.VENUE_ID);
				_vIsoAbbreviation = rs
						.getString(CoreDBConstants.VenueFields.ISO_ABBREVIATION);
				_vType = rs
						.getString(CoreDBConstants.VenueFields.TYPE);
				_vTitle = rs
						.getString(CoreDBConstants.VenueFields.TITLE);
				_vTitleWithoutDiacritics = rs
						.getString(CoreDBConstants.VenueFields.TITLE_WITHOUT_DIACRITICS);
				//ignoring image for now
				_vYearStart = rs
						.getInt(CoreDBConstants.VenueFields.YEAR_START);
				_vYearEnd = rs
						.getInt(CoreDBConstants.VenueFields.YEAR_END);
				_vFrequency = rs
						.getString(CoreDBConstants.VenueFields.FREQUENCY);
				_vLanguage = rs
						.getString(CoreDBConstants.VenueFields.LANGUAGE);
				_vPublisher = rs
						.getString(CoreDBConstants.VenueFields.PUBLISHER);;
				_vSource = rs
						.getString(CoreDBConstants.VenueFields.SOURCE);
				_vIsoWithoutDiacritic = rs
						.getString(CoreDBConstants.VenueFields.ISO_WITHOUT_DIACRITICS);
				_vEFMedianRaw = rs
						.getFloat(CoreDBConstants.VenueFields.EF_MEDIAN_RAW);
				_vEFMedianNormalized = rs
						.getFloat(CoreDBConstants.VenueFields.EF_MEDIAN_NORMALIZED);
				_vDateImported = rs
						.getString(CoreDBConstants.VenueFields.DATE_IMPORTED);
				_vMapSource = rs
						.getString(CoreDBConstants.PaperToVenueFields.SOURCE);
				_vMapConfidenceScore = rs
						.getFloat(CoreDBConstants.PaperToVenueFields.CONFIDENCE_SCORE);
				_vMapConfidenceLevel = rs
						.getInt(CoreDBConstants.PaperToVenueFields.CONFIDENCE_LEVEL);
				_vMapDateImported = rs
						.getString(CoreDBConstants.PaperToVenueFields.DATE_IMPORTED);
			}
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void populateAuthorFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, AuthorFields> authors = new LinkedHashMap<Long, AuthorFields>();
		try {
			while (rs.next()) {
				long id = rs
						.getLong(CoreDBConstants.AuthorFields.ID);
				String name = rs.getString(CoreDBConstants.AuthorFields.NAME);
				String homepage = rs.getString(CoreDBConstants.AuthorFields.HOMEPAGE);
				String authorSource = rs.getString(CoreDBConstants.AuthorFields.AUTHOR_SOURCE);
				String authorDateImported = rs.getString(CoreDBConstants.AuthorFields.AUTHOR_DATE_IMPORTED);
				String orcidId = rs.getString(CoreDBConstants.AuthorFields.ORCID_ID);
				int index = rs.getInt(CoreDBConstants.PaperToAuthorFields.INDEX);
				String rawName = rs.getString(CoreDBConstants.PaperToAuthorFields.RAW_NAME);
				String rawAff = rs.getString(CoreDBConstants.PaperToAuthorFields.RAW_AFFILIATION);
				float disambiguationScore = rs.getFloat(CoreDBConstants.PaperToAuthorFields.DISAMBIGUATION_SCORE);
				String mappingSource = rs.getString(CoreDBConstants.PaperToAuthorFields.MAPPING_SOURCE);
				String mappingDateImported = rs.getString(CoreDBConstants.PaperToAuthorFields.MAPPING_DATE_IMPORTED);
				// populate the authors map
				//authors.put(id, new AuthorFields(name));
				authors.put(id, new AuthorFields(id, name, homepage,
						authorSource, authorDateImported, orcidId, index,
						rawName, rawAff, disambiguationScore, mappingSource,
						mappingDateImported));
			}
			_authors = authors;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void populateInstituteFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, InstitutionFields> institution = new HashMap<Long, InstitutionFields>();
		try {
			while (rs.next()) {
				long id = rs
						.getLong(CoreDBConstants.InstitutionFields.ID);
				String name = rs.getString(CoreDBConstants.InstitutionFields.NAME);
				String externalName = rs.getString(CoreDBConstants.InstitutionFields.EXTERNAL_NAME);
				String city = rs.getString(CoreDBConstants.InstitutionFields.CITY);
				String externalCity = rs.getString(CoreDBConstants.InstitutionFields.EXTERNAL_CITY);;
				String zipcode = rs.getString(CoreDBConstants.InstitutionFields.ZIPCODE);
				String country = rs.getString(CoreDBConstants.InstitutionFields.COUNTRY);
				String state = rs.getString(CoreDBConstants.InstitutionFields.STATE);
				String type = rs.getString(CoreDBConstants.InstitutionFields.TYPE);
				long idParent = rs.getLong(CoreDBConstants.InstitutionFields.ID_PARENT);
				long idParentHighest = rs.getLong(CoreDBConstants.InstitutionFields.ID_PARENT_HIGHEST);
				float eigenfactor = rs.getFloat(CoreDBConstants.InstitutionFields.EF);
				String institutionSource = rs.getString(CoreDBConstants.InstitutionFields.INSTITUION_SOURCE);
				String institutionDateImported = rs.getString(CoreDBConstants.InstitutionFields.INSTITUION_DATE_IMPORTED);
				int authorIndex = rs.getInt(CoreDBConstants.PaperToInstitutionFields.AUTHOR_INDEX);
				int indexAffiliation = rs.getInt(CoreDBConstants.PaperToInstitutionFields.INDEX_AFFILIATION);
				String protoAffiliation = rs.getString(CoreDBConstants.PaperToInstitutionFields.AFFILIATION_PROTO);
				float solrScore = rs.getFloat(CoreDBConstants.PaperToInstitutionFields.SOLR_SCORE);
				String crfSegmentation = rs.getString(CoreDBConstants.PaperToInstitutionFields.CRF_SEGMENTATION);
				int confidenceLevel = rs.getInt(CoreDBConstants.PaperToInstitutionFields.CONFIDENCE_LEVEL);
				String mappingSource = rs.getString(CoreDBConstants.PaperToInstitutionFields.MAPPING_SOURCE);
				String mappingDateImported = rs.getString(CoreDBConstants.PaperToInstitutionFields.MAPPING_DATE_IMPORTED);
				// populate institution map-object
				institution.put(id, new InstitutionFields(id, name,
						externalName, city, externalCity, zipcode, country,
						state, type, idParent, idParentHighest, eigenfactor,
						institutionSource, institutionDateImported, authorIndex,
						indexAffiliation, protoAffiliation, solrScore,
						crfSegmentation, confidenceLevel, mappingSource,
						mappingDateImported));
			}
			_institution = institution;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}
	
	public void populateConceptFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, ConceptFields> fields = new HashMap<Long, ConceptFields>();
		try {
			while (rs.next()) {
				long id = rs.getLong(CoreDBConstants.ConceptFields.ID);
				String idSource = rs.getString(CoreDBConstants.ConceptFields.ID_SOURCE);
				String name = rs.getString(CoreDBConstants.ConceptFields.NAME);
				/**
				 * NOTE : Not putting description for now as I dont think
				 * it will be needed.
				 */
				//String description = rs.getString(CoreDBConstants.ConceptFields.DESCRIPTION);
				boolean isActive = rs.getBoolean(CoreDBConstants.ConceptFields.IS_ACTIVE);
				boolean isGeneric = rs.getBoolean(CoreDBConstants.ConceptFields.IS_GENERIC);
				float specificityScore = rs.getFloat(CoreDBConstants.ConceptFields.SPECIFICITY_SCORE);
				String conceptSource = rs.getString(CoreDBConstants.ConceptFields.CONCEPT_SOURCE);
				String conceptDateImported = rs.getString(CoreDBConstants.ConceptFields.CONCEPT_DATE_IMPORTED);
				String idConceptRaw = rs.getString(CoreDBConstants.PaperToConceptFields.ID_CONCEPT_RAW);
				String conceptSourceType = rs.getString(CoreDBConstants.PaperToConceptFields.CONCEPT_SOURCE_TYPE);
				String mappingSource = rs.getString(CoreDBConstants.PaperToConceptFields.MAPPING_SOURCE);
				int idSection = rs.getInt(CoreDBConstants.PaperToConceptFields.ID_SECTION);
				int idSentence = rs.getInt(CoreDBConstants.PaperToConceptFields.ID_SENTENCE);
				int count = rs.getInt(CoreDBConstants.PaperToConceptFields.COUNT);
				String rawMention = rs.getString(CoreDBConstants.PaperToConceptFields.RAW_MENTION);
				float confidenceScore = rs.getFloat(CoreDBConstants.PaperToConceptFields.CONFIDENCE_SCORE);
				int confidenceLevel = rs.getInt(CoreDBConstants.PaperToConceptFields.CONFIDENCE_LEVEL);
				String mappingDateImported = rs.getString(CoreDBConstants.PaperToConceptFields.MAPPING_DATE_IMPORTED);
				fields.put(id, new ConceptFields(id, idSource, name, isActive,
						isGeneric, specificityScore, conceptSource,
						conceptDateImported, idConceptRaw, conceptSourceType,
						mappingSource, idSection, idSentence, count, rawMention,
						confidenceScore, confidenceLevel, mappingDateImported));
			}
			_concepts = fields;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void populateInCitationFields(ResultSet rs) throws CoreDBOpException {
			if (rs == null) {
				throw new CoreDBOpException(
						CoreDBConstants.Messages.RESULTSET_NULL_MSG);
			}
			Map<Long, CitationFields> citations = new HashMap<Long, CitationFields>();
			try {
				while (rs.next()) {
					long id = rs.getLong(CoreDBConstants.CitationFields.ID);
					String doiFromPaper = rs.getString(CoreDBConstants.CitationFields.DOI_FROM_PAPER);
					String doiToPaper = rs.getString(CoreDBConstants.CitationFields.DOI_TO_PAPER);
					long idFromPaper = rs.getLong(CoreDBConstants.CitationFields.ID_FROM_PAPER);
					long idToPaper = rs.getLong(CoreDBConstants.CitationFields.ID_TO_PAPER);
					int year = rs.getInt(CoreDBConstants.CitationFields.YEAR);
					int month = rs.getInt(CoreDBConstants.CitationFields.MONTH);
					float confidenceScore = rs.getFloat(CoreDBConstants.CitationFields.CONFIDENCE_SCORE);
					int confidenceLevel = rs.getInt(CoreDBConstants.CitationFields.CONFIDENCE_LEVEL);
					String source = rs.getString(CoreDBConstants.CitationFields.SOURCE);
					String dateImported = rs.getString(CoreDBConstants.CitationFields.DATE_IMPORTED);
					
					citations.put(id, new CitationFields(id, doiFromPaper,
							doiToPaper, idFromPaper, idToPaper, year, month,
							confidenceScore, confidenceLevel, source,
							dateImported));
				}
				_inCitations = citations;
			} catch (SQLException e) {
				throw new CoreDBOpException(e.getMessage());
			}
		}

	public void populateOutCitationFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, CitationFields> citations = new HashMap<Long, CitationFields>();
		try {
			while (rs.next()) {
				long id = rs.getLong(CoreDBConstants.CitationFields.ID);
				String doiFromPaper = rs.getString(CoreDBConstants.CitationFields.DOI_FROM_PAPER);
				String doiToPaper = rs.getString(CoreDBConstants.CitationFields.DOI_TO_PAPER);
				long idFromPaper = rs.getLong(CoreDBConstants.CitationFields.ID_FROM_PAPER);
				long idToPaper = rs.getLong(CoreDBConstants.CitationFields.ID_TO_PAPER);
				int year = rs.getInt(CoreDBConstants.CitationFields.YEAR);
				int month = rs.getInt(CoreDBConstants.CitationFields.MONTH);
				float confidenceScore = rs.getFloat(CoreDBConstants.CitationFields.CONFIDENCE_SCORE);
				int confidenceLevel = rs.getInt(CoreDBConstants.CitationFields.CONFIDENCE_LEVEL);
				String source = rs.getString(CoreDBConstants.CitationFields.SOURCE);
				String dateImported = rs.getString(CoreDBConstants.CitationFields.DATE_IMPORTED);
				
				citations.put(id, new CitationFields(id, doiFromPaper,
						doiToPaper, idFromPaper, idToPaper, year, month,
						confidenceScore, confidenceLevel, source,
						dateImported));
			}
			_outCitations = citations;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	/**
	 * @return the _id
	 */
	public final long get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public final void set_id(long _id) {
		this._id = _id;
	}

	/**
	 * @return the _pmId
	 */
	public final long get_pmId() {
		return _pmId;
	}

	/**
	 * @param _pmId the _pmId to set
	 */
	public final void set_pmId(long _pmId) {
		this._pmId = _pmId;
	}

	/**
	 * @return the _doi
	 */
	public final String get_doi() {
		return _doi;
	}

	/**
	 * @param _doi the _doi to set
	 */
	public final void set_doi(String _doi) {
		this._doi = _doi;
	}

	/**
	 * @return the _title
	 */
	public final String get_title() {
		return _title;
	}

	/**
	 * @param _title the _title to set
	 */
	public final void set_title(String _title) {
		this._title = _title;
	}

	/**
	 * @return the _datePmReleased
	 */
	public final String get_datePmReleased() {
		return _datePmReleased;
	}

	/**
	 * @param _datePmReleased the _datePmReleased to set
	 */
	public final void set_datePmReleased(String _datePmReleased) {
		this._datePmReleased = _datePmReleased;
	}

	/**
	 * @return the _issn
	 */
	public final String get_issn() {
		return _issn;
	}

	/**
	 * @param _issn the _issn to set
	 */
	public final void set_issn(String _issn) {
		this._issn = _issn;
	}

	/**
	 * @return the _year
	 */
	public final int get_year() {
		return _year;
	}

	/**
	 * @param _year the _year to set
	 */
	public final void set_year(int _year) {
		this._year = _year;
	}

	/**
	 * @return the _month
	 */
	public final int get_month() {
		return _month;
	}

	/**
	 * @param _month the _month to set
	 */
	public final void set_month(int _month) {
		this._month = _month;
	}

	/**
	 * @return the _day
	 */
	public final int get_day() {
		return _day;
	}

	/**
	 * @param _day the _day to set
	 */
	public final void set_day(int _day) {
		this._day = _day;
	}

	/**
	 * @return the _iso
	 */
	public final String get_iso() {
		return _iso;
	}

	/**
	 * @param _iso the _iso to set
	 */
	public final void set_iso(String _iso) {
		this._iso = _iso;
	}

	/**
	 * @return the _issue
	 */
	public final String get_issue() {
		return _issue;
	}

	/**
	 * @param _issue the _issue to set
	 */
	public final void set_issue(String _issue) {
		this._issue = _issue;
	}

	/**
	 * @return the _language
	 */
	public final String get_language() {
		return _language;
	}

	/**
	 * @param _language the _language to set
	 */
	public final void set_language(String _language) {
		this._language = _language;
	}

	/**
	 * @return the _type
	 */
	public final String get_type() {
		return _type;
	}

	/**
	 * @param _type the _type to set
	 */
	public final void set_type(String _type) {
		this._type = _type;
	}

	/**
	 * @return the _journalName
	 */
	public final String get_journalName() {
		return _journalName;
	}

	/**
	 * @param _journalName the _journalName to set
	 */
	public final void set_journalName(String _journalName) {
		this._journalName = _journalName;
	}

	/**
	 * @return the _eigenFactor
	 */
	public final float get_eigenFactor() {
		return _eigenFactor;
	}

	/**
	 * @param _eigenFactor the _eigenFactor to set
	 */
	public final void set_eigenFactor(float _eigenFactor) {
		this._eigenFactor = _eigenFactor;
	}

	/**
	 * @return the _country
	 */
	public final String get_country() {
		return _country;
	}

	/**
	 * @param _country the _country to set
	 */
	public final void set_country(String _country) {
		this._country = _country;
	}

	/**
	 * @return the _volume
	 */
	public final String get_volume() {
		return _volume;
	}

	/**
	 * @param _volume the _volume to set
	 */
	public final void set_volume(String _volume) {
		this._volume = _volume;
	}

	/**
	 * @return the _source
	 */
	public final String get_metadataSource() {
		return _source;
	}

	/**
	 * @param _source the _source to set
	 */
	public final void set_metadataSource(String _metadataSource) {
		this._source = _metadataSource;
	}

	/**
	 * @return the _venueId
	 */
	public final long get_venueId() {
		return _venueId;
	}

	/**
	 * @param _venueId the _venueId to set
	 */
	public final void set_venueId(long _venueId) {
		this._venueId = _venueId;
	}

	/**
	 * @return the _vPublisher
	 */
	public final String get_publisher() {
		return _vPublisher;
	}

	/**
	 * @param _vPublisher the _vPublisher to set
	 */
	public final void set_publisher(String _publisher) {
		this._vPublisher = _publisher;
	}

	/**
	 * @return the _authors
	 */
	public final Map<Long, AuthorFields> get_authors() {
		return _authors;
	}

	/**
	 * @param _authors the _authors to set
	 */
	public final void set_authors(Map<Long, AuthorFields> _authors) {
		this._authors = _authors;
	}

	/**
	 * @return the _institution
	 */
	public final Map<Long, InstitutionFields> get_institution() {
		return _institution;
	}

	/**
	 * @param _institution the _institution to set
	 */
	public final void set_institution(Map<Long, InstitutionFields> _institution) {
		this._institution = _institution;
	}

	/**
	 * @return the _abstract
	 */
	public final String get_abstract() {
		return _abstract;
	}

	/**
	 * @param _abstract the _abstract to set
	 */
	public final void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}

	/**
	 * @return the _sections
	 */
	public final String[] get_sections() {
		return _sections;
	}

	/**
	 * @param _sections the _sections to set
	 */
	public final void set_sections(String[] _sections) {
		this._sections = _sections;
	}

	/**
	 * @return the _dateImported
	 */
	public final String get_dateImported() {
		return _dateImported;
	}

	/**
	 * @param _dateImported the _dateImported to set
	 */
	public final void set_dateImported(String _dateImported) {
		this._dateImported = _dateImported;
	}

	/**
	 * @return the _pmcId
	 */
	public final String get_pmcId() {
		return _pmcId;
	}

	/**
	 * @param _pmcId the _pmcId to set
	 */
	public final void set_pmcId(String _pmcId) {
		this._pmcId = _pmcId;
	}

	/**
	 * @return the _meshTerms
	 */
	public final String get_meshTerms() {
		return _meshTerms;
	}

	/**
	 * @param _meshTerms the _meshTerms to set
	 */
	public final void set_meshTerms(String _meshTerms) {
		this._meshTerms = _meshTerms;
	}

	/**
	 * @return the _keywords
	 */
	public final String get_keywords() {
		return _keywords;
	}

	/**
	 * @param _keywords the _keywords to set
	 */
	public final void set_keywords(String _keywords) {
		this._keywords = _keywords;
	}

	/**
	 * @return the _conceptsRaw
	 */
	public final String get_conceptsRaw() {
		return _conceptsRaw;
	}

	/**
	 * @param _conceptsRaw the _conceptsRaw to set
	 */
	public final void set_conceptsRaw(String _conceptsRaw) {
		this._conceptsRaw = _conceptsRaw;
	}

	/**
	 * @return the _numOfReferences
	 */
	public final int get_numOfReferences() {
		return _numOfReferences;
	}

	/**
	 * @param _numOfReferences the _numOfReferences to set
	 */
	public final void set_numOfReferences(int _numOfReferences) {
		this._numOfReferences = _numOfReferences;
	}

	/**
	 * @return the _grants
	 */
	public final String get_grants() {
		return _grants;
	}

	/**
	 * @param _grants the _grants to set
	 */
	public final void set_grants(String _grants) {
		this._grants = _grants;
	}

	/**
	 * @return the _vIsoAbbreviation
	 */
	public final String get_vIsoAbbreviation() {
		return _vIsoAbbreviation;
	}

	/**
	 * @param _vIsoAbbreviation the _vIsoAbbreviation to set
	 */
	public final void set_vIsoAbbreviation(String _vIsoAbbreviation) {
		this._vIsoAbbreviation = _vIsoAbbreviation;
	}

	/**
	 * @return the _vType
	 */
	public final String get_vType() {
		return _vType;
	}

	/**
	 * @param _vType the _vType to set
	 */
	public final void set_vType(String _vType) {
		this._vType = _vType;
	}

	/**
	 * @return the _vTitle
	 */
	public final String get_vTitle() {
		return _vTitle;
	}

	/**
	 * @param _vTitle the _vTitle to set
	 */
	public final void set_vTitle(String _vTitle) {
		this._vTitle = _vTitle;
	}

	/**
	 * @return the _vTitleWithoutDiacritics
	 */
	public final String get_vTitleWithoutDiacritics() {
		return _vTitleWithoutDiacritics;
	}

	/**
	 * @param _vTitleWithoutDiacritics the _vTitleWithoutDiacritics to set
	 */
	public final void set_vTitleWithoutDiacritics(String _vTitleWithoutDiacritics) {
		this._vTitleWithoutDiacritics = _vTitleWithoutDiacritics;
	}

	/**
	 * @return the _vYearStart
	 */
	public final int get_vYearStart() {
		return _vYearStart;
	}

	/**
	 * @param _vYearStart the _vYearStart to set
	 */
	public final void set_vYearStart(int _vYearStart) {
		this._vYearStart = _vYearStart;
	}

	/**
	 * @return the _vYearEnd
	 */
	public final int get_vYearEnd() {
		return _vYearEnd;
	}

	/**
	 * @param _vYearEnd the _vYearEnd to set
	 */
	public final void set_vYearEnd(int _vYearEnd) {
		this._vYearEnd = _vYearEnd;
	}

	/**
	 * @return the _vFrequency
	 */
	public final String get_vFrequency() {
		return _vFrequency;
	}

	/**
	 * @param _vFrequency the _vFrequency to set
	 */
	public final void set_vFrequency(String _vFrequency) {
		this._vFrequency = _vFrequency;
	}

	/**
	 * @return the _vLanguage
	 */
	public final String get_vLanguage() {
		return _vLanguage;
	}

	/**
	 * @param _vLanguage the _vLanguage to set
	 */
	public final void set_vLanguage(String _vLanguage) {
		this._vLanguage = _vLanguage;
	}

	/**
	 * @return the _vPublisher
	 */
	public final String get_vPublisher() {
		return _vPublisher;
	}

	/**
	 * @param _vPublisher the _vPublisher to set
	 */
	public final void set_vPublisher(String _vPublisher) {
		this._vPublisher = _vPublisher;
	}

	/**
	 * @return the _vSource
	 */
	public final String get_vSource() {
		return _vSource;
	}

	/**
	 * @param _vSource the _vSource to set
	 */
	public final void set_vSource(String _vSource) {
		this._vSource = _vSource;
	}

	/**
	 * @return the _vIsoWithoutDiacritic
	 */
	public final String get_vIsoWithoutDiacritic() {
		return _vIsoWithoutDiacritic;
	}

	/**
	 * @param _vIsoWithoutDiacritic the _vIsoWithoutDiacritic to set
	 */
	public final void set_vIsoWithoutDiacritic(String _vIsoWithoutDiacritic) {
		this._vIsoWithoutDiacritic = _vIsoWithoutDiacritic;
	}

	/**
	 * @return the _vEFMedianRaw
	 */
	public final float get_vEFMedianRaw() {
		return _vEFMedianRaw;
	}

	/**
	 * @param _vEFMedianRaw the _vEFMedianRaw to set
	 */
	public final void set_vEFMedianRaw(float _vEFMedianRaw) {
		this._vEFMedianRaw = _vEFMedianRaw;
	}

	/**
	 * @return the _vEFMedianNormalized
	 */
	public final float get_vEFMedianNormalized() {
		return _vEFMedianNormalized;
	}

	/**
	 * @param _vEFMedianNormalized the _vEFMedianNormalized to set
	 */
	public final void set_vEFMedianNormalized(float _vEFMedianNormalized) {
		this._vEFMedianNormalized = _vEFMedianNormalized;
	}

	/**
	 * @return the _vDateImported
	 */
	public final String get_vDateImported() {
		return _vDateImported;
	}

	/**
	 * @param _vDateImported the _vDateImported to set
	 */
	public final void set_vDateImported(String _vDateImported) {
		this._vDateImported = _vDateImported;
	}

	/**
	 * @return the _vMapSource
	 */
	public final String get_vMapSource() {
		return _vMapSource;
	}

	/**
	 * @param _vMapSource the _vMapSource to set
	 */
	public final void set_vMapSource(String _vMapSource) {
		this._vMapSource = _vMapSource;
	}

	/**
	 * @return the _vMapConfidenceScore
	 */
	public final float get_vMapConfidenceScore() {
		return _vMapConfidenceScore;
	}

	/**
	 * @param _vMapConfidenceScore the _vMapConfidenceScore to set
	 */
	public final void set_vMapConfidenceScore(float _vMapConfidenceScore) {
		this._vMapConfidenceScore = _vMapConfidenceScore;
	}

	/**
	 * @return the _vMapConfidenceLevel
	 */
	public final int get_vMapConfidenceLevel() {
		return _vMapConfidenceLevel;
	}

	/**
	 * @param _vMapConfidenceLevel the _vMapConfidenceLevel to set
	 */
	public final void set_vMapConfidenceLevel(int _vMapConfidenceLevel) {
		this._vMapConfidenceLevel = _vMapConfidenceLevel;
	}

	/**
	 * @return the _vMapDateImported
	 */
	public final String get_vMapDateImported() {
		return _vMapDateImported;
	}

	/**
	 * @param _vMapDateImported the _vMapDateImported to set
	 */
	public final void set_vMapDateImported(String _vMapDateImported) {
		this._vMapDateImported = _vMapDateImported;
	}

	/**
	 * @return the _abstractOther
	 */
	public final String get_abstractOther() {
		return _abstractOther;
	}

	/**
	 * @param _abstractOther the _abstractOther to set
	 */
	public final void set_abstractOther(String _abstractOther) {
		this._abstractOther = _abstractOther;
	}

	/**
	 * @return the _inCitations
	 */
	public final Map<Long, CitationFields> get_inCitations() {
		return _inCitations;
	}

	/**
	 * @param _inCitations the _inCitations to set
	 */
	public final void set_inCitations(Map<Long, CitationFields> _inCitations) {
		this._inCitations = _inCitations;
	}

	/**
	 * @return the _outCitations
	 */
	public final Map<Long, CitationFields> get_outCitations() {
		return _outCitations;
	}

	/**
	 * @param _outCitations the _outCitations to set
	 */
	public final void set_outCitations(Map<Long, CitationFields> _outCitations) {
		this._outCitations = _outCitations;
	}

	/**
	 * @return the _source
	 */
	public final String get_source() {
		return _source;
	}

	/**
	 * @param _source the _source to set
	 */
	public final void set_source(String _source) {
		this._source = _source;
	}

	/**
	 * @return the _concepts
	 */
	public final Map<Long, ConceptFields> get_concepts() {
		return _concepts;
	}

	/**
	 * @param _concepts the _concepts to set
	 */
	public final void set_concepts(Map<Long, ConceptFields> _concepts) {
		this._concepts = _concepts;
	}

	/**
	 * @return the _nlmId
	 */
	public final String get_nlmId() {
		return _nlmId;
	}

	/**
	 * @param _nlmId the _nlmId to set
	 */
	public final void set_nlmId(String _nlmId) {
		this._nlmId = _nlmId;
	}
}
