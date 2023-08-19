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

            int removedRows = 0;

            while (jdbcRS.next()) {

                if (jdbcRS.getBoolean("is_fulltime") == true
                        && jdbcRS.getDouble("hourly_rate") > 20) {

                    displayRow("Removing row: ", jdbcRS);
                    //Removing row: : Pam | Cruz | 21.00 | true
                    jdbcRS.deleteRow();

                    removedRows++;
                }
            }

            System.out.println("Number of deleted rows: " + removedRows);
            //Number of deleted rows: 1

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

//        try (JdbcRowSet jdbcRS = DBUtils.getJdbcRowSet("DeliveryService")) {
//
//            jdbcRS.setCommand("select * from delpartners where is_fulltime = false");
//            jdbcRS.execute();
//
//            jdbcRS.last();
//            int numPT = jdbcRS.getRow();
//            System.out.println("Number of part-time partners: " + numPT);
//            //Number of part-time partners: 2
//
//            if (numPT < 5) {
//
//                jdbcRS.moveToInsertRow();
//
//                jdbcRS.updateString("first_name", "Kylie");
//                jdbcRS.updateString("last_name", "Kass");
//                jdbcRS.updateDouble("hourly_rate", 22.0);
//                jdbcRS.updateBoolean("is_fulltime", false);
//
//                jdbcRS.insertRow();
//
//                jdbcRS.last();
//                displayRow("Added part-time partner: ", jdbcRS);
//                //Added part-time partner: : Kylie | Kass | 22.00 | false
//            }
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }

    }
}