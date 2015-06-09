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
	private long _nlmId;
	private String _meshTerms;
	private String _keywords;
	private String _concepts;
	private int _numOfReferences;
	private String _grants;
	private String _metadataSource;
	// Fields from venue table
	private long _venueId;
	private String _publisher;
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
	private Map<Long, FieldsFields> _fields;
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
				CoreDBConstants.PaperFields.METADATA_SOURCE, _metadataSource);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.PaperFields.VENUE_ID, _venueId);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT,
				CoreDBConstants.VenueFields.PUBLISHER, _publisher);
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
			stream.printf(
					CoreDBConstants.Messages.MSG_INSTITUTION_RAW_AFFILIATION_FORMAT,
					(entry.getValue()).getRawAffiliationName());
			stream.printf(
					CoreDBConstants.Messages.MSG_INSTITUTION_NORM_AFFILIATION_FORMAT,
					(entry.getValue()).getNormalizedAffiliationName());
		}
	}

	public void printFieldsInfo(PrintStream stream) {
		if (stream == null || _fields == null) {
			return;
		}
		for (Map.Entry<Long, FieldsFields> entry : _fields.entrySet()) {
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
			_nlmId = rs.getLong(CoreDBConstants.PaperFields.NLM_ID);
			_meshTerms =
					rs.getString(CoreDBConstants.PaperFields.MESH_TERMS_RAW);
			_keywords = rs.getString(CoreDBConstants.PaperFields.KEYWORDS);
			_concepts = rs.getString(CoreDBConstants.PaperFields.CONCEPTS_RAW);
			_numOfReferences =
					rs.getInt(CoreDBConstants.PaperFields.NUM_OF_REFS);
			_grants =  rs.getString(CoreDBConstants.PaperFields.GRANTS);
			_country = rs.getString(CoreDBConstants.PaperFields.COUNTRY);
			_language = rs.getString(CoreDBConstants.PaperFields.LANGUAGE);
			_metadataSource = rs
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
				_publisher = rs
						.getString(CoreDBConstants.VenueFields.PUBLISHER);
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
						.getLong(CoreDBConstants.PaperToAuthorFields.AUTHOR_ID);
				String name = rs.getString(CoreDBConstants.AuthorFields.NAME);
				authors.put(id, new AuthorFields(name));
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
						.getLong(CoreDBConstants.PaperToInstitutionFields.INSITUTION_ID);
				String rawAffiliation = rs
						.getString(CoreDBConstants.PaperToInstitutionFields.AFFILIATION_PROTO);
				String normAffiliation = rs
						.getString(CoreDBConstants.InstitutionFields.NAME);
				// since raw string has other information chop extra-stuff off
				if (rawAffiliation != null
						&& rawAffiliation
								.contains(CoreDBConstants.PaperToInstitutionFields.RAW_AFFILIATION_DELIMITER)) {
					rawAffiliation = (rawAffiliation
							.split(CoreDBConstants.PaperToInstitutionFields.RAW_AFFILIATION_DELIMITER))[1];
				}
				institution.put(id, new InstitutionFields(rawAffiliation,
						normAffiliation));
			}
			_institution = institution;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

/*	public void populateFullTextSections(int numOfSection, int lengthOfSection)
			throws CoreDBOpException {
		String[] sections = new String[numOfSection];
		RandomText random = new RandomText();
		for (int i = 0; i < numOfSection; ++i) {
			sections[i] = random.generateText(lengthOfSection);
		}
		_sections = sections;
	}
*/
	public void populateFieldsFields(ResultSet rs) throws CoreDBOpException {
		if (rs == null) {
			throw new CoreDBOpException(
					CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, FieldsFields> fields = new HashMap<Long, FieldsFields>();
		try {
			while (rs.next()) {
				long id = rs
						.getLong(CoreDBConstants.PaperToFieldFields.FIELD_ID);
				String name = rs
						.getString(CoreDBConstants.PaperToFieldFields.FIELD_NAME);
				fields.put(id, new FieldsFields(name));
			}
			_fields = fields;
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
	 * @return the _nlmId
	 */
	public final long get_nlmId() {
		return _nlmId;
	}

	/**
	 * @param _nlmId the _nlmId to set
	 */
	public final void set_nlmId(long _nlmId) {
		this._nlmId = _nlmId;
	}

	/**
	 * @return the _metadataSource
	 */
	public final String get_metadataSource() {
		return _metadataSource;
	}

	/**
	 * @param _metadataSource the _metadataSource to set
	 */
	public final void set_metadataSource(String _metadataSource) {
		this._metadataSource = _metadataSource;
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
	 * @return the _publisher
	 */
	public final String get_publisher() {
		return _publisher;
	}

	/**
	 * @param _publisher the _publisher to set
	 */
	public final void set_publisher(String _publisher) {
		this._publisher = _publisher;
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
	 * @return the _fields
	 */
	public final Map<Long, FieldsFields> get_fields() {
		return _fields;
	}

	/**
	 * @param _fields the _fields to set
	 */
	public final void set_fields(Map<Long, FieldsFields> _fields) {
		this._fields = _fields;
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
}
