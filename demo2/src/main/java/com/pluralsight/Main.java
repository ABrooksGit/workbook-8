package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;



public class Main {

    private static BasicDataSource basicDataSource;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password> <sqlUrl>");
            System.exit(1);
        }
        basicDataSource = getBasicDataSource(args);
/*
       Scanner scanner = new Scanner(System.in);
        int countryID = scanner.nextInt();
        scanner.nextLine();
        displayCities(countryID);
 */

        displayCities(103);
    }


    public static BasicDataSource getBasicDataSource(String[] args) {



        String username = args[0];
        String password = args[1];
        BasicDataSource result = new BasicDataSource();
        result.setUrl("jdbc:mysql://localhost:3306/sakila");
        result.setUsername(username);
        result.setPassword(password);

        return result;


    }


    public static void displayCities(int countryID){


//        // load the MySQL Driver
//        Class.forName("com.mysql.cj.jdbc.Driver");

        // 1. open a connection to the database
        // use the database URL to point to the correct database
        // define your query
        try (Connection connection = basicDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT city FROM city WHERE country_id = ?"))
        {
            ps.setInt(1, countryID);
            // create statement
            // the statement is tied to the open connection
            try (ResultSet results = ps.executeQuery()){
                while (results.next()) {
                String city = results.getString("city");
                System.out.println(city);
                }
            }

            } catch (Exception e) {
                throw new RuntimeException(e);
         }

    }

//    public static void displayALlCities(){
//        // load the MySQL Driver
////        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        // 1. open a connection to the database
//        // use the database URL to point to the correct database
//        try (Connection connection = DriverManager.getConnection(basicDataSource.getConnectionString(), basicDataSource.getUsername(), basicDataSource.getPassword());
//             PreparedStatement ps = connection.prepareStatement("SELECT city FROM city WHERE country_id = 103");
//             ResultSet results = ps.executeQuery();
//        ) {
//
//            while (results.next()) {
//                String city = results.getString("city");
//                System.out.println(city);
//            }
//
//        }catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//    }


}





//
//            connection =


//        Statement statement = connection.createStatement();






//            ps = connection.prepareStatement(query);
//            ps.setInt(1, countryID);
//
//            results = ps.executeQuery();


// process the results

// 3. Close the connection
//            connection.close();
//            ps.close();
//        } catch (Exception e) {
//            System.out.println("error");
//        } finally {
//            if(results != null) {
//                results.close();
//            }
//
//            if(ps != null){
//                ps.close();
//            }
//
//            if (connection != null) {
//                connection.close();
//            }
//
//        }









