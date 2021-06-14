package com.sabu.utils;

import com.sabu.entities.Action;
import com.sabu.entities.pieces.Ninja;
import com.sabu.validator.Validator;

import java.awt.*;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

import static com.sabu.utils.Constants.*;

public class Input {
    private static final Properties properties = new Properties();
    private static Scanner scanner = new Scanner(System.in);

    public static Point getBoardLocation(String message){
        Point point = null;
        while(point == null){
            try{
                Printer.print(message);
                String response = scanner.nextLine();
                response = response.toUpperCase(Locale.ROOT);
                Validator.isTrue(response.matches("^[A-E][1-5]$"), "Invalid input");
                int x = (int) response.charAt(0) - 65;
                int y = (int) response.charAt(1) - 49;
                point = new Point(x,y);
            }catch (Exception e){
                Printer.print(e.getMessage());
            }
        }
        return point;
    }

    public static String getName() {
        System.out.print("Insert your name: ");
        String response = scanner.nextLine();

        while (response.trim().isEmpty()){
            Printer.print("Your name cannot be empty!, Try again");
            response = scanner.nextLine();
        }
        return response;
    }

    public static String getConnectionMode(String message){
        Printer.print(message);
        String response = scanner.nextLine();
        response = response.toUpperCase(Locale.ROOT);
        while (!response.matches("^[YN]$")){
            System.out.println("Invalid input,Only accepts Y/N");
            response = scanner.nextLine();
            response = response.toUpperCase(Locale.ROOT);
        }
        return response;
    }



    public static Action getAction(Ninja ninja, char actionType){
        Action attack = null;
        String action = "move";
        if (actionType == ATTACK){
            action = "attack";
        }

        while(attack == null) {
            try {
                Printer.print("Enter location to " + action);
                String response = scanner.nextLine();
                response = response.toUpperCase(Locale.ROOT);
                Validator.isTrue(response.matches("^[A-E][1-5]$"), "Invalid input");

                int x = Translate.translateIntToChar(response.charAt(0),65);
                int y = Translate.translateIntToChar(response.charAt(1),48) - 1;
                attack = new Action(x,y,ninja,actionType);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return attack;
    }



    public static Ninja getNinja(boolean isBoss){
        String message = "Enter ninja location";
        if(isBoss){
            message = "Enter Ninja Boss location";
        }
        Point point = getBoardLocation(message);
        Ninja ninja = new Ninja(isBoss, point.x, point.y);
        return ninja;
    }


    public static String getIp(){
        String response = "";
        while (!response.matches(IP_REGEX)){
            System.out.print("Please enter valid ip: ");
            response = scanner.nextLine();
        }
        return response;
    }

    public static int getPort(){
        String response = "";
        while (!response.matches(PORT_REGEX)){
            System.out.print("Please enter valid port: ");
            response = scanner.nextLine();
        }
        return Integer.parseInt(response);
    }

    public static char scanChar(String message, String chars){
        String response = scanner.nextLine();
        response = response.toUpperCase(Locale.ROOT);
        chars = chars.toUpperCase(Locale.ROOT);
        while (!response.matches("^[ "+ chars +"]$")){
            System.out.println(message);
            response = scanner.nextLine().toUpperCase(Locale.ROOT);
        }
        return response.charAt(0);
    }

}
