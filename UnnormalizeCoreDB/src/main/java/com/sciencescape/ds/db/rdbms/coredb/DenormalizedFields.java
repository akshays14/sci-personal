package main.java.com.sciencescape.ds.db.rdbms.coredb;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import main.java.com.sciencescape.ds.db.transfer.CoreDBOpException;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.RandomText;
/**
 * @class DenormalizedFields DenormalizedFields.java
 * @brief container class for all de-normalized fields
 * @author Akshay
 *
 */
public class DenormalizedFields {
	// Fields from paper table
	private long _id;
	private long _pmId;
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
	private String _metadataSource;
	private long _venueId;
	// Fields from venue table
	private String _publisher;
	// Fields from author table
	private Map<Long, AuthorFields> _authors;
	// Field from institute table
	private Map<Long, InstitutionFields> _institution;
	// Fields from paper table
	private String _abstract;
	// Fields from full-text
	private String[] _sections;
	// Fields from fields table
	private Map<Long, FieldsFields> _fields;
	// Field from paper table
	private String _dateImported;
	
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	public long get_pmId() {
		return _pmId;
	}
	public void set_pmId(long _pmId) {
		this._pmId = _pmId;
	}
	public String get_doi() {
		return _doi;
	}
	public void set_doi(String _doi) {
		this._doi = _doi;
	}
	public String get_title() {
		return _title;
	}
	public void set_title(String _title) {
		this._title = _title;
	}
	public String get_datePmReleased() {
		return _datePmReleased;
	}
	public void set_datePmReleased(String _datePmReleased) {
		this._datePmReleased = _datePmReleased;
	}
	public String get_issn() {
		return _issn;
	}
	public void set_issn(String _issn) {
		this._issn = _issn;
	}
	public int get_year() {
		return _year;
	}
	public void set_year(int _year) {
		this._year = _year;
	}
	public int get_month() {
		return _month;
	}
	public void set_month(int _month) {
		this._month = _month;
	}
	public int get_day() {
		return _day;
	}
	public void set_day(int _day) {
		this._day = _day;
	}
	public String get_iso() {
		return _iso;
	}
	public void set_iso(String _iso) {
		this._iso = _iso;
	}
	public String get_issue() {
		return _issue;
	}
	public void set_issue(String _issue) {
		this._issue = _issue;
	}
	public String get_language() {
		return _language;
	}
	public void set_language(String _language) {
		this._language = _language;
	}
	public String get_type() {
		return _type;
	}
	public void set_type(String _type) {
		this._type = _type;
	}
	public String get_journalName() {
		return _journalName;
	}
	public void set_journalName(String _journalName) {
		this._journalName = _journalName;
	}
	public float get_eigenFactor() {
		return _eigenFactor;
	}
	public void set_eigenFactor(float _eigenFactor) {
		this._eigenFactor = _eigenFactor;
	}
	public String get_country() {
		return _country;
	}
	public void set_country(String _country) {
		this._country = _country;
	}
	public String get_volume() {
		return _volume;
	}
	public void set_volume(String _volume) {
		this._volume = _volume;
	}
	public long get_nlmId() {
		return _nlmId;
	}
	public void set_nlmId(long _nlmId) {
		this._nlmId = _nlmId;
	}
	public String get_metadataSource() {
		return _metadataSource;
	}
	public void set_metadataSource(String _metadata_source) {
		this._metadataSource = _metadata_source;
	}
	public String get_publisher() {
		return _publisher;
	}
	public void set_publisher(String _publisher) {
		this._publisher = _publisher;
	}
	public long get_venueId() {
		return _venueId;
	}
	public void set_venueId(long _venueId) {
		this._venueId = _venueId;
	}
	public Map<Long, AuthorFields> get_authors() {
		return _authors;
	}
	public void set_authors(Map<Long, AuthorFields> authors) {
		this._authors = authors;
	}
	public Map<Long, InstitutionFields> get_institution() {
		return _institution;
	}
	public void set_institution(Map<Long, InstitutionFields> _institution) {
		this._institution = _institution;
	}
	public String get_abstract() {
		return _abstract;
	}
	public void set_abstract(String _abstract) {
		this._abstract = _abstract;
	}
	public String[] get_sections() {
		return _sections;
	}
	public void set_sections(String[] _sections) {
		this._sections = _sections;
	}
	public Map<Long, FieldsFields> get_fields() {
		return _fields;
	}
	public void set_fields(Map<Long, FieldsFields> _fields) {
		this._fields = _fields;
	}
	public String get_dateImported() {
		return _dateImported;
	}
	public void set_dateImported(String _dateImported) {
		this._dateImported = _dateImported;
	}
	public void printFields(PrintStream stream) {
		if (stream == null) {
			return;
		}
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT, CoreDBConstants.PaperFields.ID, _id);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT, CoreDBConstants.PaperFields.PMID, _pmId);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.DOI, _doi);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.TITLE, _title);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.PM_DATE_RELEASED, _datePmReleased);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.ISSN, _issn);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT, CoreDBConstants.PaperFields.YEAR, _year);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT, CoreDBConstants.PaperFields.MONTH, _month);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT, CoreDBConstants.PaperFields.DAY, _day);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.ISO, _iso);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.ISSUE, _issue);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.LANGUAGE, _language);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.TYPE, _type);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.JOURNAL_NAME, _journalName);
		stream.printf(CoreDBConstants.Messages.MSG_FLOAT_FIELD_FORMAT, CoreDBConstants.PaperFields.EIGENFACTOR, _eigenFactor);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.COUNTRY, _country);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.VOLUME, _volume);
		stream.printf(CoreDBConstants.Messages.MSG_INT_FIELD_FORMAT, CoreDBConstants.PaperFields.NLM_ID, _nlmId);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.METADATA_SOURCE, _metadataSource);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.PaperFields.VENUE_ID, _venueId);
		stream.printf(CoreDBConstants.Messages.MSG_STRING_FIELD_FORMAT, CoreDBConstants.VenueFields.PUBLISHER, _publisher);
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
			stream.printf(CoreDBConstants.Messages.MSG_AUTHOR_ID_FORMAT, entry.getKey());
			stream.printf(CoreDBConstants.Messages.MSG_AUTHOR_NAME_FORMAT, (entry.getValue()).get_name());
		}
	}
	
	public void printInstitution(PrintStream stream) {
		if (stream == null || _institution == null) {
			return;
		}
		for (Map.Entry<Long, InstitutionFields> entry : _institution.entrySet()) {
			stream.printf(CoreDBConstants.Messages.MSG_INSTITUTION_ID_FORMAT, entry.getKey());
			stream.printf(CoreDBConstants.Messages.MSG_INSTITUTION_RAW_AFFILIATION_FORMAT, (entry.getValue()).get_rawAffiliationName());
			stream.printf(CoreDBConstants.Messages.MSG_INSTITUTION_NORM_AFFILIATION_FORMAT, (entry.getValue()).get_normalizedAffiliationName());
		}
	}

	public void printFieldsInfo(PrintStream stream) {
		if (stream == null || _fields == null) {
			return;
		}
		for (Map.Entry<Long, FieldsFields> entry : _fields.entrySet()) {
			stream.printf(CoreDBConstants.Messages.MSG_FIELD_ID_FORMAT, entry.getKey());
			stream.printf(CoreDBConstants.Messages.MSG_FIELD_NAME_FORMAT, (entry.getValue()).get_fieldName());
		}
	}
	
	public void populatePaperFields(ResultSet rs) throws CoreDBOpException  {
		if (rs == null) {
			throw new CoreDBOpException(CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		try {
			_id = rs.getLong(CoreDBConstants.PaperFields.ID);
			_pmId = rs.getLong(CoreDBConstants.PaperFields.PMID);
			_doi = rs.getString(CoreDBConstants.PaperFields.DOI);
			_title = rs.getString(CoreDBConstants.PaperFields.TITLE);
			_datePmReleased = rs.getString(CoreDBConstants.PaperFields.PM_DATE_RELEASED);
			_issn = rs.getString(CoreDBConstants.PaperFields.ISSN);
			_year = rs.getInt(CoreDBConstants.PaperFields.YEAR);
			_month = rs.getInt(CoreDBConstants.PaperFields.MONTH);
			_day = rs.getInt(CoreDBConstants.PaperFields.DAY);
			_iso = rs.getString(CoreDBConstants.PaperFields.ISO);
			_issue = rs.getString(CoreDBConstants.PaperFields.ISSUE);
			_language = rs.getString(CoreDBConstants.PaperFields.LANGUAGE);
			_type = rs.getString(CoreDBConstants.PaperFields.TYPE);
			_journalName = rs.getString(CoreDBConstants.PaperFields.JOURNAL_NAME);
			_eigenFactor = rs.getFloat(CoreDBConstants.PaperFields.EIGENFACTOR);
			_country = rs.getString(CoreDBConstants.PaperFields.COUNTRY);
			_volume = rs.getString(CoreDBConstants.PaperFields.VOLUME);
			_nlmId = rs.getLong(CoreDBConstants.PaperFields.NLM_ID);
			_metadataSource = rs.getString(CoreDBConstants.PaperFields.METADATA_SOURCE);
			_venueId = rs.getLong(CoreDBConstants.PaperFields.VENUE_ID);
			_abstract = rs.getString(CoreDBConstants.PaperFields.ABSTRACT);
			
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}
	
	public void populateVenueFields(ResultSet rs) throws CoreDBOpException  {
		if (rs == null) {
			throw new CoreDBOpException(CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		try {
			// move resultSet to first row
			rs.next();
			_publisher = rs.getString(CoreDBConstants.VenueFields.PUBLISHER);
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}
	
	public void populateAuthorFields(ResultSet rs) throws CoreDBOpException  {
		if (rs == null) {
			throw new CoreDBOpException(CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, AuthorFields> authors = new LinkedHashMap<Long, AuthorFields>();
		try {
			while (rs.next()) {
				long id = rs.getLong(CoreDBConstants.PaperToAuthorFields.AUTHOR_ID);
				String name = rs.getString(CoreDBConstants.AuthorFields.NAME);
				authors.put(id, new AuthorFields(name));
			}
			_authors = authors;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void populateInstituteFields(ResultSet rs) throws CoreDBOpException  {
		if (rs == null) {
			throw new CoreDBOpException(CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, InstitutionFields> institution = new HashMap<Long, InstitutionFields>();
		try {
			while (rs.next()) {
				long id = rs.getLong(CoreDBConstants.PaperToInstitutionFields.INSITUTION_ID);
				String rawAffiliation = rs.getString(CoreDBConstants.PaperToInstitutionFields.AFFILIATION_PROTO);
				String normAffiliation = rs.getString(CoreDBConstants.InstitutionFields.NAME);
				// since raw string has other information chop extra-stuff off
				if (rawAffiliation != null && rawAffiliation.contains(CoreDBConstants.PaperToInstitutionFields.RAW_AFFILIATION_DELIMITER)) {
					rawAffiliation = (rawAffiliation.split(CoreDBConstants.PaperToInstitutionFields.RAW_AFFILIATION_DELIMITER))[1];
				}
				institution.put(id, new InstitutionFields(rawAffiliation, normAffiliation));
			}
			_institution = institution;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void populateFullTextSections(int numOfSection, int lengthOfSection) throws CoreDBOpException  {
		String[] sections = new String[numOfSection];
		RandomText random = new RandomText();
		for (int i = 0; i < numOfSection; ++i) {
			sections[i] = random.generateText(lengthOfSection);
		}
		_sections = sections;
	}

	public void populateFieldsFields(ResultSet rs) throws CoreDBOpException  {
		if (rs == null) {
			throw new CoreDBOpException(CoreDBConstants.Messages.RESULTSET_NULL_MSG);
		}
		Map<Long, FieldsFields> fields = new HashMap<Long, FieldsFields>();
		try {
			while (rs.next()) {
				long id = rs.getLong(CoreDBConstants.PaperToFieldFields.FIELD_ID);
				String name = rs.getString(CoreDBConstants.PaperToFieldFields.FIELD_NAME);
				fields.put(id, new FieldsFields(name));
			}
			_fields = fields;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}
}
