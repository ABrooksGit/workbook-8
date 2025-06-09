package com.pluralsight;
import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password>");
            System.exit(1);
        }

        String connectionString = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];

        // load the MySQL Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
// 1. open a connection to the database
// use the database URL to point to the correct database
        Connection connection;
        connection = DriverManager.getConnection(connectionString, username,password);

        // create statement
// the statement is tied to the open connection
        Statement statement = connection.createStatement();

        // define your query
        String query = "SELECT productID, productName,UnitPrice,UnitsInStock FROM products";
// 2. Execute your query
        ResultSet results = statement.executeQuery(query);

// process the results
        while (results.next()) {
            String products = results.getString("productID");
            String productName = results.getString("ProductName");
            double unitPrice = results.getDouble("UnitPrice");
            int unitsInStock = results.getInt("UnitsInStock");

//            System.out.println(products + " " + productName + " " +  unitPrice + " " +  unitsInStock);
            System.out.printf("Product ID: %s\nName: %s\nPrice: $%.2f\nStock: %s\n----------------\n", products, productName, unitPrice, unitsInStock);
        }
// 3. Close the connection
        connection.close();

    }
}