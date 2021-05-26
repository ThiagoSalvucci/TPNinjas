package com.sabu;

import com.sabu.entities.Board;
import com.sabu.entities.Ninja;
import com.sabu.entities.Player;
import com.sabu.http.Server;
import com.sabu.utils.Config;
import com.sabu.utils.Input;
import com.sabu.utils.Printer;
import com.sabu.validator.Validator;

import java.awt.*;
import java.util.Locale;
import java.util.Scanner;

import static com.sabu.utils.Constants.*;

public class Main {


    public static void main(String[] args) {
        Config.init();

        System.out.println("Welcome to the Game!!");
        Game game = new Game();

        if (Input.getConnectionMode() == PLAYER_HOST) {
            game.createPlayer();
            Printer.clearScreen();
            new Server(game);
            System.out.println("Server Start.....");
        }else{

        }

    }
}

