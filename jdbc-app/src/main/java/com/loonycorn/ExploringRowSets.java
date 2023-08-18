package com.loonycorn;

import javax.sql.rowset.JdbcRowSet; // interface
import java.sql.SQLException;

public class ExploringRowSets {

    public static void main(String[] args) {

        try (JdbcRowSet jdbcRS = DBUtils.getJdbcRowSet("DeliveryService")) {

            System.out.println("\n##############\n");
            System.out.println("Retrieving the details of one partner...\n");

            jdbcRS.setCommand("select first_name, last_name, hourly_rate, is_fulltime "
                    + "from delpartners where id = ?");

            jdbcRS.setInt(1, 102);

            jdbcRS.execute();

            jdbcRS.next();
            System.out.println(jdbcRS.getString("first_name")
                    + "\t" + jdbcRS.getString("last_name")
                    + "\t" + jdbcRS.getDouble("hourly_rate")
                    + "\t" + jdbcRS.getBoolean("is_fulltime"));
            //Eric	Jones	22.75	false


//            System.out.println("\n##############\n");
//            System.out.println("Retrieving all Delivery Partner details...\n");
//
//            jdbcRS.setCommand("select * from delpartners");
//
//            jdbcRS.execute();
//
//            while (jdbcRS.next()) {
//                System.out.println(jdbcRS.getString("first_name")
//                        + "\t" + jdbcRS.getString("last_name")
//                        + "\t" + jdbcRS.getDouble("hourly_rate")
//                        + "\t" + jdbcRS.getBoolean("is_fulltime"));
//                //Adam	Bell	18.5	true
//                //Eric	Jones	22.75	false
//                //Pam	Cruz	19.0	true
//            }

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
