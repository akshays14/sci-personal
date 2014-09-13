package main.java.com.sciencescape.ds.db.rdbms.mysqlhandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.coredb.PaperFields;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.DBMSConstants;

public class MySQLOperations {

	public ResultSet executeQuery(MySQLHandler mh, String query) throws IOException {
		if (mh == null) {
			throw new IOException(DBMSConstants.MySQLHandlerOperations.MYSQL_HANDLER_NULL_MESSAGE);
		}
		if (mh.get_dbConnection() == null) {
			throw new IOException(DBMSConstants.MySQLHandlerOperations.MYSQL_CONNECTION_NULL_MESSAGE);
		}
		ResultSet resultSet = null;
		try {
			resultSet = mh.get_dbConnection().createStatement().executeQuery(query);
		} catch (SQLException ex) {
			throw new IOException(ex);
		}
		return resultSet;
	}

}
