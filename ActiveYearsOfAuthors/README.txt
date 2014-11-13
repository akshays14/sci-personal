Command to run:
java -cp ./target/HBaseMapReduce-0.0.1-SNAPSHOT.jar:/home/akshay/software/hbase-0.98.5-hadoop2/lib/* com.sciencescape.ds.HBaseMapReduce.GenerateRandomData

java -cp ./target/NumOfPaperByAuthor-0.0.1-SNAPSHOT.jar:/home/akshay/software/lib/*:/home/akshay/.m2/repository/net/sourceforge/argparse4j/argparse4j/0.4.4/argparse4j-0.4.4.jar:/home/akshay/.m2/repository/org/slf4j/slf4j-simple/1.7.7/slf4j-simple-1.7.7.jar com.sciencescape.ds.HBaseAggregate.GetAllPapers -i denormalizedData -o hbase -ot "author_summary"
