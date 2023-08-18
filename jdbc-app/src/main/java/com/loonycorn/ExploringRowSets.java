package com.loonycorn;

import javax.sql.RowSet; // interface
import javax.sql.rowset.JdbcRowSet; // interface JdbcRowSet
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

        try (JdbcRowSet jdbcRS = DBUtils.getJdbcRowSet("DeliveryService")) {

            jdbcRS.setCommand("select * from delpartners");
            jdbcRS.execute();

            int updatedRows = 0;

            jdbcRS.setAutoCommit(false);

            while(jdbcRS.next()) {

                if (!jdbcRS.getBoolean("is_fulltime")) {

                    jdbcRS.updateDouble("hourly_rate", 23.0);
                    jdbcRS.updateRow();

                    displayRow("Updated record: ", jdbcRS);
                    //Updated record: : Eric | Jones | 23.00 | false
                    //Updated record: : Stacey | Shields | 23.00 | false
                    // This time it has been updated to the database
                    updatedRows++;
                }
            }

            jdbcRS.commit();

            System.out.println("\nNumber of updated rows: " + updatedRows);
            //Number of updated rows: 2

//            jdbcRS.setCommand("select * from delpartners");
//            jdbcRS.execute();
//
//            int updatedRows = 0;
//
//            jdbcRS.setAutoCommit(false);
//
//            while(jdbcRS.next()) {
//
//                if (!jdbcRS.getBoolean("is_fulltime")) {
//
//                    jdbcRS.updateDouble("hourly_rate", 23.0);
//                    jdbcRS.updateRow();
//
//                    displayRow("Updated record: ", jdbcRS);
//                    //Updated record: : Eric | Jones | 23.00 | false
//                    //Updated record: : Stacey | Shields | 23.00 | false
//                    // The update has not taken place in the database, only in the JdbcRS object
//                    updatedRows++;
//                }
//            }
//
//            System.out.println("\nNumber of updated rows: " + updatedRows);
//            //Number of updated rows: 2

//            jdbcRS.setCommand("select * from delpartners");
//            jdbcRS.execute();
//
//            int updatedRows = 0;
//
//            while(jdbcRS.next()) {
//
//                if (!jdbcRS.getBoolean("is_fulltime")) {
//
//                    jdbcRS.updateDouble("hourly_rate", 21.0);
//                    jdbcRS.updateRow();
//
//                    displayRow("Updated record: ", jdbcRS);
//                    //Updated record: : Eric | Jones | 21.00 | false
//                    //Updated record: : Stacey | Shields | 21.00 | false
//                    updatedRows++;
//                }
//            }
//
//            System.out.println("\nNumber of updated rows: " + updatedRows);
//            //Number of updated rows: 2

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}