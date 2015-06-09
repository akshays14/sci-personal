package com.sciencescape.ds.db.hbase;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.sciencescape.ds.db.mysql.coredb.AuthorFields;
import com.sciencescape.ds.db.mysql.coredb.DenormalizedFields;
import com.sciencescape.ds.db.mysql.coredb.FieldsFields;
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

	/**
	 * @brief write given @ record to HBase table
	 * @param rec data-record to be written
	 * @param hbaseCols array of string representing format of data-record
	 * @throws IOException throws IOException on writing error
	 *
	 * @note the first item in the data-record would be treated as row-key
	 * Function to write a record to a HBase cluster.
	 */

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
		Put p = new Put(Bytes.toBytes(String.valueOf(df.get_id())));
		// add pmid
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.PMID), Bytes.toBytes(NoSQLConstants.Columns.ID), Bytes.toBytes(df.get_pmId()));
		// add DOI
		if (df.get_doi() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.DOI), Bytes.toBytes(NoSQLConstants.Columns.ID), Bytes.toBytes(df.get_doi()));
		}
		// add title
		if (df.get_title() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.TITLE), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_title()));
		}
		// add pubmed release date
		if (df.get_datePmReleased() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.DATE_PM_RELEASED), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_datePmReleased()));
		}
		// add issn
		if (df.get_issn() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.ISSN), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_issn()));
		}
		// add date
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.DATE), Bytes.toBytes(NoSQLConstants.Columns.YEAR), Bytes.toBytes(df.get_year()));
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.DATE), Bytes.toBytes(NoSQLConstants.Columns.MONTH), Bytes.toBytes(df.get_month()));
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.DATE), Bytes.toBytes(NoSQLConstants.Columns.DAY), Bytes.toBytes(df.get_day()));
		// add iso
		if (df.get_iso() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.ISO), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_iso()));
		}
		// add issue
		if (df.get_issue() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.ISSUE), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_issue()));
		}
		// add language
		if (df.get_language() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.LANGUAGE), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_language()));
		}
		// add type
		if (df.get_type() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.TYPE), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_type()));
		}
		// journal info
		if (df.get_journalName() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.JOURNAL), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_journalName()));
		}
		// add eigenfactor
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.EIGENFACTOR), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_eigenFactor()));
		// add country
		if (df.get_country() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.LOCATION), Bytes.toBytes(NoSQLConstants.Columns.COUNTRY), Bytes.toBytes(df.get_country()));
		}
		// add volume
		if (df.get_volume() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.VOULME), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_volume()));
		}
		// add nlm
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.NLM), Bytes.toBytes(NoSQLConstants.Columns.ID), Bytes.toBytes(df.get_nlmId()));
		// add metadata-info
		if (df.get_metadataSource() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.MD), Bytes.toBytes(NoSQLConstants.Columns.SOURCE), Bytes.toBytes(df.get_metadataSource()));
		}
		// add venue info
		p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.VENUE), Bytes.toBytes(NoSQLConstants.Columns.ID), Bytes.toBytes(df.get_venueId()));
		if (df.get_publisher() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.VENUE), Bytes.toBytes(NoSQLConstants.Columns.PUBLISHER), Bytes.toBytes(df.get_publisher()));
		}
		// add authors
		if (df.get_authors() != null) {
			for (Map.Entry<Long, AuthorFields> entry : df.get_authors().entrySet()) {
				String authorNameCol = buildAuthorNameColumnName(entry.getKey());
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.AUTHORS),
						Bytes.toBytes(authorNameCol), Bytes.toBytes(entry.getValue().getName()));
			}
		}
		// add institutions
		if (df.get_institution() != null) {
			for (Map.Entry<Long, InstitutionFields> entry : df.get_institution().entrySet()) {
				String rawAffColName = buildRawAffColumnName(entry.getKey());
				String normAffColName = buildNormAffColumnName(entry.getKey());
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.INSTITUTION),
						Bytes.toBytes(rawAffColName), Bytes.toBytes(entry.getValue().getRawAffiliationName()));
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.INSTITUTION),
						Bytes.toBytes(normAffColName), Bytes.toBytes(entry.getValue().getNormalizedAffiliationName()));
			}
		}
		// add abstract
		if (df.get_abstract() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.ABTSRACT), Bytes.toBytes(NoSQLConstants.Columns.VALUE), Bytes.toBytes(df.get_abstract()));
		}
		// add sections
		if (df.get_sections() != null) {
			for (int i = 0; i < df.get_sections().length; ++i) {
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.SECTIONS), Bytes.toBytes(i), Bytes.toBytes(df.get_sections()[i]));
			}
		}
		// add fields
		if (df.get_fields() != null) {
			for (Map.Entry<Long, FieldsFields> entry : df.get_fields().entrySet()) {
				String fieldNameCol = buildFieldsNameColumnName(entry.getKey());
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.FIELDS),
						Bytes.toBytes(fieldNameCol), Bytes.toBytes(entry.getValue().getName()));
			}
		}
		// add import info
		if (df.get_dateImported() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.IMPORT_INFO), Bytes.toBytes(NoSQLConstants.ColumnKeyWords.IMPORT_DATE), Bytes.toBytes(df.get_dateImported()));
		}
		/* finally put in the table */
		table.put(p);
	}

	public String buildAuthorNameColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(NoSQLConstants.ColumnKeyWords.AUTHOR_NAME);
		colName.append(id);
		return (colName.toString());
	}

	public String buildRawAffColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(NoSQLConstants.ColumnKeyWords.RAW_AFFILIATION);
		colName.append(id);
		return (colName.toString());
	}
	public String buildNormAffColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(NoSQLConstants.ColumnKeyWords.NORM_AFFILIATION);
		colName.append(id);
		return (colName.toString());
	}

	public String buildFieldsNameColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(NoSQLConstants.ColumnKeyWords.FIELD_NAME);
		colName.append(id);
		return (colName.toString());
	}
}
