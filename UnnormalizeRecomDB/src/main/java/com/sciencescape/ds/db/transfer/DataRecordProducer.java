package main.java.com.sciencescape.ds.db.transfer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;

import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLHandler;
import main.java.com.sciencescape.ds.db.rdbms.mysqlhandler.MySQLOpException;
import main.java.com.sciencescape.ds.db.util.CoreDBConstants;
import main.java.com.sciencescape.ds.db.util.DataTransferConstants;

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
	private ResultSet fieldSet;

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
		} catch (MySQLOpException e) {
			throw new CoreDBOpException(e.getMessage());
		}
		this.recordsProcessed = 0;
		// initialize resultSet object
		paperSet = null;
		venueSet = null;
		authorSet = null;
		instituteSet = null;
		fieldSet = null;
	}

	@Override
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

	public void fetchAndPushDenormalizedFields(int publicationYear)
			throws CoreDBOpException {
		// get the numOfRecords records from paper table
		try {
			paperSet = coreOps.getPaperFieldsByYear(publicationYear);
		} catch (MySQLOpException e1) {
			throw new CoreDBOpException(e1.getMessage());
		}
		// process each paper
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
				venueSet = coreOps.getVenueFields(venueId);
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
				// populate sections (NOTE: randomly generated currently)
				denormFields.populateFullTextSections(6, 300);
				// get fields for this paper
				fieldSet = coreOps.getFields(paperId);
				// populate the DenormalizedFields object with fields
				denormFields.populateFieldsFields(fieldSet);
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
		} catch (MySQLOpException e) {
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
			fieldSet.close();
			paperSet.close();
		} catch (SQLException e) {
			throw new CoreDBOpException(e.getMessage());
		}
	}
}
