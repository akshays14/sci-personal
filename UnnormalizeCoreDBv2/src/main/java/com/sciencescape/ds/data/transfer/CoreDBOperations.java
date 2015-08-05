package com.sciencescape.ds.data.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sciencescape.ds.db.mysql.coredb.CoreDBConstants;
import com.sciencescape.ds.db.mysql.coredb.CoreDBOpException;
import com.sciencescape.ds.db.mysql.coredb.DenormalizedFields;
import com.sciencescape.ds.db.mysqlprovider.DBMSConstants;
import com.sciencescape.ds.db.mysqlprovider.MySQLHandler;
import com.sciencescape.ds.db.mysqlprovider.MySQLProviderException;

public class CoreDBOperations {
	private MySQLHandler _my;
	private long _totalRecordsProcessed;

	public CoreDBOperations(MySQLHandler my) throws MySQLProviderException {
		if (my == null) {
			throw new MySQLProviderException(
					DBMSConstants.MySQLHandlerOperations.
					MYSQL_HANDLER_NULL_MESSAGE);
		}
		this._my = my;
		this._totalRecordsProcessed = 0L;
	}

	public List<DenormalizedFields> getDenormalizedFields(long startPaperId,
			long endPaperId) throws CoreDBOpException {
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
		} catch (MySQLProviderException e1) {
			throw new CoreDBOpException(e1.getMessage());
		}
		try {
			while (paperSet.next()) {
				DenormalizedFields denormFields = new DenormalizedFields();
				// get paper id
				String paperId = paperSet.getString(CoreDBConstants.PaperFields.ID);
				// populate the DenormalizedFields object with paper fields
				denormFields.populatePaperFields(paperSet);
				// get venue info for this venueId
				venueSet = getVenueFields(paperId);
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
				// populate sections (NOTE: should be populated by FT pipeline)
				//denormFields.populateFullTextSections(6, 300);
				// get fields for this paper
				fieldSet = getConcepts(paperId);
				// populate the DenormalizedFields object with fields
				denormFields.populateConceptFields(fieldSet);
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
		} catch (MySQLProviderException e) {
			throw new CoreDBOpException(e.getMessage());
		} finally {
			_totalRecordsProcessed += recordsProcessed;
		}
		return (dfList);
	}

	ResultSet getPaperFields(long numOfRecords) throws MySQLProviderException {
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
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
		logger.debug("Query issued {}", query);
		System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getPaperFields(long startPaperId, long endPaperId) throws MySQLProviderException {
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
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
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getPaperFieldsByYear(int startYear, int endYear) throws MySQLProviderException {
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
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
		strBuff.append(CoreDBConstants.PaperFields.YEAR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.GREATER_THAN);
		strBuff.append(startYear);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.AND);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.PaperFields.YEAR);
		strBuff.append(DBMSConstants.MySQLKeyWords.LESS_THAN_EQUAL_TO);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(endYear);

		query = strBuff.toString();
		System.out.format("Query issued %s%n", query);
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getPaperFieldsByYear(int publicationYear) throws MySQLProviderException {
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
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
		strBuff.append(CoreDBConstants.PaperFields.YEAR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(publicationYear);

		query = strBuff.toString();
		System.out.format("Query issued: %s%n", query);
		logger.debug("Query issued: {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getPaperFieldsByPubDate(Integer publicationYear,
			Integer publicationMonth, Integer publicationDay)
					throws MySQLProviderException {
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
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
		strBuff.append(CoreDBConstants.PaperFields.YEAR);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(publicationYear);
		
		// add month clause only if it is greater than zero
		if (publicationMonth != null && publicationMonth > 0) {
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(DBMSConstants.MySQLKeyWords.AND);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(CoreDBConstants.PaperFields.MONTH);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(publicationMonth);

			// add day clause only if day and month is greater than zero
			if (publicationDay != null && publicationDay > 0) {
				strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
				strBuff.append(DBMSConstants.MySQLKeyWords.AND);
				strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
				strBuff.append(CoreDBConstants.PaperFields.DAY);
				strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
				strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
				strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
				strBuff.append(publicationDay);
			}
		}
		
		query = strBuff.toString();
		System.out.format("Query issued: %s%n", query);
		logger.debug("Query issued: {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getPaperFields(String pmId) throws MySQLProviderException {
		if (pmId == null) {
			throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
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
		logger.debug("Query issued {}", query);
		resultSet = _my.executeQuery(query);
		/* check if there is only one record in the response */
		try {
			assert(resultSet.isFirst() && resultSet.isLast());
		} catch (SQLException e) {
			throw new MySQLProviderException(e.getMessage());
		}
		return (resultSet);
	}

	ResultSet getVenueFields(String paperId) throws MySQLProviderException {
		if (paperId == null) {
			throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PAPER_ID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		// select publisher from venue, paper_to_venue where venue.id =
		// paper_to_venue.id_venue and paper_to_venue.id_paper = 2;

		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.VENUE);
		strBuff.append(DBMSConstants.MySQLKeyWords.COMMA);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_VENUE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		
		strBuff.append(CoreDBConstants.Tables.VENUE);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.VenueFields.VENUE_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_VENUE);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToVenueFields.VENUE_ID);
		
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.AND);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_VENUE);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToVenueFields.PAPER_ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(paperId);

		query = strBuff.toString();
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		/* check if there is only one record in the response */
		try {
			assert(resultSet.isFirst() && resultSet.isLast());
		} catch (SQLException e) {
			throw new MySQLProviderException(e.getMessage());
		}
		return (resultSet);
	}

	ResultSet getAuthors(String pmId) throws MySQLProviderException {
		if (pmId == null) {
			throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/* select *
		 * from paper_to_author INNER JOIN author
		 * ON paper_to_author.id_author = author.id
		 * where paper_to_author.id_paper=1033; */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
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
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getInstitute(String pmId) throws MySQLProviderException {
		if (pmId == null) {
			throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/*
		 * select * from paper_to_institution INNER JOIN institution ON
		 * paper_to_institution.id_institution = institution.id where
		 * paper_to_institution.id_paper = 8560263;
		 */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
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
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getConcepts(String pmId) throws MySQLProviderException {
		if (pmId == null) {
			throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/*
		 * select * from paper_to_concept INNER JOIN concept
		 * ON concept.id = paper_to_concept.id_concept
		 * where paper_to_concept.id_paper = 8560263\G
		 */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_CONCEPT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.INNER_JOIN);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.CONCEPT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ON);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_CONCEPT);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToConceptFields.ID_CONCEPT);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(CoreDBConstants.Tables.CONCEPT);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.ConceptFields.ID);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.PAPER_TO_CONCEPT);
		strBuff.append(DBMSConstants.MySQLKeyWords.DOT);
		strBuff.append(CoreDBConstants.PaperToConceptFields.ID_PAPER);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);
		
		query = strBuff.toString();
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getInCitations(String pmId) throws MySQLProviderException {
		if (pmId == null) {
			throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
		}
		Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
		String query = null;
		ResultSet resultSet = null;

		//Create SQL statement
		/* select * from citation where id_from_paper = 2439601\G */
		StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.Tables.CITATION);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
		strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
		strBuff.append(CoreDBConstants.CitationFields.ID_TO_PAPER);
		strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
		strBuff.append(pmId);
		
		query = strBuff.toString();
		logger.debug("Query issued {}", query);
		//System.err.println(query);
		resultSet = _my.executeQuery(query);
		return (resultSet);
	}

	ResultSet getOutCitations(String pmId) throws MySQLProviderException {
			if (pmId == null) {
				throw new MySQLProviderException(DBMSConstants.MySQLHandlerOperations.PMID_NULL_MESSAGE);
			}
			Logger logger = LoggerFactory.getLogger(CoreDBOperations.class);
			String query = null;
			ResultSet resultSet = null;

			//Create SQL statement
			/* select * from citation where id_to_paper = 2439601\G */
			StringBuilder strBuff = new StringBuilder(DBMSConstants.MySQLKeyWords.SELECT);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(DBMSConstants.MySQLKeyWords.ALL);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(DBMSConstants.MySQLKeyWords.FROM);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(CoreDBConstants.Tables.CITATION);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(DBMSConstants.MySQLKeyWords.WHERE);
			strBuff.append(DBMSConstants.MySQLKeyWords.SPACE);
			strBuff.append(CoreDBConstants.CitationFields.ID_FROM_PAPER);
			strBuff.append(DBMSConstants.MySQLKeyWords.EQUALS);
			strBuff.append(pmId);
			
			query = strBuff.toString();
			logger.debug("Query issued {}", query);
			//System.err.println(query);
			resultSet = _my.executeQuery(query);
			return (resultSet);
		}

	void showProgress(String paperId) {
		if (Integer.parseInt(paperId)%1000 == 0) {
			System.err.println("Done " + paperId + "records.");
		}
	}

	void showProgress(long recordsProcessed) {
		if (recordsProcessed%1000 == 0) {
			System.err.format("Done with %d records.%n", recordsProcessed);
		}
	}

	public long getTotalNumberOfRecordsProcessed() {
		return _totalRecordsProcessed;
	}
}
