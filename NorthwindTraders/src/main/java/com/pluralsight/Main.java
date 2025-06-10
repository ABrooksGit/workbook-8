package com.pluralsight;
import java.sql.*;


public class Main {
    private static final Console console = new Console();
    private static sqlConnectionInfo sqlConnectionInfo;


    public static void main(String[] args)  {

        if (args.length != 3) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password> <sqlUrl>");
            System.exit(1);
        }
        sqlConnectionInfo = getSqlConnectionInfoFromArgs(args);

        displayHome();


    }


    public static sqlConnectionInfo getSqlConnectionInfoFromArgs(String[] args){

        String connectionString = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];
        String password = args[1];


        return new sqlConnectionInfo(connectionString,username,password);



    }






    public static void displayHome(){

            String homeScreen = """
                    What do you want to do?
                    1) Display all products
                    2) Display all customers
                    0) Exit
                    Select an option:\s""";

            int choice;
            do {
                choice = console.promptForInt(homeScreen);
                switch (choice) {
                    case 1:
                        try {
                            displayProducts();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        try {
                            displayCustomers();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        break;
                    default:
                        System.out.println("exiting...");
                        break;


                }

            } while (choice != 0);
    }



    private static void displayProducts() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;

        try {

            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
// 1. open a connection to the database
// use the database URL to point to the correct database

            connection = DriverManager.getConnection(sqlConnectionInfo.getConnectionString(),
                    sqlConnectionInfo.getUsername(),sqlConnectionInfo.getPassword());

            // create statement

// the statement is tied to the open connection


            // define your query
            String query = "SELECT productID, productName,UnitPrice,UnitsInStock FROM products";
             ps = connection.prepareStatement(query);
// 2. Execute your query
            results = ps.executeQuery();

// process the results
            while (results.next()) {
                int products = results.getInt("productID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");

//            System.out.println(products + " " + productName + " " +  unitPrice + " " +  unitsInStock);
                System.out.printf("Product ID: %s\nName: %s\nPrice: $%.2f\nStock: %s\n----------------\n", products, productName, unitPrice, unitsInStock);
//            System.out.printf("Product ID--------Name-------Price-------Stock\n%s, %s, $%.2f, %s", products, productName, unitPrice, unitsInStock);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            // 3. Close the connection

            if(results != null){
                results.close();
            }
            if (ps != null){
                ps.close();
            }
            if(connection != null) {
                connection.close();
            }


        }



    }

    private static void displayCustomers() throws SQLException{
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet results = null;


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(sqlConnectionInfo.getConnectionString(),
                    sqlConnectionInfo.getUsername(),sqlConnectionInfo.getPassword());


            String query = "SELECT CustomerID, ContactName  FROM customers";
            ps = connection.prepareStatement(query);
            results = ps.executeQuery();

            while(results.next()){
                String customerID = results.getString("CustomerID");
                String contactName = results.getString("ContactName");
                System.out.printf("CustomerID: %s  Contact Name: %s\n", customerID, contactName );


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {

            if(results != null) {
                results.close();
            }

            if(ps != null) {
                ps.close();
            }

            if(connection != null) {
                connection.close();
            }

        }


    }



}