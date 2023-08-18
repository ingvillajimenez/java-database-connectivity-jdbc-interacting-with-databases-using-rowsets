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

            System.out.println("Moving around in a JdbcRowSet: \n");
            //Moving around in a JdbcRowSet:

            jdbcRS.first();
            displayRow("first()", jdbcRS);
            //first(): Adam | Bell | 18.50 | true

            jdbcRS.relative(2);
            displayRow("relative(2)", jdbcRS);
            //relative(2): Pam | Cruz | 21.00 | true

            jdbcRS.previous();
            displayRow("previous()", jdbcRS);
            //previous(): Eric | Jones | 22.75 | false

            jdbcRS.absolute(4);
            displayRow("absolute(4)", jdbcRS);
            //absolute(4): Gav | Comey | 19.00 | true

            System.out.println("\nSleeping for a minute...");
            Thread.sleep(60000);

            jdbcRS.last();
            jdbcRS.refreshRow();
            displayRow("last()", jdbcRS);
            //last(): Pablo | Hernandez | 19.50 | true
            //last(): Pablo | Hernandez | 20.00 | false

            jdbcRS.relative(-1);
            displayRow("relative(-1)", jdbcRS);
            //relative(-1): Marie | Woods | 19.00 | true

        }
        catch (SQLException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}