package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOpException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOperations;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.DBMSConstants;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;

public class CoreDBOperations {

	private MySQLHandler my;
	
	public CoreDBOperations(MySQLHandler my) throws MySQLOpException {
		super();
		if (my == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.MYSQL_HANDLER_NULL_MESSAGE);
		}
		this.my = my;
	}

	public DenormalizedFields getDenormalizedFields(long numOfRecords) throws MySQLOpException {
		int recordsProcessed = 0;
		String id = null;
		ResultSet paperSet = getPaperFields(numOfRecords);
		DenormalizedFields denormFields = new DenormalizedFields();
		try {
			while (paperSet.next()) {
				id = paperSet.getString(CoreDBConstants.PaperFields.ID);
				
			}
		} catch (SQLException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (denormFields);
	}
	
	private ResultSet getPaperFields(long numOfRecords) throws MySQLOpException {	
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
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
		strBuff.append(DBMSConstants.MySQLKeyWords.LIMIT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(numOfRecords);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		try {
			resultSet = my.executeQuery(query);
		} catch (IOException e) {
			throw new MySQLOpException(e.getMessage());
		}
		/* check if there is only one record in the response */
		try {
			assert(resultSet.isFirst() && resultSet.isLast());
		} catch (SQLException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (resultSet);
	}
	
	private ResultSet getPaperFields(String pmId) throws MySQLOpException {	
		if (pmId == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
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
			resultSet = my.executeQuery(query);
		} catch (IOException e) {
			throw new MySQLOpException(e.getMessage());
		}
		/* check if there is only one record in the response */
		try {
			assert(resultSet.isFirst() && resultSet.isLast());
		} catch (SQLException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (resultSet);
	}
}
