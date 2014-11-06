package main.java.com.sciencescape.ds.db.transfer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOpException;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOperations;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.DBMSConstants;

@SuppressWarnings("unused")
public class CoreDBOperations {
	private MySQLHandler _my;
	private long _totalRecordsProcessed;

	public CoreDBOperations(MySQLHandler my) throws MySQLOpException {
		super();
		if (my == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.MYSQL_HANDLER_NULL_MESSAGE);
		}
		this._my = my;
		this._totalRecordsProcessed = 0L;
	}

	public List<DenormalizedFields> getDenormalizedFields(long startPaperId, long endPaperId) throws CoreDBOpException {
		int recordsProcessed = 0;
		ResultSet paperSet = null;
		ResultSet venueSet = null;
		ResultSet authorSet = null;
		ResultSet instituteSet = null;
		ResultSet fieldSet = null;
		List<DenormalizedFields> dfList = new ArrayList<DenormalizedFields>();
		// get the numOfRecords records from paper table
		try {
			//paperSet = getPaperFields(numOfRecords);
			paperSet = getPaperFields(startPaperId, endPaperId);
		} catch (MySQLOpException e1) {
			throw new CoreDBOpException(e1.getMessage());
		}
		try {
			while (paperSet.next()) {
				DenormalizedFields denormFields = new DenormalizedFields();
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
				// get institute info for this paper
				instituteSet = getInstitute(paperId);
				// populate the DenormalizedFields object with institute fields
				denormFields.populateInstituteFields(instituteSet);
				// populate sections (NOTE: randomly generated currently)
				denormFields.populateFullTextSections(6, 300);
				// get fields for this paper
				fieldSet = getFields(paperId);
				// populate the DenormalizedFields object with fields
				denormFields.populateFieldsFields(fieldSet);
				// TODO: remove later .. print the fields (for sanity)
				//denormFields.printFields(System.out);
				// at last add it to the list
				dfList.add(denormFields);
				// update number of records processed (may be used later for validation)
				recordsProcessed++;
				// show the progress
				showProgress(paperId);
			}
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		} catch (MySQLOpException e) {
			throw new CoreDBOpException(e.getMessage());
		} finally {
			_totalRecordsProcessed += recordsProcessed;
		}
		return (dfList);
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

	private ResultSet getPaperFields(long startPaperId, long endPaperId) throws MySQLOpException {
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
		strBuff.append(CoreDBConstants.AuthorFields.ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.GREATER_THAN);
		strBuff.append(startPaperId);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.AND);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.AuthorFields.ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.LESS_THAN_EQUAL_TO);
		strBuff.append(endPaperId);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		//System.err.println(query);
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
		//System.err.println(query);
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
		//System.err.println(query);
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
		/* select paper_to_institution.proto_affiliation, paper_to_institution.id_institution, institution.name
		 * from paper_to_institution INNER JOIN institution
		 * ON paper_to_institution.id_institution = institution.id
		 * where paper_to_institution.id_paper = 8560263; */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToInstitutionFields.AFFILIATION_PROTO);
		strBuff.append(DBMSConstants.MySQLKeyWords.COMMA);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToInstitutionFields.INSITUTION_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.COMMA);
		strBuff.append(CoreDBConstants.Tables.INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.InstitutionFields.NAME);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.INNER_JOIN);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ON);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToInstitutionFields.INSITUTION_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(CoreDBConstants.Tables.INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.InstitutionFields.ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_INSTITUTION);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToInstitutionFields.PAPER_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		//System.err.println(query);
		try {
			resultSet = _my.executeQuery(query);
		} catch (IOException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (resultSet);
	}

	private ResultSet getFields(String pmId) throws MySQLOpException {
		if (pmId == null) {
			throw new MySQLOpException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(MySQLOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/*
		 * select id_field, field_name from paper_to_field where id_paper = pmid
		 */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.PaperToFieldFields.FIELD_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.COMMA);
		strBuff.append(CoreDBConstants.PaperToFieldFields.FIELD_NAME);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_FIELD);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.PaperToFieldFields.PAPER_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);

		query = strBuff.toString();
		logger.info("Query issued {}", query);
		//System.err.println(query);
		try {
			resultSet = _my.executeQuery(query);
		} catch (IOException e) {
			throw new MySQLOpException(e.getMessage());
		}
		return (resultSet);
	}

	private void showProgress(String paperId) {
		if (Integer.parseInt(paperId)%1000 == 0) {
			System.err.println("Done " + paperId + "records.");
		}
	}

	public long getTotalNumberOfRecordsProcessed() {
		return _totalRecordsProcessed;
	}
}
