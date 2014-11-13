package com.sciencescape.ds.HBaseAggregate;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Class to play around with filters and scan objects in HBase API.
 * @author Akshay
 */
public final class CheckDifferentColumnFilters {

	/**
	 * Private constructor to avoid instantiation.
	 */
	private CheckDifferentColumnFilters() {

	}

	/**
	 * Main function to populate HBase with random user logs.
	 *
	 * @param args command line arguments
	 * @throws Exception any exception
	 */
	public static void main(final String[] args) throws Exception {

		Configuration hbaseConfig = HBaseConfiguration.create();
		HTable htable = new HTable(hbaseConfig, "denormalizedData");
		Scan scan = new Scan();
		scan.setStartRow(Bytes.toBytes("1"));
		scan.setStopRow(Bytes.toBytes("10000"));
		scan.addFamily(Bytes.toBytes("AUTHORS"));
		/*Filter filter = new ColumnPrefixFilter(Bytes.toBytes("412"));
		Filter filter = new ColumnRangeFilter(Bytes.toBytes("412"), true,
				Bytes.toBytes("7"), true);
		scan.setFilter(filter);*/

		ResultScanner rs = htable.getScanner(scan);
		for (Result r = rs.next(); r != null; r = rs.next()) {
			// r will now have all HBase columns that start with "412",
			// which would represent a single row
			System.out.println("Key: " + Bytes.toString(r.getRow()));
			for (KeyValue kv : r.raw()) {
				// each kv represent - the latest version of - a column
				System.out.println("====================");
				//System.out.println("Key: " + Bytes.toString(kv.getKey()));
				System.out.println("Family: " + Bytes.toString(kv.getFamily()));
				System.out.println("Qualifier: "
						+ Bytes.toString(kv.getQualifier()));
				System.out.println("Value: " + Bytes.toString(kv.getValue()));
				System.out.println("====================");
				System.out.println();
			}
			System.out.println("done");
		}
		// close the table
		htable.close();
	}
}
