package main.java.com.sciencescape.ds.db.util;

public class NoSQLConstants {
	
	public static class ZooKeeperConstants {
		public static final String ZK_PORT_DEFAULT = "2181";
	}
	
	public static class HBaseConfigConstants {
		public static final String ZK_QUOROM = "hbase.zookeeper.quorum";
		public static final String ZK_PORT = "hbase.zookeeper.property.clientPort";
		public static final String MASTER= "hbase.master";
		public static final String ROOT_DIR ="hbase.rootdir";
		public static final String DISTRIBUTED_MODE = "hbase.cluster.distributed"; 
	}
	
	public static class HBaseClusterConstants {
		public static final String HBASE_MASTER = "10.100.0.120:60000";
		public static final String HBASE_HDFS_DIR = "hdfs://10.100.0.120:8020/hbase";
		public static final String HBASE_DISTRIBUTED_MODE = "true";
	}
}