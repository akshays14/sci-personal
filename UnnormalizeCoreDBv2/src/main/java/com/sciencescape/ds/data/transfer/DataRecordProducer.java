package com.sciencescape.ds.data.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

import com.sciencescape.ds.db.mysql.coredb.CoreDBConstants;
import com.sciencescape.ds.db.mysql.coredb.CoreDBOpException;
import com.sciencescape.ds.db.mysql.coredb.DenormalizedFields;
import com.sciencescape.ds.db.mysqlprovider.MySQLHandler;
import com.sciencescape.ds.db.mysqlprovider.MySQLProviderException;

public class DataRecordProducer implements Runnable {

	private BlockingQueue<DenormalizedFields> queue;
	private CoreDBOperations coreOps;
	private long recordsProcessed;
	private int publicationYear;
	// ResultSet object
	private ResultSet paperSet;
	private ResultSet venueSet;
	private ResultSet authorSet;
	private ResultSet instituteSet;
	private ResultSet conceptSet;
	private ResultSet inCitationSet;
	private ResultSet outCitationSet;
	
	public DataRecordProducer(final BlockingQueue<DenormalizedFields> queue,
			final MySQLHandler mysql, final int publicationYear)
					throws CoreDBOpException {
		if (queue == null) {
			throw new CoreDBOpException("Given queue object is null");
		}
		this.queue = queue;
		this.publicationYear = publicationYear;
		try {
			this.coreOps = new CoreDBOperations(mysql);
		} catch (MySQLProviderException e) {
			throw new CoreDBOpException(e);
		}
		this.recordsProcessed = 0;
		// initialize resultSet object
		paperSet = null;
		venueSet = null;
		authorSet = null;
		instituteSet = null;
		conceptSet = null;
		inCitationSet = null;
		outCitationSet = null;
	}

	public void fetchAndPushDenormalizedFields(int publicationYear)
			throws CoreDBOpException {
		// get the numOfRecords records from paper table
		try {
			paperSet = coreOps.getPaperFieldsByYear(publicationYear);
		} catch (MySQLProviderException e1) {
			throw new CoreDBOpException(e1.getMessage());
		}
		// process each paper
		try {
			while (paperSet.next()) {
				DenormalizedFields denormFields = new DenormalizedFields();
				// get paper id
				String paperId = paperSet.getString(CoreDBConstants.PaperFields.ID);
				// populate the DenormalizedFields object with paper fields
				denormFields.populatePaperFields(paperSet);
				// get venue info for this venueId
				venueSet = coreOps.getVenueFields(paperId);
				// populate the DenormalizedFields object with venue fields
				denormFields.populateVenueFields(venueSet);
				// get author info for this paper
				authorSet = coreOps.getAuthors(paperId);
				// populate the DenormalizedFields object with venue fields
				denormFields.populateAuthorFields(authorSet);
				// get institute info for this paper
				instituteSet = coreOps.getInstitute(paperId);
				// populate the DenormalizedFields object with institute fields
				denormFields.populateInstituteFields(instituteSet);
				// populate sections (NOTE: should be filled by FT pipeline)
				//denormFields.populateFullTextSections(6, 300);
				// get concepts for this paper
				conceptSet = coreOps.getConcepts(paperId);
				// populate the DenormalizedFields object with concepts
				denormFields.populateConceptFields(conceptSet);
				// get incoming citations for this paper
				inCitationSet = coreOps.getInCitations(paperId);
				// populate the DenormalizedFields object with incoming citation
				denormFields.populateInCitationFields(inCitationSet);
				// get incoming citations for this paper
				outCitationSet = coreOps.getOutCitations(paperId);
				// populate the DenormalizedFields object with incoming citation
				denormFields.populateOutCitationFields(outCitationSet);
				
				// TODO: remove later .. print the fields (for sanity)
				//denormFields.printFields(System.out);
				
				// push the de-normalized field object to blocking queue
				try {
					queue.put(denormFields);
				} catch (InterruptedException e) {
					throw new CoreDBOpException(e.getMessage());
				}
				// update number of records processed (may be used later for validation)
				recordsProcessed++;
				// show the progress
				coreOps.showProgress(recordsProcessed);
			}
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		} catch (MySQLProviderException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void pushEmptyDenormalizedFields() throws CoreDBOpException {
		DenormalizedFields denormFields = new DenormalizedFields();
		// set the pmid to for empty record
		denormFields.set_pmId(DataTransferConstants.DataTransfer.EMPTY_RECORD_PMID);
		try {
			queue.put(denormFields);
		} catch (InterruptedException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void clean() throws CoreDBOpException {
		// close the ResultSet object
		try {
			venueSet.close();
			authorSet.close();
			instituteSet.close();
			conceptSet.close();
			paperSet.close();
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}

	public void run() {
		try {
			// push all the papers for this publication year
			fetchAndPushDenormalizedFields(this.publicationYear);
			// push a empty paper
			pushEmptyDenormalizedFields();
		} catch (CoreDBOpException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
