package com.loonycorn;

import javax.sql.RowSet; // interface
import javax.sql.rowset.CachedRowSet; // interface CachedRowSet
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

        try (CachedRowSet cachedRS = DBUtils.getCachedRowSet("DeliveryService")) {

            cachedRS.setCommand("select * from delpartners");
            cachedRS.execute();

            System.out.println("Moving around in a CachedRowSet: \n");

            cachedRS.first();
            displayRow("first()", cachedRS);
            //first(): Adam | Bell | 18.50 | true

            cachedRS.relative(2);
            displayRow("relative(2)", cachedRS);
            //relative(2): Pam | Cruz | 21.00 | true

            cachedRS.previous();
            displayRow("previous()", cachedRS);
            //previous(): Eric | Jones | 23.00 | false

            cachedRS.absolute(4);
            displayRow("absolute(4)", cachedRS);
            //absolute(4): Gav | Comey | 19.00 | true

            cachedRS.last();
            displayRow("last()", cachedRS);
            //last(): Kylie | Kass | 25.00 | false
            // a new connection was established so that the update in the DB was read

            cachedRS.relative(-1);
            displayRow("relative(-1)", cachedRS);
            //relative(-1): Pablo | Hernandez | 19.50 | true
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (CachedRowSet cachedRS = DBUtils.getCachedRowSet("DeliveryService")) {
//
//            cachedRS.setCommand("select * from delpartners");
//            cachedRS.execute();
//
//            System.out.println("Moving around in a CachedRowSet: \n");
//
//            cachedRS.first();
//            displayRow("first()", cachedRS);
//            //first(): Adam | Bell | 18.50 | true
//
//            cachedRS.relative(2);
//            displayRow("relative(2)", cachedRS);
//            //relative(2): Pam | Cruz | 21.00 | true
//
//            cachedRS.previous();
//            displayRow("previous()", cachedRS);
//            //previous(): Eric | Jones | 23.00 | false
//
//            cachedRS.absolute(4);
//            displayRow("absolute(4)", cachedRS);
//            //absolute(4): Gav | Comey | 19.00 | true
//
//            System.out.println("\nSleeping for a minute...");
//            Thread.sleep(60000);
//
//            cachedRS.last();
//            cachedRS.refreshRow();
//            displayRow("last()", cachedRS);
//            //last(): Kylie | Kass | 22.00 | false
//            //cachedRS was not able to pick up the updated value in the DB because cachedRS is disconnected to the DB
//
//            cachedRS.relative(-1);
//            displayRow("relative(-1)", cachedRS);
//            //relative(-1): Pablo | Hernandez | 19.50 | true
//        }
//        catch (SQLException | InterruptedException ex) {
//            ex.printStackTrace();
//        }

    }
}