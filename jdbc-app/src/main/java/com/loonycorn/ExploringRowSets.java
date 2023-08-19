package com.loonycorn;

import javax.sql.RowSet; // interface RowSet
import javax.sql.rowset.CachedRowSet; // interface CachedRowSet
import java.sql.Connection; // interface Connection
import java.sql.SQLException; // class SQLException

public class ExploringRowSets {

    public static void displayRow(String label, RowSet rowSet) throws SQLException {

        String fName = rowSet.getString("first_name");
        String lName = rowSet.getString("last_name");
        double hourlyRate = rowSet.getDouble("hourly_rate");
        boolean isFT = rowSet.getBoolean("is_fulltime");

        String stdData = "\n%s: %s | %s | %.2f | %s \n";
        System.out.format(stdData, label, fName, lName, hourlyRate, isFT);
    }

    public static void main(String[] args) {

        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {

            conn.setAutoCommit(false);

            CachedRowSet cachedRS = DBUtils.getCachedRowSet("");

            cachedRS.setCommand("select * from delpartners");
            cachedRS.execute(conn);

            int updatedRows = 0;

            while (cachedRS.next()) {

                if (!cachedRS.getBoolean("is_fulltime")) {

                    cachedRS.updateDouble("hourly_rate", 26.0);
                    cachedRS.updateRow();

                    displayRow("Updated record: ", cachedRS);
                    //Updated record: : Eric | Jones | 26.00 | false
                    //
                    //Updated record: : Stacey | Shields | 26.00 | false
                    //
                    //Updated record: : Kylie | Kass | 26.00 | false
                    updatedRows++;
                }
            }

            cachedRS.acceptChanges(conn);

            cachedRS.close();

            System.out.println("\nNumber of updated rows: " + updatedRows);
            //Number of updated rows: 3
            // the update has taken effect in the DB

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

/*        try (CachedRowSet cachedRS = DBUtils.getCachedRowSet("DeliveryService")) {

            cachedRS.setCommand("select * from delpartners");
            cachedRS.execute();

//            cachedRS.setAutoCommit(true); // Cannot resolve method 'setAutoCommit' in 'CachedRowSet'

            int updatedRows = 0;

            while (cachedRS.next()) {

                if (!cachedRS.getBoolean("is_fulltime")) {

                    cachedRS.updateDouble("hourly_rate", 25.0);
                    cachedRS.updateRow();

                    displayRow("Updated record: ", cachedRS);
                    //Updated record: : Eric | Jones | 25.00 | false
                    //
                    //Updated record: : Stacey | Shields | 25.00 | false
                    //
                    //Updated record: : Kylie | Kass | 25.00 | false
                    //In spite of the exception, the update did in fact get pushed through to the DB
                    updatedRows++;
                }
            }

            cachedRS.acceptChanges();
            //java.sql.SQLException: Can't call commit when autocommit=true
            //	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:67)
            //	at com.mysql.cj.jdbc.ConnectionImpl.commit(ConnectionImpl.java:782)
            //	at java.sql.rowset/com.sun.rowset.internal.CachedRowSetWriter.commit(CachedRowSetWriter.java:1408)
            //	at java.sql.rowset/com.sun.rowset.CachedRowSetImpl.acceptChanges(CachedRowSetImpl.java:904)
            //	at com.loonycorn.ExploringRowSets.main(ExploringRowSets.java:48)
            //javax.sql.rowset.spi.SyncProviderException: Can't call commit when autocommit=true
            //	at java.sql.rowset/com.sun.rowset.CachedRowSetImpl.acceptChanges(CachedRowSetImpl.java:923)
            //	at com.loonycorn.ExploringRowSets.main(ExploringRowSets.java:48)

            System.out.println("\nNumber of updated rows: " + updatedRows);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }*/

//        try (CachedRowSet cachedRS = DBUtils.getCachedRowSet("DeliveryService")) {
//
//            cachedRS.setCommand("select * from delpartners");
//            cachedRS.execute();
//
//            int updatedRows = 0;
//
//            while (cachedRS.next()) {
//
//                if (!cachedRS.getBoolean("is_fulltime")) {
//
//                    cachedRS.updateDouble("hourly_rate", 25.0);
//                    cachedRS.updateRow();
//
//                    displayRow("Updated record: ", cachedRS);
//                    //Updated record: : Eric | Jones | 25.00 | false
//                    //
//                    //Updated record: : Stacey | Shields | 25.00 | false
//                    //
//                    //Updated record: : Kylie | Kass | 25.00 | false
//                    updatedRows++;
//                }
//            }
//
//            System.out.println("\nNumber of updated rows: " + updatedRows);
//            //Number of updated rows: 3
//            //The update has not taken place at the database end because there was not a commit.
//            // It took place at the cachedRS
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}