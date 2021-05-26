package com.sabu;

import com.sabu.entities.Board;
import com.sabu.entities.Ninja;
import com.sabu.entities.Player;
import com.sabu.http.Server;

import java.util.Locale;
import java.util.Scanner;

import static com.sabu.Constants.*;

public class Main {


    public static void main(String[] args) {

//        HOST O CLIENT
//        SI HOST PIDO NOMBRE, SI CLIENTE PIDO IP Y PUERTO DEL SV
//        HOST PIDO POS NINJAS;
//        CREO TABLERO CON NINJAS Y CON ESTE CREO EL PLAYER
//        START SERVER;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al juego!!");
        System.out.println("Host = 0, Client = 1");
        String response = scanner.nextLine();
        Game game = new Game();


        if (response.equals("0")) {
            System.out.println("Ingrese su nombre!");
            String name = scanner.nextLine();
            Board board = new Board();
            Printer.printBoard(board);
            int ninjas = 0;

            System.out.println("Ingrese el NinjaBoss Ej ('A1', X = A, Y = 1)"); //Ingrese tipo de ninja que quiere ubicar(Boss = B o Ninja comun = N)," +"luego ingrese la posicion Ej('BA1' Ninja Boss en la posicion X = A Y = 1)"

            while (ninjas < MAX_NINJAS) {
                response = scanner.nextLine(); //TODO validar 1,1
                response = response.toUpperCase(Locale.ROOT);

                boolean isBoss = ninjas == 0;
                int x = (int) response.charAt(0) - 65;
                int y = (int) response.charAt(1) - 49;


                Ninja ninja = new Ninja(isBoss, x, y);
                try {
                    ninja.validate();
                    board.setUnit(x, y, isBoss ? BOSS : NINJA);
                    ninjas++;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                Printer.clearScreen();
                Printer.printBoard(board);
                System.out.println("Ingrese el proximo ninja! EjEj('NB2' Ninja comun en la posicion X = B Y = 2)");
            }

            game.addPlayer(new Player(name, board));
            Printer.clearScreen();
            Printer.printBoard(game.getPlayer(PLAYER_HOST).getBoard());
            new Server(game);
            System.out.println("Server Start.....");
        }

    }
}

