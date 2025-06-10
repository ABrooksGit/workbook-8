package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {

    Scanner scanner = new Scanner(System.in);


    public int promptForInt(String prompt) {
        boolean hasResult = false;
        int result = -1;
        while(!hasResult){
            try {

                System.out.print(prompt);
                result = scanner.nextInt();
                scanner.nextLine();
                hasResult = true;

            } catch (Exception e) {
                System.out.println(ColorCodes.RED +"Not a valid option, please try again"+ ColorCodes.RESET);
                scanner.next();


            }
        }

        return result;

    }

    public float promptForFloat(String prompt) {
        boolean hasResult = false;
        float result = -1;

        while (!hasResult) {
            try {
                System.out.print(prompt);
                result = scanner.nextFloat();
                return result;
            } catch (Exception e) {
                System.out.println(ColorCodes.RED + "Not a valid input, please enter a valid float." + ColorCodes.RESET);
                scanner.nextLine();
            }
        }
        return result;
    }


    public double promptForDouble(String prompt) {
        boolean hasResult = false;
        double result = -1;

        while (!hasResult) {
            try {
                System.out.print(prompt);
                result = scanner.nextDouble();
                scanner.nextLine();
                return result;

            } catch (Exception e) {
                System.out.println(ColorCodes.RED +"Not a valid input, please enter a valid double." + ColorCodes.RESET);
                scanner.nextLine();
            }
        }
        return result;
    }

    public String promptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();


    }


    public LocalDate promptForDate(String prompt) {
        LocalDate result = null;
        boolean hasResult = false;

        while (!hasResult) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (!input.isEmpty()) {
                    result = LocalDate.parse(input);
                }
                hasResult = true;
            } catch (DateTimeParseException e) {
                System.out.println(ColorCodes.RED +"Incorrect date format. Please use (YYYY) or leave blank" + ColorCodes.RESET);
            }
        }

        return result;
    }
}