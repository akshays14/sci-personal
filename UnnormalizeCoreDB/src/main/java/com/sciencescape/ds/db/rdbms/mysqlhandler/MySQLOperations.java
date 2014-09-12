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

	public PaperFields getPaperFields(MySQLHandler my, String pmId) throws MySQLOpException {
		if (my == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.MYSQL_HANDLER_NULL_MESSAGE);
		}
		if (pmId == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
		PaperFields pf = new PaperFields();
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.PaperFields.PMID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		try {
			resultSet = executeQuery(my, query);
		} catch (SQLException ex) {
			throw new MySQLOpException(ex.getMessage());
		}
		/* check if there is only one record in the response */
		assert(resultSet.isFirst() && resultSet.isLast());
		/* fetch the record */
		pf.set_id(resultSet.getLong(CoreDBConstants.PaperFields.ID));
		pf.set_pmId((resultSet.getLong(CoreDBConstants.PaperFields.PMID)));
		pf.set_doi(resultSet.getString(CoreDBConstants.PaperFields.DOI));
		pf.set_title(resultSet.getString(CoreDBConstants.PaperFields.TITLE));
		try {
			if (!_resultSet.next()) {
				return null;
			}
			Object data[] = new Object[_cols.length];
			for(int i=0;i<_cols.length;i++) {
				data[i] = _resultSet.getObject(_cols[i]);
			}
			return new DataRecord(data);
		} catch (SQLException exp) {
			throw new IOException(exp);
		}
		return (pf);
	}
}
