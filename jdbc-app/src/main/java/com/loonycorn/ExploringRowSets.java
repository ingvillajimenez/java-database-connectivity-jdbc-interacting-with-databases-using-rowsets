package com.loonycorn;

import javax.sql.rowset.JdbcRowSet; // interface JdbcRowSet
import java.sql.SQLException; // class SQLException

public class ExploringRowSets {

    public static void main(String[] args) {

        try (JdbcRowSet jdbcRS = DBUtils.getJdbcRowSet("DeliveryService")) {

            jdbcRS.setCommand("select * from delpartners");
            jdbcRS.execute();

            System.out.println("The properties of JdbcRowSet are: \n");

            System.out.println("Read-only? " + jdbcRS.isReadOnly()); //Read-only? true
            System.out.println("Auto-commit? " + jdbcRS.getAutoCommit()); //Auto-commit? true
            System.out.println("Fetch direction: " + jdbcRS.getFetchDirection()); //Fetch direction: 1000
            System.out.println("Code for FETCH_FORWARD? " + jdbcRS.FETCH_FORWARD); //Code for FETCH_FORWARD? 1000
            System.out.println("Command: " + jdbcRS.getCommand()); //Command: select * from delpartners

            System.out.println("\n-------------------");
            System.out.println("Metadata: \n" + jdbcRS.getMetaData());
            //Metadata:
            //com.mysql.cj.jdbc.result.ResultSetMetaData@292b08d6 - Field level information:
            //	com.mysql.cj.result.Field@22555ebf[dbName=deliveryservice,tableName=delpartners,originalTableName=delpartners,columnName=id,originalColumnName=id,mysqlType=3(FIELD_TYPE_INT),sqlType=4,flags= AUTO_INCREMENT PRIMARY_KEY, charsetIndex=63, charsetName=ISO-8859-1]
            //	com.mysql.cj.result.Field@36ebc363[dbName=deliveryservice,tableName=delpartners,originalTableName=delpartners,columnName=first_name,originalColumnName=first_name,mysqlType=253(FIELD_TYPE_VARCHAR),sqlType=12,flags=, charsetIndex=255, charsetName=UTF-8]
            //	com.mysql.cj.result.Field@45752059[dbName=deliveryservice,tableName=delpartners,originalTableName=delpartners,columnName=last_name,originalColumnName=last_name,mysqlType=253(FIELD_TYPE_VARCHAR),sqlType=12,flags=, charsetIndex=255, charsetName=UTF-8]
            //	com.mysql.cj.result.Field@34e9fd99[dbName=deliveryservice,tableName=delpartners,originalTableName=delpartners,columnName=hourly_rate,originalColumnName=hourly_rate,mysqlType=5(FIELD_TYPE_DOUBLE),sqlType=8,flags=, charsetIndex=63, charsetName=ISO-8859-1]
            //	com.mysql.cj.result.Field@3c41ed1d[dbName=deliveryservice,tableName=delpartners,originalTableName=delpartners,columnName=is_fulltime,originalColumnName=is_fulltime,mysqlType=1(FIELD_TYPE_BIT),sqlType=-7,flags=, charsetIndex=63, charsetName=ISO-8859-1]

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
