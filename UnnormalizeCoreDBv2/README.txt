Instructions to run the program:
===============================

A) Create HBase table if it does not exit
i) get to the hbase shell
$> hbase shell
ii) make sure the table does not exist (in output of following command)
hbase(main)> list
iii) in case it exist, and you want to get rid of it
hbase(main)> disable 'denormalizedData_dev'
hbase(main)> drop 'denormalizedData_dev'
iv) create the hbase table
create 'denormalizedData_dev', 'PMID', 'DOI', 'TITLE', 'DATE_PM_RELEASED', 'ISSN', 'DATE', 'ISO', 'ISSUE', 'LANGUAGE', 'TYPE', 'JOURNAL', 'EF', 'LOCATION', 'VOLUME', 'NLM', 'MD', 'VENUE', 'AUTHORS', 'INSTITUTION', 'ABSTRACT', 'SECTIONS', 'PRODUCTS', 'FIELDS', 'CITATIONS', 'IMPORTINFO'

B) execute the program after making sure if the parameters to main program in DataTransfer.java are reflecting what you want:
i) Check if deployment variables are set correctly in following places:
/UnnormalizeCoreDB/src/main/java/com/sciencescape/ds/db/transfer/DataTransfer.java
/UnnormalizeCoreDB/src/main/java/com/sciencescape/ds/db/util/NoSQLConstants.java$HBaseConfigConstants
/UnnormalizeCoreDB/src/main/java/com/sciencescape/ds/db/util/CoreDBConstants.java$DBServers

ii) to run on your own dev machine (using Apache hbase library)
java -Xmx16g -cp ./target/UnnormalizeCoreDB-1.0-SNAPSHOT.jar:<PATH-TO-HBASE>/lib/*:<MAVEN_HOME>/repository/mysql/mysql-connector-java/5.1.18/mysql-connector-java-5.1.18.jar main.java.com.sciencescape.ds.db.transfer.DataTransfer <YEAR-OF-PUBLICATION>
For example:
java -Xmx16g -cp ./target/UnnormalizeCoreDB-1.0-SNAPSHOT.jar:/home/akshay/software/hbase-0.98.5-hadoop2/lib/*:/home/akshay/.m2/repository/mysql/mysql-connector-java/5.1.18/mysql-connector-java-5.1.18.jar main.java.com.sciencescape.ds.db.transfer.DataTransfer

b) On dev machines in machine room (Using CDH libraries):
for i in {<START-YEAR>..<END-YEAR>}; do java -Xmx48g -cp ./target/UnnormalizeCoreDB-1.0-SNAPSHOT.jar:/opt/cloudera/parcels/CDH-5.2.0-1.cdh5.2.0.p0.36/lib/hbase/*:/opt/cloudera/parcels/CDH-5.2.0-1.cdh5.2.0.p0.36/lib/hbase/lib/*:/opt/cloudera/parcels/CDH-5.2.0-1.cdh5.2.0.p0.36/lib/hadoop/*:/home/akshay/.m2/repository/mysql/mysql-connector-java/5.1.18/mysql-connector-java-5.1.18.jar main.java.com.sciencescape.ds.db.transfer.DataTransfer $i; done
For example:
for i in {2013..2013}; do java -Xmx48g -cp ./target/UnnormalizeCoreDB-1.0-SNAPSHOT.jar:/opt/cloudera/parcels/CDH-5.2.0-1.cdh5.2.0.p0.36/lib/hbase/*:/opt/cloudera/parcels/CDH-5.2.0-1.cdh5.2.0.p0.36/lib/hbase/lib/*:/opt/cloudera/parcels/CDH-5.2.0-1.cdh5.2.0.p0.36/lib/hadoop/*:/home/akshay/.m2/repository/mysql/mysql-connector-java/5.1.18/mysql-connector-java-5.1.18.jar main.java.com.sciencescape.ds.db.transfer.DataTransfer $i; done


For version 2
+++++++++++++

1) Create table
create 'paper_v2_dev', 'ID', 'P', 'D', 'CI', 'EF', 'V', 'A', 'I', 'CO'

2) Running from command line:
mvn exec:java -Dexec.mainClass=com.sciencescape.ds.data.transfer.DataTransfer -Dexec.args="--help"

Example:

for i in {2012..2014}; do for j in {-1..12}; do mvn exec:java -Dexec.mainClass=com.sciencescape.ds.data.transfer.DataTransfer -Dexec.args="-t paper_v2_prod -y 2013 -m $j"; sleep 20; done; done | tee -a ~/logs/denormalization.log


For version 4
+++++++++++++

1) Create pre-splitted table (with proper version, compression and datablock encoding)

From SRCDIR (file having pom.xml)

partition_range=`python scripts/hbase_partitions.py <NUMBER_OF_PARTITIONS>`
echo "create 'paper_v4_dev', {NAME => 'ID', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'D', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'CI', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'EF', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'V', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'A', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'I', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {NAME => 'CO', VERSIONS => 1, COMPRESSION => 'SNAPPY', DATA_BLOCK_ENCODING => 'FAST_DIFF'}, {SPLITS => $partition_range}" | hbase shell

Note : We have removed 'P' (or Paper) Column Family.

2) Running from command line:
mvn exec:java -Dexec.mainClass=com.sciencescape.ds.data.transfer.DataTransfer -Dexec.args="--help"

Example:

for i in {2012..2014}; do for j in {-1..12}; do mvn exec:java -Dexec.mainClass=com.sciencescape.ds.data.transfer.DataTransfer -Dexec.args="-t paper_v4_dev -y $i -m $j"; sleep 20; done; done | tee -a ~/logs/denormalization.log
