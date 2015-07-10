package com.sciencescape.ds.db.hbase;

public class NoSQLConstants {

	public static class HBaseConfig {
		public static final String ZK_QUOROM = "hbase.zookeeper.quorum";
		public static final String ZK_PORT = "hbase.zookeeper.property.clientPort";
		public static final String MASTER= "hbase.master";
		public static final String ROOT_DIR ="hbase.rootdir";
		public static final String DISTRIBUTED_MODE = "hbase.cluster.distributed";
	}

	public static class HBaseCluster {
		public static final String HBASE_MASTER = "dev1:60000";
		public static final String ZK_QUOROM = "dev1:2181,dev2:2181,dev3:2181";
		public static final String ZK_CLIENT_PORT = "2181";
		public static final String HBASE_DISTRIBUTED_MODE = "true";
	}

	public static class ColumnFamilies {
		public static final String ID = "ID";
		public static final String PAPER = "P";
		public static final String DATE = "D";
		public static final String CITATION = "CI";
		public static final String EIGENFACTOR = "EF";
		public static final String VENUE = "V";
		public static final String AUTHORS = "A";
		public static final String INSTITUTION = "I";
		public static final String CONCEPT = "CO";
	}

	public static class PaperColumns {
		// columns in ID CF
		public static final String PMID = "pmid";
		public static final String DOI = "doi";
		public static final String PMC = "pmc";
		public static final String NLM = "nlm";
		// columns in PAPER CF
		public static final String TITLE = "title";
		public static final String ISSN = "issn";
		public static final String ISO = "iso";
		public static final String VOLUME = "volume";
		public static final String ISSUE = "issue";
		public static final String TYPE = "type";
		public static final String ABSTRACT = "abst";
		public static final String ABSTRACT_OTH = "abst_oth";
		public static final String JOURNAL = "journal";
		public static final String MESH = "mesh";
		public static final String KEYWORDS = "keyw";
		public static final String CONCEPTS = "concepts";
		public static final String GRANTS = "grants";
		public static final String COUNTRY = "country";
		public static final String LANG = "lang";
		public static final String SOURCE = "source";
		// columns in DATE CF
		public static final String YEAR = "y";
		public static final String MONTH = "m";
		public static final String DAY = "d";
		public static final String PM_RELEASED = "pm_rel";
		public static final String DATE_IMPORTED = "d_imp";
	}
	
	public static class CitationColumns {
		public static final String NUM_OF_REF = "num_of_ref";
		// incoming citations related
		public static final String OUT_COUNT = "ocount";
		public static final String OUT_ID = "oid_";
		public static final String OUT_PAPER_ID = "opid_";
		public static final String OUT_DOI = "odoi_";
		public static final String OUT_MONTH = "omonth_";
		public static final String OUT_YEAR = "oyear_";
		public static final String OUT_CONF_SCORE = "oconf_score_";
		public static final String OUT_CONF_LEVEL = "oconf_level_";
		public static final String OUT_SOURCE = "osource_";
		public static final String OUT_DATE_IMPORTED = "od_imp_";
		// outgoing citations related
		public static final String IN_COUNT = "icount";
		public static final String IN_ID = "iid_";
		public static final String IN_PAPER_ID = "ipid_";
		public static final String IN_DOI = "idoi_";
		public static final String IN_MONTH = "imonth_";
		public static final String IN_YEAR = "iyear_";
		public static final String IN_CONF_SCORE = "iconf_score_";
		public static final String IN_CONF_LEVEL = "iconf_level_";
		public static final String IN_SOURCE = "isource_";
		public static final String IN_DATE_IMPORTED = "id_imp_";
	}
	
	public static class EFColumns {
		public static final String EF = "ef";
	}
	
	public static class VenueColumns {
		public static final String ID = "id";
		public static final String ISO_ABRV = "iso_abrv";
		public static final String TYPE = "type";
		public static final String TITLE = "title";
		public static final String TITLE_WD = "title_wd";
		public static final String START_YEAR = "syear";
		public static final String END_YEAR = "eyear";
		public static final String FREQUENCY = "freq";
		public static final String LANGUAGE = "lang";
		public static final String PUBLISHER = "pub";
		public static final String SOURCE = "src";
		public static final String ISO_WD = "iso_wd";
		public static final String EF_MEDIAN_RAW = "ef_med_r";
		public static final String EF_MEDIAN_NORMALIZED = "ef_med_n";
		public static final String DATE_IMPORTED = "d_imp";
		public static final String MAPPING_SOURCE = "map_src";
		public static final String CONFIDENCE_SCORE = "conf_scr";
		public static final String CONFIDENCE_LEVEL = "conf_lvl";
		public static final String MAPPING_DATE_IMPORTED = "map_d_imp";
	}
	
	public static class AuthorColumns {
		public static final String ID = "id_";
		public static final String NAME = "n_";
		public static final String HOMEPAGE = "hp_";
		public static final String SOURCE = "src_";
		public static final String DATE_IMPORTED = "d_imp_";
		public static final String ORCID_ID = "orcid_";
		public static final String INDEX = "indx_";
		public static final String RAW_NAME = "rn_";
		public static final String RAW_AFFILIATION = "ra_";
		public static final String DISAMBIGUATION_SCORE = "dis_scr_";
		public static final String MAPPING_SOURCE = "map_src_";
		public static final String MAPPING_DATE_IMPORTED = "map_d_imp_";
	}
	
	public static class InstitutionColumns {
		public static final String ID = "id_";
		public static final String NAME = "n_";
		public static final String EXTERNAL_NAME = "en_";
		public static final String CITY = "city_";
		public static final String EXTERNAL_CITY = "ecity_";
		public static final String ZIPCODE = "zc_";
		public static final String COUNTRY = "country_";
		public static final String STATE = "state_";
		public static final String TYPE = "type_";
		public static final String ID_PARENT = "id_p_";
		public static final String ID_PARENT_HIGHEST = "id_ph_";
		public static final String EF = "ef_";
		public static final String SOURCE = "src_";
		public static final String DATE_IMPORTED = "d_imp_";
		public static final String AUTHOR_INDEX = "a_indx_";
		public static final String INDEX_AFFILIATION = "indx_aff_";
		public static final String PROTO_AFFILIATION = "p_aff_";
		public static final String SOLR_SCORE = "s_score_";
		public static final String CRF_SEGMENTATION = "crf_s_";
		public static final String CONFIDENCE_LEVEL = "conf_lvl_";
		public static final String MAPPING_SOURCE = "map_src_";
		public static final String MAPPING_DATE_IMPORTED = "map_d_imp_";
	}
	
	public static class ConceptColumns {
		public static final String ID = "id_";
		public static final String ID_SOURCE = "id_src_";
		public static final String NAME = "n_";
		public static final String IS_ACTIVE = "is_a_";
		public static final String IS_GENERIC = "is_g_";
		public static final String SPECIFICITY_SCORE = "spec_score_";
		public static final String SOURCE = "src_";
		public static final String DATE_IMPORTED = "d_imp_";
		public static final String ID_RAW = "id_raw_";
		public static final String SOURCE_TYPE = "src_type_";
		public static final String MAPPING_SOURCE = "map_src_";
		public static final String ID_SECTION = "id_sec_";
		public static final String ID_SENTENCE = "id_sntnc_";
		public static final String COUNT = "cnt_";
		public static final String RAW_MENTION = "raw_men_";
		public static final String CONFIDENCE_SCORE = "conf_score_";
		public static final String CONFIDENCE_LEVEL = "conf_lvl_";
		public static final String MAPPING_DATE_IMPORTED = "map_d_imp_";
	}
}
