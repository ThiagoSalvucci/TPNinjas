package com.sabu.utils;

import com.sabu.entities.Attack;
import com.sabu.entities.Ninja;
import com.sabu.validator.Validator;

import java.awt.*;
import java.util.Locale;
import java.util.Scanner;

public class Input {

    private static Scanner scanner = new Scanner(System.in);

    public static Point getBoardLocation(String message){
        Point point = null;
        while(point == null){
            try{
                System.out.println(message);
                String response = scanner.nextLine();
                response = response.toUpperCase(Locale.ROOT);
                Validator.isTrue(response.matches("^[A-E][1-5]$"), "Invalid input");
                int x = (int) response.charAt(0) - 65;
                int y = (int) response.charAt(1) - 49;
                point = new Point(x,y);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return point;
    }

    public static String getName() {
        System.out.print("Insert your name: ");
        String response = scanner.nextLine();

        while (response.trim().isEmpty()){
            System.out.println("Your name cannot be empty!, Try again");
            response = scanner.nextLine();
        }
        return response;
    }

    public static int getConnectionMode(){
        System.out.println("how do you wanna launch? Host = 0, Client = 1");
        String response = scanner.nextLine();
        while (!response.matches("^[0-1]$")){
            System.out.println("Invalid input, Host = 0, Client = 1");
            response = scanner.nextLine();
        }
        return Integer.parseInt(response);
    }

    public static Attack getAttack(){
        Attack attack = null;
        while(attack == null) {
            try {
                System.out.println("Enter attack location");
                String response = scanner.nextLine();
                response = response.toUpperCase(Locale.ROOT);
                Validator.isTrue(response.matches("^[A-E][1-5]$"), "Invalid input");
                attack = new Attack();
                attack.setAttackX((int) response.charAt(0) - 65);
                attack.setAttackY((int) response.charAt(1) - 49);
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

}
