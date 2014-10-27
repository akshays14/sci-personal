
Instructions to run the program:

1) Create HBase table if it does not exit
create 'denormalizedData', 'PMID', 'DOI', 'TITLE', 'DATE_PM_RELEASED', 'ISSN', 'DATE', 'ISO', 'ISSUE', 'LANGUAGE', 'TYPE', 'JOURNAL', 'EF', 'LOCATION', 'VOLUME', 'NLM', 'MD', 'VENUE', 'AUTHORS', 'INSTITUTION', 'ABSTRACT', 'SECTIONS', 'PRODUCTS', 'FIELDS', 'CITATIONS', 'IMPORTINFO'

2) execute the program after making sure if the parameters to main program in DataTransfer.java are reflecting what you want :
java -Xmx16g -cp ./target/UnnormalizeCoreDB-1.0-SNAPSHOT.jar:/home/akshay/Software/hbase-0.98.5-hadoop2/lib/*:./libs/mysql-connector-java-5.0.8-bin.jar main.java.com.sciencescape.ds.db.transfer.DataTransfer


