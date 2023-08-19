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
            //last(): Kylie | Kass | 22.00 | false

            cachedRS.relative(-1);
            displayRow("relative(-1)", cachedRS);
            //relative(-1): Pablo | Hernandez | 19.50 | true
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (CachedRowSet cachedRS = DBUtils.getCachedRowSet("DeliveryService")) {
//
//            System.out.println("\n####################\n");
//            System.out.println("Retrieving all Delivery Partner details...\n");
//
//            cachedRS.setCommand("select * from delpartners");
//
//            cachedRS.execute();
//
//            int rowNum = 1;
//
//            while (cachedRS.next()) {
//
//                displayRow("Row #" + rowNum, cachedRS);
//                //Row #1: Adam | Bell | 18.50 | true
//                //
//                //Row #2: Eric | Jones | 23.00 | false
//                //
//                //Row #3: Pam | Cruz | 21.00 | true
//                //
//                //Row #4: Gav | Comey | 19.00 | true
//                //
//                //Row #5: Stacey | Shields | 23.00 | false
//                //
//                //Row #6: Marie | Woods | 19.00 | true
//                //
//                //Row #7: Pablo | Hernandez | 19.50 | true
//                //
//                //Row #8: Kylie | Kass | 22.00 | false
//                rowNum++;
//            }
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}