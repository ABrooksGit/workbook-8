//package com.pluralsight;
//
//import java.sql.SQLException;
//
//public class Second {
//    public static void second(String [] args) throws SQLException, ClassNotFoundException {
//
//        // Query was: SELECT first_name, last_name FROM ...
//        ResultSet resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()) {
//// Get the 1st and 2nd fields returned from the query
//// based on the SELECT statement
//            System.out.printf("first_name = %s, last_name = %s;\n",
//                    resultSet.getString(1),
//                    resultSet.getString(2));
//
//
//            resultSet.close();
//            preparedStatement.close();
//            connection.close();
//
//
//
//
//    }
//
//
//
//
//
//}
