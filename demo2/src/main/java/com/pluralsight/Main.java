package com.pluralsight;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.*;
import javax.sql.DataSource;

public class Main {

    private static sqlConnectionInfo sqlConnectionInfo;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password> <sqlUrl>");
            System.exit(1);
        }
        sqlConnectionInfo = getSqlConnectionInfoFromArgs(args);


        displayCities(103);
    }


    public static sqlConnectionInfo getSqlConnectionInfoFromArgs(String[] args) throws SQLException, ClassNotFoundException {

        String username = args[0];
        String password = args[1];
        String connectionString = "jdbc:mysql://localhost:3306/sakila";

        return new sqlConnectionInfo(connectionString, username, password);


    }


    public static void displayCities(int countryID) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;

        try {
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
// 1. open a connection to the database
// use the database URL to point to the correct database

            connection = DriverManager.getConnection(sqlConnectionInfo.getConnectionString(),
                    sqlConnectionInfo.getUsername(),
                    sqlConnectionInfo.getPassword());

            // create statement
// the statement is tied to the open connection
//        Statement statement = connection.createStatement();


            // define your query
            String query = "SELECT city FROM city " +
                    "WHERE country_id = ?";
// 2. Execute your query

            ps = connection.prepareStatement(query);
            ps.setInt(1, countryID);

            results = ps.executeQuery();


// process the results
            while (results.next()) {
                String city = results.getString("city");
                System.out.println(city);
            }
// 3. Close the connection
            connection.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("error");
        } finally {
            if (connection != null) {
                connection.close();
            }
            if(results != null) {
                results.close();
            }

            if(ps != null){
                ps.close();
            }

        }

    }
}






