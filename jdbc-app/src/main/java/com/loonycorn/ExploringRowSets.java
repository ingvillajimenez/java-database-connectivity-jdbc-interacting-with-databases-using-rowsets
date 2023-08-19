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

            int removedRows = 0;

            while (cachedRS.next()) {

                if (cachedRS.getBoolean("is_fulltime") == false
                        && cachedRS.getDouble("hourly_rate") > 20) {

                    displayRow("Removing row: ", cachedRS);
                    //Removing row: : Eric | Jones | 26.00 | false
                    //
                    //Removing row: : Stacey | Shields | 26.00 | false
                    //
                    //Removing row: : Kylie | Kass | 26.00 | false
                    //
                    //Removing row: : Brian | Walters | 25.00 | false
                    cachedRS.deleteRow();

                    removedRows++;
                }
            }

            System.out.println("\nNumber of deleted rows: " + removedRows);
            //Number of deleted rows: 4

            cachedRS.acceptChanges(conn);

            cachedRS.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            conn.setAutoCommit(false);
//
//            CachedRowSet cachedRS = DBUtils.getCachedRowSet("");
//
//            cachedRS.setCommand("select * from delpartners where is_fulltime = false");
//            cachedRS.execute(conn);
//
//            cachedRS.last();
//            int numPT = cachedRS.getRow();
//            System.out.println("Number of part-time partners: " + numPT);
//            //Number of part-time partners: 3
//
//            if (numPT < 5) {
//
//                cachedRS.moveToInsertRow();
//
//                cachedRS.updateNull("id");
//                cachedRS.updateString("first_name", "Brian");
//                cachedRS.updateString("last_name", "Walters");
//                cachedRS.updateDouble("hourly_rate", 25.0);
//                cachedRS.updateBoolean("is_fulltime", false);
//
//                cachedRS.insertRow();
//                cachedRS.moveToCurrentRow();
//
//                cachedRS.last();
//                displayRow("Added part-time partner: ", cachedRS);
//                //Added part-time partner: : Brian | Walters | 25.00 | false
//                // Inserting a new row was applied with an autoincrement for the id in the DB
//            }
//
//            cachedRS.acceptChanges(conn);
//
//            cachedRS.close();
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

//        try (Connection conn = DBUtils.getMysqlConnection("DeliveryService")) {
//
//            conn.setAutoCommit(false);
//
//            CachedRowSet cachedRS = DBUtils.getCachedRowSet("");
//
//            cachedRS.setCommand("select * from delpartners where is_fulltime = false");
//            cachedRS.execute(conn);
//
//            cachedRS.last();
//            int numPT = cachedRS.getRow();
//            System.out.println("Number of part-time partners: " + numPT);
//            //Number of part-time partners: 3
//
//            if (numPT < 5) {
//
//                cachedRS.moveToInsertRow();
//
//                cachedRS.updateString("first_name", "Brian");
//                cachedRS.updateString("last_name", "Walters");
//                cachedRS.updateDouble("hourly_rate", 25.0);
//                cachedRS.updateBoolean("is_fulltime", false);
//
//                cachedRS.insertRow();
//                //java.sql.SQLException: Failed on insert row
//                //	at java.sql.rowset/com.sun.rowset.CachedRowSetImpl.insertRow(CachedRowSetImpl.java:5464)
//                //	at com.loonycorn.ExploringRowSets.main(ExploringRowSets.java:45)
//                cachedRS.moveToCurrentRow();
//                  // Exception because there is no auto increment for the new row
//                  // the update at the BD was not successful either
//
//                cachedRS.last();
//                displayRow("Added part-time partner: ", cachedRS);
//            }
//
//            cachedRS.acceptChanges(conn);
//
//            cachedRS.close();
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}