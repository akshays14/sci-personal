package main.java.com.sciencescape.ds.db.hbase;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import main.java.com.sciencescape.ds.db.rdbms.common.DataContainer;
import main.java.com.sciencescape.ds.db.rdbms.common.DataReader;
import main.java.com.sciencescape.ds.db.rdbms.common.DataRecord;
import main.java.com.sciencescape.ds.db.rdbms.coredb.AuthorFields;
import main.java.com.sciencescape.ds.db.rdbms.coredb.DenormalizedFields;
import main.java.com.sciencescape.ds.db.rdbms.coredb.FieldsFields;
import main.java.com.sciencescape.ds.db.rdbms.coredb.InstitutionFields;
import main.java.com.sciencescape.ds.db.util.NoSQLConstants;

/**
 * @class HbaseHandler HbaseHandler.java
 * @brief handler for HBase
 * @author Akshay
 *
 * Class for handling all the operations to HBase. 
 */
public class HbaseHandler implements DataReader {

	private String _tableName = null;
	private Configuration _config = null;
	private String _zkQurom = null;
	private String _zkPort;
	private String _hbaseMaster = null;
	private HTable _table = null;
	
	/**
	 * @brief constructor for HbaseHandler
	 * @param zkQurom ZooKeeper quorom for hbase cluster
	 * @param zkPort ZooKeeper port for quorom-peers
	 * @param hbaseMaster hbase master server
	 * @param table hbase table
	 * 
	 * Main constructor for HbaseHandler
	 */
	public HbaseHandler(String zkQurom, String zkPort, String hbaseMaster, String table) {
		_zkQurom = zkQurom;
		_zkPort = zkPort;
		_hbaseMaster = hbaseMaster;
		_tableName = table;
	}

	/**
	 * @brief method to set configuration for non-local HBase cluster
	 *  
	 * Method to set the fields required to connect to an external
	 * HBase cluster.
	 */
	private void configureExternalHBase () {
		_config.clear();
		//specify ZK setup
		_config.set(NoSQLConstants.HBaseConfigConstants.ZK_QUOROM, _zkQurom);
		_config.set(NoSQLConstants.HBaseConfigConstants.ZK_PORT, _zkPort);
		// specify HBase setup
		_config.set(NoSQLConstants.HBaseConfigConstants.MASTER, _hbaseMaster);
		_config.set(NoSQLConstants.HBaseConfigConstants.DISTRIBUTED_MODE, 
				NoSQLConstants.HBaseClusterConstants.HBASE_DISTRIBUTED_MODE);
	}

	/**
	 * Implements connect method of {@link DataContainer}.
	 * By default, it creates configuration to connect to local
	 * HBase cluster. Call configureExternalHBase to be
	 * able to connect to a non-local HBase cluster.
	 */
	@Override
	public void connect() throws IOException {
		_config = HBaseConfiguration.create();
		// un-comment this for external HBase
		configureExternalHBase();
	}

	/**
	 * Implements close method of {@link DataContainer}.
	 * @todo implement it.
	 */
	@Override
	public void close() throws IOException {

	}

	/**
	 * @brief initialize Writer for HbaseHandler
	 * @throws IOException
	 * 
	 * Method to initialize writer for HBase. Created a
	 * handle for the HBase table.
	 */
	private void initalizeWriter() throws IOException {
		_table = new HTable(_config, _tableName);
	}

	/**
	 * @brief write given record to HBase table
	 * @param rec data-record to be written
	 * @param hbaseCols array of string representing format of data-record
	 * @throws IOException throws IOException on writing error 
	 * 
	 * @note the first item in the data-record would be treated as row-key
	 * Function to write a record to a HBase cluster.
	 */
	public void writeRecord(DataRecord rec, String[] hbaseCols) throws IOException {
		if (_table == null) {
			initalizeWriter();
		}
		// we will use the first item as the key
		Put p = new Put(Bytes.toBytes((String) rec.getDataAt(0)));
		for (int i = 1; i < hbaseCols.length; i++) {
			String col = hbaseCols[i];
			int index = col.indexOf(":");
			byte data[] = null;
			// treating every field as string
			data = Bytes.toBytes((String) rec.getDataAt(i));
			if (index > 0) {
				p.add(Bytes.toBytes(col.substring(0, index)), Bytes.toBytes(col.substring(index + 1)), data);
			} else {
				p.add(Bytes.toBytes(col), null, data);
			}
			/* finally put in the table */
			_table.put(p);
		}
	}

	public void writeRecord(DenormalizedFields df) throws IOException {
		if (_table == null) {
			initalizeWriter();
		}
		if (df == null) {
			throw new IOException("Given DenormalizedFields object is null");
		}
		// User paper ID as key
		//Put p = new Put(Bytes.toBytes(df.get_id()));
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
						Bytes.toBytes(authorNameCol), Bytes.toBytes(entry.getValue().get_name()));
			}
		}
		// add institutions
		if (df.get_institution() != null) {
			for (Map.Entry<Long, InstitutionFields> entry : df.get_institution().entrySet()) {
				String rawAffColName = buildRawAffColumnName(entry.getKey());
				String normAffColName = buildNormAffColumnName(entry.getKey());
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.INSTITUTION), 
						Bytes.toBytes(rawAffColName), Bytes.toBytes(entry.getValue().get_rawAffiliationName()));
				p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.INSTITUTION), 
						Bytes.toBytes(normAffColName), Bytes.toBytes(entry.getValue().get_normalizedAffiliationName()));
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
						Bytes.toBytes(fieldNameCol), Bytes.toBytes(entry.getValue().get_fieldName()));
			}
		}
		// add import info
		if (df.get_dateImported() != null) {
			p.add(Bytes.toBytes(NoSQLConstants.ColumnFamilies.IMPORT_INFO), Bytes.toBytes(NoSQLConstants.ColumnKeyWords.IMPORT_DATE), Bytes.toBytes(df.get_dateImported()));
		}
		/* finally put in the table */
		_table.put(p);
	}

	/**
	 * Implements readRecord method of DataReader
	 * @todo to be implemented 
	 */
	@Override
	public DataRecord readRecord() throws IOException {
		return null;
	}

	public String buildAuthorNameColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(id);
		colName.append(NoSQLConstants.ColumnKeyWords.AUTHOR_NAME);
		return (colName.toString());
	}

	public String buildRawAffColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(id);
		colName.append(NoSQLConstants.ColumnKeyWords.RAW_AFFILIATION);
		return (colName.toString());
	}
	public String buildNormAffColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(id);
		colName.append(NoSQLConstants.ColumnKeyWords.NORM_AFFILIATION);
		return (colName.toString());
	}

	public String buildFieldsNameColumnName(long id) {
		StringBuilder colName = new StringBuilder();
		colName.append(id);
		colName.append(NoSQLConstants.ColumnKeyWords.FIELD_NAME);
		return (colName.toString());
	}
}
