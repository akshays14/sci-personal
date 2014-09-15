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

public class CoreDBOperations {

	private MySQLHandler _my;
	
	public CoreDBOperations(MySQLHandler my) throws MySQLOpException {
		super();
		if (my == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.MYSQL_HANDLER_NULL_MESSAGE);
		}
		this._my = my;
	}

	public DenormalizedFields getDenormalizedFields(long numOfRecords) throws CoreDBOpException {
		int recordsProcessed = 0;
		ResultSet paperSet = null;
		ResultSet venueSet = null;
		ResultSet authorSet = null;
		DenormalizedFields denormFields = new DenormalizedFields();
		// get the numOfRecords records from paper table
		try {
			paperSet = getPaperFields(numOfRecords);
		} catch (MySQLOpException e1) {
			throw new CoreDBOpException(e1.getMessage());
		}
		try {
			while (paperSet.next()) {
				// get paper id
				String paperId = paperSet.getString(CoreDBConstants.PaperFields.ID);
				// get venue id
				String venueId = paperSet.getString(CoreDBConstants.PaperFields.VENUE_ID);
				// populate the DenormalizedFields object with paper fields
				denormFields.populatePaperFields(paperSet);
				// get venue info for this venueId
				venueSet = getVenueFields(venueId);
				// populate the DenormalizedFields object with venue fields
				denormFields.populateVenueFields(venueSet);
				// get author info for this paper
				authorSet = getAuthors(paperId);
				// populate the DenormalizedFields object with venue fields
				denormFields.populateAuthorFields(authorSet);
				// TODO: remove later .. print the fields (for sanity)
				//denormFields.printFields(System.out);
			}
			
			recordsProcessed++;
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		} catch (MySQLOpException e) {
			throw new CoreDBOpException(e.getMessage());
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
		strBuff.append(DBMSConstants.MySQLKeyWords.LIMIT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(numOfRecords);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		System.err.println(query);
		try {
			resultSet = _my.executeQuery(query);
		} catch (IOException e) {
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
			resultSet = _my.executeQuery(query);
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
	
	private ResultSet getVenueFields(String venueId) throws MySQLOpException {
		if (venueId == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.VENUE_ID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
		String query = null;
		ResultSet resultSet = null;
		
		//Create SQL statement
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.VenueFields.PUBLISHER);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.VENUE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.VenueFields.VENUE_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(venueId);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		System.err.println(query);
		try {
			resultSet = _my.executeQuery(query);
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
	
	private ResultSet getAuthors(String pmId) throws MySQLOpException {	
		if (pmId == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/* select paper_to_author.id_author, author.name 
		 * from paper_to_author INNER JOIN author 
		 * ON paper_to_author.id_author = author.id 
		 * where paper_to_author.id_paper=1033; */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToAuthorFields.AUTHOR_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.COMMA);
		strBuff.append(CoreDBConstants.Tables.AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.AuthorFields.NAME);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.INNER_JOIN);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ON);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToAuthorFields.AUTHOR_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(CoreDBConstants.Tables.AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.AuthorFields.ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToAuthorFields.PAPER_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);
		query = strBuff.toString();
		logger.info("Query issued {}", query);
		System.err.println(query);
		try {
			resultSet = _my.executeQuery(query);
		} catch (IOException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (resultSet);
	}
	
	private ResultSet getInstitute(String pmId) throws MySQLOpException {	
		if (pmId == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/* select paper_to_author.id_author, author.name 
		 * from paper_to_author INNER JOIN author 
		 * ON paper_to_author.id_author = author.id 
		 * where paper_to_author.id_paper=1033; */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToAuthorFields.AUTHOR_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.COMMA);
		strBuff.append(CoreDBConstants.Tables.AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.AuthorFields.NAME);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.INNER_JOIN);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ON);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToAuthorFields.AUTHOR_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(CoreDBConstants.Tables.AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.AuthorFields.ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_AUTHOR);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToAuthorFields.PAPER_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		System.err.println(query);
		try {
			resultSet = _my.executeQuery(query);
		} catch (IOException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (resultSet);
	}
}
