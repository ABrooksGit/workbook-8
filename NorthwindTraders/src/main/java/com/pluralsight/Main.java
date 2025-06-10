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






    public static void displayHome() {

            String homeScreen = """
                    What do you want to do?
                    1) Display all products
                    2) Display all customers
                    3) Display all categories
                    0) Exit
                    Select an option:\s""";

            int choice;
            do {
                try {
                    choice = console.promptForInt(homeScreen);
                    switch (choice) {
                        case 1:
                            displayProducts();
                            break;
                        case 2:
                            displayCustomers();
                            break;
                        case 3:
                            try {
                                displayAllCategories();
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        default:
                            System.out.println("exiting...");
                            break;
                    }

                } catch (SQLException e){
                    throw new RuntimeException(e);
                }

            } while (choice != 0) ;
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


    private static void displayAllCategories() throws ClassNotFoundException {


        Class.forName("com.mysql.cj.jdbc.Driver");
        int choice;

        try (Connection connection = DriverManager.getConnection(sqlConnectionInfo.getConnectionString(), sqlConnectionInfo.getUsername(), sqlConnectionInfo.getPassword());
             PreparedStatement ps = connection.prepareStatement("SELECT CategoryID, CategoryName, Description From categories Order by categoryID asc ")) {
            try (ResultSet results = ps.executeQuery()) {
                while (results.next()) {
                    int categoryID = results.getInt("CategoryID");
                    String categoryName = results.getString("CategoryName");
                    String description = results.getString("Description");
                    System.out.printf("%s %s %s\n", categoryID, categoryName, description);
                }
                choice = console.promptForInt("Which CategoryID do you want to see? ");
            }




            PreparedStatement preparedStatement = connection.prepareStatement("Select productId, productName, unitPrice, UnitsInStock from Products Where categoryID = ?");
            preparedStatement.setInt(1, choice);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    int productID = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    double unitPrice = resultSet.getDouble("UnitPrice");
                    int UnitsInStock = resultSet.getInt("UnitsInStock");
                    System.out.printf("%s %s $%.2f %s\n", productID,productName,unitPrice,UnitsInStock);
                }



            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }





    }



}