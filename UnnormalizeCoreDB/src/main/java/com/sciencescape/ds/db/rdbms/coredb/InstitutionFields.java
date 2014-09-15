package main.java.com.sciencescape.ds.db.rdbms.coredb;

public class InstitutionFields {

	String _rawAffiliationName;
	String _normalizedAffiliationName;
	
	public InstitutionFields(String _rawAffiliationName,
			String _normalizedAffiliationName) {
		super();
		this._rawAffiliationName = _rawAffiliationName;
		this._normalizedAffiliationName = _normalizedAffiliationName;
	}

	public String get_rawAffiliationName() {
		return _rawAffiliationName;
	}

	public void set_rawAffiliationName(String _rawAffiliationName) {
		this._rawAffiliationName = _rawAffiliationName;
	}

	public String get_normalizedAffiliationName() {
		return _normalizedAffiliationName;
	}

	public void set_normalizedAffiliationName(String _normalizedAffiliationName) {
		this._normalizedAffiliationName = _normalizedAffiliationName;
	}	
}
