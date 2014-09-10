package test.java.com.sciencescape.ds.db.hbase;

import main.java.com.sciencescape.ds.db.hbase.HbaseHandler;

public class WriteTest {

	 
    //HBAse column types
    Class types[] = {Integer.class, String.class};
    //HBase columns
    String hbaseCols[] =  {"f1:app","f2:apps"};
    //MySQL columns
    //String mySQLCols[] =  {"PERS_ID","PERS_DESC"};

    //HBaseSource hb = new HBaseSource("sample",hbaseCols, types);
    HbaseHandler hh = new HbaseHandler("10.100.0.120", "t1", hbaseCols, types);
    //MySQLSource my = new MySQLSource("PERSON",mySQLCols);

    try {
        hh.connect();
        //my.connect();
        String value1[] = {"samplA", "sampleB"};
        DataRecord rec = my.readRecord();
        while(rec != null) {
            hb.writeRecord(rec);
            rec = my.readRecord();
        }



    } catch (IOException ex) {
        ex.printStackTrace();
        return;
    }
}
