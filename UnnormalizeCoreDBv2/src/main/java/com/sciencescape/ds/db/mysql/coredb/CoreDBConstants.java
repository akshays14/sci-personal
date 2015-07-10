package com.sciencescape.ds.db.mysql.coredb;

/**
 * @class CoreDBConstants CoreDBConstants.java
 * @author Akshay
 *
 * Class to store all the constants related to
 * Core-DB at ScienceScape.
 */
public class CoreDBConstants {

	/**
	 * @brief enlist table names in core-db
	 * @author Akshay
	 *
	 * Class having table names in core-db.
	 */
	public static class Tables {
		public static final String PAPER = "paper";
		public static final String PAPER_TO_VENUE = "paper_to_venue";
		public static final String VENUE = "venue";
		public static final String PAPER_TO_AUTHOR = "paper_to_author";
		public static final String AUTHOR = "author";
		public static final String PAPER_TO_INSTITUTION = "paper_to_institution";
		public static final String INSTITUTION = "institution";
		public static final String PAPER_TO_CONCEPT = "paper_to_concept";
		public static final String CONCEPT = "concept";
		public static final String CITATION = "citation";
	}

	/**
	 * @brief enlist fields of paper table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of paper table in core-db.
	 */
	public static class PaperFields {
		public static final String ID = "id";
		public static final String PMID = "pmid";
		public static final String PMC_ID = "id_pmc";
		public static final String DOI = "doi";
		public static final String TITLE = "title";
		public static final String YEAR = "year";
		public static final String MONTH = "month";
		public static final String DAY = "day";
		public static final String ISSN = "issn";
		public static final String ISO = "iso";
		public static final String VOLUME = "volume";
		public static final String ISSUE = "issue";
		public static final String TYPE = "type";
		public static final String ABSTRACT = "abstract";
		public static final String ABSTRACT_OTHER = "abstract_other";
		public static final String AUTHORS_RAW = "authors_raw";
		public static final String JOURNAL_NAME = "journal_name_raw";
		public static final String NLM_ID = "nlm_id";
		public static final String MESH_TERMS_RAW = "mesh_terms_raw";
		public static final String KEYWORDS = "keywords";
		public static final String CONCEPTS_RAW = "concepts_raw";
		public static final String NUM_OF_REFS = "number_of_references";
		public static final String GRANTS = "grants";
		public static final String COUNTRY = "country";
		public static final String LANGUAGE = "language";
		public static final String METADATA_SOURCE = "source";
		public static final String EIGENFACTOR = "eigenfactor";
		public static final String PM_DATE_RELEASED = "date_pubmed_release";
		public static final String DATE_IMPORTED = "date_imported";
		// not in paper table
		public static final String FIELDS = "fields";
	}
	
	/**
	 * @brief enlist fields of paper_to_venue table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in venue table in core-db.
	 */
	public static class PaperToVenueFields {
		public static final String VENUE_ID = "id_venue";
		public static final String PAPER_ID = "id_paper";
		public static final String SOURCE = "paper_to_venue.source";
		public static final String CONFIDENCE_SCORE = "confidence_score";
		public static final String CONFIDENCE_LEVEL = "confidence_level";
		public static final String DATE_IMPORTED = "paper_to_venue.date_imported";
	}


	/**
	 * @brief enlist fields of venue table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in venue table in core-db.
	 */
	public static class VenueFields {
		public static final String VENUE_ID = "id";
		public static final String ISO_ABBREVIATION = "iso_abbreviation";
		public static final String TYPE = "type";
		public static final String TITLE = "title";
		public static final String TITLE_WITHOUT_DIACRITICS = "title_without_diacritics";
		//ignoring image for now
		public static final String YEAR_START = "year_start";
		public static final String YEAR_END = "year_end";
		public static final String FREQUENCY = "frequency";
		public static final String LANGUAGE = "language";
		public static final String PUBLISHER = "publisher";
		public static final String SOURCE = "source";
		public static final String ISO_WITHOUT_DIACRITICS = "iso_without_diacritics";
		public static final String EF_MEDIAN_RAW = "eigenfactor_median_raw";
		public static final String EF_MEDIAN_NORMALIZED = "eigenfactor_median_normalized";
		public static final String DATE_IMPORTED = "date_imported";
	}

	/**
	 * @brief enlist fields of paper_to_author table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in paper_to_author table in core-db.
	 */
	public static class PaperToAuthorFields {
		public static final String PAPER_ID = "id_paper";
		public static final String AUTHOR_ID = "id_author";
		public static final String INDEX = "author_index";
		public static final String RAW_NAME = "author_name";
		public static final String RAW_AFFILIATION = "proto";
		public static final String DISAMBIGUATION_SCORE = "dis_score";
		public static final String MAPPING_SOURCE = "paper_to_author.source";
		public static final String MAPPING_DATE_IMPORTED = "paper_to_author.date_imported";

	}

	/**
	 * @brief enlist fields of author table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of author in venue table in core-db.
	 */
	public static class AuthorFields {
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String HOMEPAGE = "homepage";
		public static final String AUTHOR_SOURCE = "author.source";
		public static final String AUTHOR_DATE_IMPORTED = "author.date_imported";
		public static final String ORCID_ID = "id_orcid";
	}

	/**
	 * @brief enlist fields of paper_to_institution table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in paper_to_institution table in core-db.
	 */
	public static class PaperToInstitutionFields {
		public static final String PAPER_ID = "id_paper";
		public static final String AUTHOR_INDEX = "author_index";
		public static final String INDEX_AFFILIATION = "index_affiliation";
		public static final String INSITUTION_ID = "id_institution";
		public static final String AFFILIATION_PROTO = "proto_affiliation";
		public static final String SOLR_SCORE = "score_solr_match";
		public static final String CRF_SEGMENTATION = "crf_segmentation";
		public static final String CONFIDENCE_LEVEL = "confidence_level";
		public static final String MAPPING_SOURCE = "paper_to_institution.source";
		public static final String MAPPING_DATE_IMPORTED = "paper_to_institution.date_imported";
		/**
		 * TODO: remove it if not used later.
		 */
		public static final String RAW_AFFILIATION_DELIMITER = ":";
	}

	/**
	 * @brief enlist fields of institution table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of author in institution table in core-db.
	 */
	public static class InstitutionFields {
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String EXTERNAL_NAME = "external_name";
		public static final String CITY = "city";
		public static final String EXTERNAL_CITY = "external_city";
		public static final String ZIPCODE = "zipcode";
		public static final String COUNTRY = "country";
		public static final String STATE = "state";
		public static final String TYPE = "type";
		public static final String ID_PARENT = "id_parent";
		public static final String ID_PARENT_HIGHEST = "id_parent_highest";
		public static final String EF = "eigenfactor";
		public static final String INSTITUION_SOURCE = "institution.source";
		public static final String INSTITUION_DATE_IMPORTED = "institution.date_imported";
	}

	/**
	 * @brief enlist fields of paper_to_concept table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in paper_to_field table in core-db.
	 */
	public static class PaperToConceptFields {
		
		public static final String ID_CONCEPT_RAW = "id_concept_raw";
		public static final String CONCEPT_SOURCE_TYPE = "concept_source_type";
		public static final String MAPPING_SOURCE = "paper_to_concept.source";
		public static final String ID_SECTION = "id_section";
		public static final String ID_SENTENCE = "id_sentence";
		public static final String COUNT = "count";
		public static final String RAW_MENTION = "raw_mention";
		public static final String CONFIDENCE_SCORE = "confidence_score";
		public static final String CONFIDENCE_LEVEL = "confidence_level";
		public static final String MAPPING_DATE_IMPORTED = "paper_to_concept.date_imported";
	}
	
	/**
	 * @brief enlist fields of concept table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in concept table in core-db.
	 */
	public static class ConceptFields {
		public static final String ID = "id";
		public static final String ID_SOURCE = "id_source";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String IS_ACTIVE = "is_active";
		public static final String IS_GENERIC = "is_generic";
		public static final String SPECIFICITY_SCORE = "specificity_score";
		public static final String CONCEPT_SOURCE = "concept.source";
		public static final String CONCEPT_DATE_IMPORTED = "concept.date_imported";
	}

	/**
	 * @brief enlist fields of citation table in core-db
	 * @author Akshay
	 *
	 * Class enlisting fields of interest in citation table in core-db.
	 */
	public static class CitationFields {
		public static final String ID = "id";
		public static final String DOI_FROM_PAPER = "doi_from_paper";
		public static final String DOI_TO_PAPER = "doi_to_paper";
		public static final String ID_FROM_PAPER = "id_from_paper";
		public static final String ID_TO_PAPER = "id_to_paper";
		public static final String YEAR = "year";
		public static final String MONTH = "month";
		public static final String CONFIDENCE_SCORE = "confidence";
		public static final String CONFIDENCE_LEVEL = "confidence_level";
		public static final String SOURCE = "source";
		public static final String DATE_IMPORTED = "date_imported";
	}

	/**
	 * @brief enlist all the user messages for CoreDB
	 * @author akshay
	 *
	 * Class enlisting all the user messages while
	 * interacting with Core DB.
	 */
	public static class Messages {
		public static final String RESULTSET_NULL_MSG = "Provided result set is null";
		public static final String MSG_SEPERATOR = " : ";
		public static final String MSG_STRING_FIELD_FORMAT = "%s : %s%n";
		public static final String MSG_INT_FIELD_FORMAT = "%s : %d%n";
		public static final String MSG_FLOAT_FIELD_FORMAT = "%s : %f%n";
		public static final String MSG_AUTHOR_ID_FORMAT = "Author-ID : %d%n";
		public static final String MSG_AUTHOR_NAME_FORMAT = "\t%s%n";
		public static final String MSG_INSTITUTION_ID_FORMAT = "Institution-ID : %d%n";
		public static final String MSG_INSTITUTION_RAW_AFFILIATION_FORMAT = "\t%s%n";
		public static final String MSG_INSTITUTION_NORM_AFFILIATION_FORMAT = "\t%s%n";
		public static final String MSG_FIELD_ID_FORMAT = "Field-ID : %d%n";
		public static final String MSG_FIELD_NAME_FORMAT = "\t%s%n";
	}

	/**
	 * @brief enlist all deployment specific values
	 * @author akshay
	 *
	 * Class enlisting all deployment specific values for
	 * our development DB.
	 */
	public static class DBServer {
		public static final String DEV_DB_SLAVE_SERVER = "dev2";
		public static final int DB_PORT = 3306;
		public static final String DEV_DB_USER = "ds_agent";
		public static final String DEV_DB_PASSWORD = "86753098675309";
		public static final String CORE_DB = "core_db";
	}
}
