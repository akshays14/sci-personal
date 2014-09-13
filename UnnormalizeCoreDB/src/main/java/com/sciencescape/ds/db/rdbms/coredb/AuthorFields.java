package main.java.com.sciencescape.ds.db.rdbms.coredb;

public class AuthorFields {

	private String _name;

	public AuthorFields(String _name) {
		super();
		this._name = _name;
	}
	
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
}
