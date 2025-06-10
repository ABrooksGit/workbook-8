package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    private static BasicDataSource basicDataSource;
    private static final Console console = new Console();


    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(
                    "Application needs three arguments to run: " +
                            "java com.pluralsight.UsingDriverManager <username> <password> <sqlUrl>");
            System.exit(1);
        }
        basicDataSource = getBasicDataSource(args);

        getActorsAndMovies();
    }

    public static void getActorsAndMovies(){
/*
"Select a.first_name, a.last_name, f.title from Actor a Join film_actor fa On a.actor_id = fa.actor_id Join film f on fa.film_id = f.film_Id Where a.last_name = ?"
 */
        try(Connection connection = basicDataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement("Select first_name, last_name from Actor Where last_name = ?")){
            String choice = console.promptForString("Enter in the last name of the actor: ");
            ps.setString(1, choice);
            try(ResultSet results = ps.executeQuery()){
                while (results.next()){
                    String lastName = results.getString("last_name");
                    String firstName = results.getString("first_name");
                    System.out.printf("%s %s\n", firstName, lastName);
                }
                System.out.println("Enter the first and last name of the actor to see the movies they star in");

            }

            String firstName = console.promptForString("Enter the actors first name: ");
            String lastName = console.promptForString("Enter the actors last name: ");
            PreparedStatement ps2nd = connection.prepareStatement("Select a.first_name, a.last_name, f.title from Actor a Join film_actor fa On a.actor_id = fa.actor_id Join film f on fa.film_id = f.film_Id Where a.first_name = ? And a.last_name = ?");
            ps2nd.setString(1, firstName);
            ps2nd.setString(2, lastName);

            try(ResultSet result = ps2nd.executeQuery()){
                while (result.next()){
                    firstName = result.getString("a.first_name");
                    lastName = result.getString("a.last_name");
                    String filmTitle = result.getString("f.title");
                    System.out.printf("%s %s %s\n", firstName, lastName, filmTitle);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }









    public static BasicDataSource getBasicDataSource(String[] args){

        String username = args[0];
        String password = args[1];
        BasicDataSource result = new BasicDataSource();
        result.setUrl("jdbc:mysql://localhost:3306/sakila");
        result.setUsername(username);
        result.setPassword(password);

        return result;
    }
}