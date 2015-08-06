package com.sciencescape.ds.db.hbase;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.sciencescape.ds.db.mysql.coredb.AuthorFields;
import com.sciencescape.ds.db.mysql.coredb.CitationFields;
import com.sciencescape.ds.db.mysql.coredb.ConceptFields;
import com.sciencescape.ds.db.mysql.coredb.DenormalizedFields;
import com.sciencescape.ds.db.mysql.coredb.InstitutionFields;

/*import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.coredb.AuthorFields;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.coredb.FieldsFields;
import main.java.com.sciencescape.ds.db.rdbms.coredb.InstitutionFields;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;
 */
/**
 * Simple Hbase provider for handling all the operations to HBase.
 *
 * @author Akshay
 * @version 0.1
 */
public class HbaseHandler {

	/**!< hbase table name */
	private String tableName = null;
	/**!< hadoop configuration */
	private Configuration config = null;
	/**!< zookeeper quorom */
	private String zkQurom = null;
	/**!< zookeeper port */
	private String zkPort;
	/**!< hbase master machine */
	private String hbaseMaster = null;
	/**!< hbase table object */
	private HTable table = null;

	/**
	 * Main constructor for HbaseHandler, expecting all deployment
	 * specific variables.
	 *
	 * @param pZkQurom ZooKeeper quorom for hbase cluster
	 * @param pZkPort ZooKeeper port for quorom-peers
	 * @param pHbaseMaster hbase master server
	 * @param pTableName hbase table name
	 */
	public HbaseHandler(final String pZkQurom, final String pZkPort,
			final String pHbaseMaster, final String pTableName) {
		this.zkQurom = pZkQurom;
		this.zkPort = pZkPort;
		this.hbaseMaster = pHbaseMaster;
		this.tableName = pTableName;
	}

	/**
	 * Method to set the fields required to connect to an external
	 * HBase cluster.
	 */
	private void configureExternalHBase() {
		config.clear();
		//specify ZK setup
		config.set(Constants.HBaseConfig.ZK_QUOROM, zkQurom);
		config.set(Constants.HBaseConfig.ZK_PORT, zkPort);
		// specify HBase setup
		config.set(Constants.HBaseConfig.MASTER, hbaseMaster);
		config.set(Constants.HBaseConfig.DISTRIBUTED_MODE,
				Constants.Defaults.HBASE_DISTRIBUTED_MODE);
	}

	/**
	 * Creates configuration to connect to local or remote HBase cluster.
	 *
	 * @param local if the hbase cluster is local of remote
	 * @throws IOException if not able to open table object on given table name
	 */
	public final void connect(final boolean local) throws IOException {
		this.config = HBaseConfiguration.create();
		if (!local) {
			// do configuration needed for remote hbase cluster
			configureExternalHBase();
		}
		// creates hbase table object
		this.table = new HTable(this.config, this.tableName);
		// disabling autoflush for write performance
		this.table.setAutoFlush(false, true);
	}

	/**
	 * Close connection to the hbase table in object.
	 *
	 * @throws IOException if not able to close hbase table connection
	 */
	public final void close() throws IOException {
		// close the connection to table
		this.table.close();
		// clear the hadoop configuration object
		this.config.clear();
	}

	public void writePaperFields(Put p, DenormalizedFields df) throws IOException {
		// columns in ID CF
		byte[] idCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.ID);
		p.addImmutable(idCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.PMID), Bytes.toBytes(df.get_pmId()));
		if(df.get_doi() != null) {
			p.addImmutable(idCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.DOI), Bytes.toBytes(df.get_doi()));
		}
		if(df.get_pmcId() != null) {
			p.addImmutable(idCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.PMC), Bytes.toBytes(df.get_pmcId()));
		}
		if(df.get_nlmId() != null) {
			p.addImmutable(idCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.NLM), Bytes.toBytes(df.get_nlmId()));
		}

		// columns in PAPER CF
		byte[] paperCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.PAPER);
		if(df.get_title() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.TITLE), Bytes.toBytes(df.get_title()));
		}
		if(df.get_issn() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.ISSN), Bytes.toBytes(df.get_issn()));
		}
		if(df.get_iso() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.ISO), Bytes.toBytes(df.get_iso()));
		}
		if(df.get_volume() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.VOLUME), Bytes.toBytes(df.get_volume()));
		}
		if(df.get_issue() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.ISSUE), Bytes.toBytes(df.get_issue()));
		}
		if(df.get_type() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.TYPE), Bytes.toBytes(df.get_type()));
		}
		if(df.get_abstract() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.ABSTRACT), Bytes.toBytes(df.get_abstract()));
		}
		if(df.get_abstractOther() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.ABSTRACT_OTH), Bytes.toBytes(df.get_abstractOther()));
		}
		if(df.get_journalName() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.JOURNAL), Bytes.toBytes(df.get_journalName()));
		}
		if(df.get_meshTerms() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.MESH), Bytes.toBytes(df.get_meshTerms()));
		}
		if(df.get_keywords() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.KEYWORDS), Bytes.toBytes(df.get_keywords()));
		}
		if(df.get_conceptsRaw() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.CONCEPTS), Bytes.toBytes(df.get_conceptsRaw()));
		}
		if(df.get_grants() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.GRANTS), Bytes.toBytes(df.get_grants()));
		}
		if(df.get_country() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.COUNTRY), Bytes.toBytes(df.get_country()));
		}
		if(df.get_language() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.LANG), Bytes.toBytes(df.get_language()));
		}
		if(df.get_source() != null) {
			p.addImmutable(paperCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.SOURCE), Bytes.toBytes(df.get_source()));
		}

		// columns in DATE CF
		byte[] dateCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.DATE);
		p.addImmutable(dateCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.YEAR), Bytes.toBytes(df.get_year()));
		p.addImmutable(dateCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.MONTH), Bytes.toBytes(df.get_month()));
		p.addImmutable(dateCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.DAY), Bytes.toBytes(df.get_day()));
		// cannot be null (but can be all 0 .. like '0000-00-00 00:00:00')
		// which our JDBC would convert to NULL so need to handle null
		if(df.get_datePmReleased() != null) {
			p.addImmutable(dateCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.PM_RELEASED), Bytes.toBytes(df.get_datePmReleased()));
		}
		// cannot be null
		p.addImmutable(dateCFBytes, Bytes.toBytes(NoSQLConstants.PaperColumns.DATE_IMPORTED), Bytes.toBytes(df.get_dateImported()));
	}

	public void writeCitationFields(Put p, DenormalizedFields df) throws IOException {
		byte[] citationCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.CITATION);
		// general columns
		p.addImmutable(citationCFBytes, Bytes.toBytes(NoSQLConstants.CitationColumns.NUM_OF_REF), Bytes.toBytes(df.get_numOfReferences()));

		// incoming citations related
		int numberOfIncomingCitations = 0;
		if (df.get_inCitations() != null) {
			for (Map.Entry<Long, CitationFields> entry : df.get_inCitations().entrySet()) {
				long id = entry.getValue().getId();
				numberOfIncomingCitations++;
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_ID, id), Bytes.toBytes(entry.getValue().getId()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_PAPER_ID, id), Bytes.toBytes(entry.getValue().getIdFromPaper()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_DOI, id), Bytes.toBytes(entry.getValue().getDoiFromPaper()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_MONTH, id), Bytes.toBytes(entry.getValue().getMonth()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_YEAR, id), Bytes.toBytes(entry.getValue().getYear()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_CONF_SCORE, id), Bytes.toBytes(entry.getValue().getConfidenceScore()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_CONF_LEVEL, id), Bytes.toBytes(entry.getValue().getConfidenceLevel()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_SOURCE, id), Bytes.toBytes(entry.getValue().getSource()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.IN_DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getDateImported()));
			}
		}
		p.addImmutable(citationCFBytes, Bytes.toBytes(NoSQLConstants.CitationColumns.IN_COUNT), Bytes.toBytes(numberOfIncomingCitations));

		// outgoing citations related
		int numberOfOutgoingCitations = 0;
		if (df.get_outCitations() != null) {
			for (Map.Entry<Long, CitationFields> entry : df.get_outCitations().entrySet()) {
				long id = entry.getValue().getId();
				numberOfOutgoingCitations++;
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_ID, id), Bytes.toBytes(entry.getValue().getId()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_PAPER_ID, id), Bytes.toBytes(entry.getValue().getIdToPaper()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_DOI, id), Bytes.toBytes(entry.getValue().getDoiToPaper()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_MONTH, id), Bytes.toBytes(entry.getValue().getMonth()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_YEAR, id), Bytes.toBytes(entry.getValue().getYear()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_CONF_SCORE, id), Bytes.toBytes(entry.getValue().getConfidenceScore()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_CONF_LEVEL, id), Bytes.toBytes(entry.getValue().getConfidenceLevel()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_SOURCE, id), Bytes.toBytes(entry.getValue().getSource()));
				p.addImmutable(citationCFBytes, buildFieldWithId(NoSQLConstants.CitationColumns.OUT_DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getDateImported()));
			}
		}
		p.addImmutable(citationCFBytes, Bytes.toBytes(NoSQLConstants.CitationColumns.OUT_COUNT), Bytes.toBytes(numberOfOutgoingCitations));
	}

	public void writeEigenfactorFields(Put p, DenormalizedFields df) throws IOException {
		byte[] efCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.EIGENFACTOR);
		p.addImmutable(efCFBytes, Bytes.toBytes(NoSQLConstants.EFColumns.EF), Bytes.toBytes(df.get_eigenFactor()));
	}

	public void writeVenueFields(Put p, DenormalizedFields df) throws IOException {
		byte[] venueCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.VENUE);

		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.ID), Bytes.toBytes(df.get_venueId()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.ISO_ABRV), Bytes.toBytes(df.get_vIsoAbbreviation()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.TYPE), Bytes.toBytes(df.get_vType()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.TITLE), Bytes.toBytes(df.get_vTitle()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.TITLE_WD), Bytes.toBytes(df.get_vTitleWithoutDiacritics()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.START_YEAR), Bytes.toBytes(df.get_vYearStart()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.END_YEAR), Bytes.toBytes(df.get_vYearEnd()));
		if (df.get_vFrequency() != null) {
			p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.FREQUENCY), Bytes.toBytes(df.get_vFrequency()));
		}
		if (df.get_vLanguage() != null) {
			p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.LANGUAGE), Bytes.toBytes(df.get_vLanguage()));
		}
		if (df.get_vPublisher() != null) {
			p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.PUBLISHER), Bytes.toBytes(df.get_vPublisher()));
		}
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.SOURCE), Bytes.toBytes(df.get_vSource()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.ISO_WD), Bytes.toBytes(df.get_vIsoWithoutDiacritic()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.EF_MEDIAN_RAW), Bytes.toBytes(df.get_vEFMedianRaw()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.EF_MEDIAN_NORMALIZED), Bytes.toBytes(df.get_vEFMedianNormalized()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.DATE_IMPORTED), Bytes.toBytes(df.get_vDateImported()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.MAPPING_SOURCE), Bytes.toBytes(df.get_vMapSource()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.CONFIDENCE_SCORE), Bytes.toBytes(df.get_vMapConfidenceScore()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.CONFIDENCE_LEVEL), Bytes.toBytes(df.get_vMapConfidenceLevel()));
		p.addImmutable(venueCFBytes, Bytes.toBytes(NoSQLConstants.VenueColumns.MAPPING_DATE_IMPORTED), Bytes.toBytes(df.get_vMapDateImported()));
	}

	public void writeAuthorFields(Put p, DenormalizedFields df) throws IOException {
		byte[] authorCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.AUTHORS);

		if (df.get_authors() != null) {
			for (Map.Entry<Long, AuthorFields> entry : df.get_authors().entrySet()) {
				long id = entry.getValue().getId();
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.ID, id), Bytes.toBytes(entry.getValue().getId()));
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.NAME, id), Bytes.toBytes(entry.getValue().getName()));
				if (entry.getValue().getHomepage() != null) {
					p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.HOMEPAGE, id), Bytes.toBytes(entry.getValue().getHomepage()));
				}
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.SOURCE, id), Bytes.toBytes(entry.getValue().getAuthorSource()));
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getAuthorDateImported()));
				if (entry.getValue().getOrcidId() != null) {
					p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.ORCID_ID, id), Bytes.toBytes(entry.getValue().getOrcidId()));
				}
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.INDEX, id), Bytes.toBytes(entry.getValue().getIndex()));
				if (entry.getValue().getRawName() != null) {
					p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.RAW_NAME, id), Bytes.toBytes(entry.getValue().getRawName()));
				}
				if (entry.getValue().getRawAffiliation() != null) {
					p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.RAW_AFFILIATION, id), Bytes.toBytes(entry.getValue().getRawAffiliation()));
				}
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.DISAMBIGUATION_SCORE, id), Bytes.toBytes(entry.getValue().getDisambiguationScore()));
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.MAPPING_SOURCE, id), Bytes.toBytes(entry.getValue().getMappingSource()));
				p.addImmutable(authorCFBytes, buildFieldWithId(NoSQLConstants.AuthorColumns.MAPPING_DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getMappingDateImported()));
			}
		}
	}

	public void writeInstitutionFields(Put p, DenormalizedFields df) throws IOException {
		byte[] instCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.INSTITUTION);

		if (df.get_institution() != null) {
			for (Map.Entry<Long, InstitutionFields> entry : df.get_institution().entrySet()) {
				long id = entry.getValue().getId();
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.ID, id), Bytes.toBytes(entry.getValue().getId()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.NAME, id), Bytes.toBytes(entry.getValue().getName()));
				if (entry.getValue().getExternalName() != null) {
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.EXTERNAL_NAME, id), Bytes.toBytes(entry.getValue().getExternalName()));
				}
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.CITY, id), Bytes.toBytes(entry.getValue().getCity()));
				if (entry.getValue().getExternalCity() != null) {
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.EXTERNAL_CITY, id), Bytes.toBytes(entry.getValue().getExternalCity()));
				}
				if (entry.getValue().getZipcode() != null) {
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.ZIPCODE, id), Bytes.toBytes(entry.getValue().getZipcode()));
				}
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.COUNTRY, id), Bytes.toBytes(entry.getValue().getCountry()));
				if (entry.getValue().getState() != null) {
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.STATE, id), Bytes.toBytes(entry.getValue().getState()));
				}
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.TYPE, id), Bytes.toBytes(entry.getValue().getType()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.ID_PARENT, id), Bytes.toBytes(entry.getValue().getIdParent()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.ID_PARENT_HIGHEST, id), Bytes.toBytes(entry.getValue().getIdParentHighest()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.EF, id), Bytes.toBytes(entry.getValue().getEigenfactor()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.SOURCE, id), Bytes.toBytes(entry.getValue().getInstitutionSource()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getInstitutionDateImported()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.AUTHOR_INDEX, id), Bytes.toBytes(entry.getValue().getAuthorIndex()));
				//p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.INDEX_AFFILIATION, id), Bytes.toBytes(entry.getValue().getIndexAffiliation()));
				p.addImmutable(instCFBytes, buildFieldWithIdAndValue(NoSQLConstants.InstitutionColumns.INDEX_AFFILIATION, entry.getValue().getIndexAffiliation(), id), Bytes.toBytes(entry.getValue().getIndexAffiliation()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.PROTO_AFFILIATION, id), Bytes.toBytes(entry.getValue().getProtoAffiliation()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.SOLR_SCORE, id), Bytes.toBytes(entry.getValue().getSolrScore()));
				if (entry.getValue().getCrfSegmentation() != null) {
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.CRF_SEGMENTATION, id), Bytes.toBytes(entry.getValue().getCrfSegmentation()));
				}
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.CONFIDENCE_LEVEL, id), Bytes.toBytes(entry.getValue().getConfidenceLevel()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.MAPPING_SOURCE, id), Bytes.toBytes(entry.getValue().getMappingSource()));
				p.addImmutable(instCFBytes, buildFieldWithId(NoSQLConstants.InstitutionColumns.MAPPING_DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getMappingDateImported()));
			}
		}
	}

	public void writeConceptFields(Put p, DenormalizedFields df) throws IOException {
		byte[] conceptCFBytes = Bytes.toBytes(NoSQLConstants.ColumnFamilies.CONCEPT);

		if (df.get_concepts() != null) {
			for (Map.Entry<Long, ConceptFields> entry : df.get_concepts().entrySet()) {
				long id = entry.getValue().getId();
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.ID, id), Bytes.toBytes(entry.getValue().getId()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.ID_SOURCE, id), Bytes.toBytes(entry.getValue().getIdSource()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.NAME, id), Bytes.toBytes(entry.getValue().getName()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.IS_ACTIVE, id), Bytes.toBytes(entry.getValue().isActive()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.IS_GENERIC, id), Bytes.toBytes(entry.getValue().isGeneric()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.SPECIFICITY_SCORE, id), Bytes.toBytes(entry.getValue().getSpecificityScore()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.SOURCE, id), Bytes.toBytes(entry.getValue().getConceptSource()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getConceptDateImported()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.ID_RAW, id), Bytes.toBytes(entry.getValue().getIdConceptRaw()));
				if (entry.getValue().getConceptSourceType() != null) {
					p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.SOURCE_TYPE, id), Bytes.toBytes(entry.getValue().getConceptSourceType()));
				}
				if (entry.getValue().getMappingSource() != null) {
					p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.MAPPING_SOURCE, id), Bytes.toBytes(entry.getValue().getMappingSource()));
				}
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.ID_SECTION, id), Bytes.toBytes(entry.getValue().getIdSection()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.ID_SENTENCE, id), Bytes.toBytes(entry.getValue().getIdSentence()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.COUNT, id), Bytes.toBytes(entry.getValue().getCount()));
				if (entry.getValue().getRawMention() != null) {
					p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.RAW_MENTION, id), Bytes.toBytes(entry.getValue().getRawMention()));
				}
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.CONFIDENCE_SCORE, id), Bytes.toBytes(entry.getValue().getConfidenceScore()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.CONFIDENCE_LEVEL, id), Bytes.toBytes(entry.getValue().getConfidenceLevel()));
				p.addImmutable(conceptCFBytes, buildFieldWithId(NoSQLConstants.ConceptColumns.MAPPING_DATE_IMPORTED, id), Bytes.toBytes(entry.getValue().getMappingDateImported()));

			}
		}
	}

	/**
	 * Function to write {@link DenormalizedFields} to a HBase cluster.
	 *
	 * @param df
	 * @throws IOException
	 */
	public void writeRecord(DenormalizedFields df) throws IOException {
		if (df == null) {
			throw new IOException("Given DenormalizedFields object is null");
		}
		// User paper ID as key
		//Put p = new Put(Bytes.toBytes(df.get_id()));	// store key as byte[]
		/**
		 * Note : Storing key as Strings so that it is easy to use HBase shell
		 */
		Put p = new Put(Bytes.toBytes(String.valueOf(df.get_id())));

		// put paper fields
		writePaperFields(p, df);
		// put citation fields
		writeCitationFields(p, df);
		// put eigenfactor fields
		writeEigenfactorFields(p, df);
		// put venue fields
		writeVenueFields(p, df);
		// put author fields
		writeAuthorFields(p, df);
		// put institution fields
		writeInstitutionFields(p, df);
		// put concept fields
		writeConceptFields(p, df);

		/* finally put in the table */
		table.put(p);
	}

	public byte[] buildFieldWithId(String field, long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(field);
		colName.append(id);
		return (Bytes.toBytes(colName.toString()));
	}
	
	public byte[] buildFieldWithIdAndValue(String field, int value, long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(field);
		colName.append(value);
		colName.append(NoSQLConstants.KeyDesign.KEY_DEMILITER);
		colName.append(id);
		return (Bytes.toBytes(colName.toString()));
	}
}
