package com.sabu;

import com.sabu.manager.gamemanager.ClientManager;

import com.sabu.manager.gamemanager.ServerManager;
import com.sabu.utils.Config;
import com.sabu.utils.Menu;


public class Main {


    public static void main(String[] args) {
        Config.init();
        boolean running = true;
        char choice;

        while (running) {
            choice = Menu.selectionMenu();
            switch (choice) {
                case '1':
                    if (Menu.gameInitHost()) {//queres invitar o esperar Ninja que se conected
                        ServerManager manager = new ServerManager();
                        manager.run();
                    }
                    break;

                case '2':
                    if (Menu.gameInitClient()) {
                        ClientManager manager = new ClientManager();
                        manager.run();
                    }
                    break;

                case '3':
                    running = false;
            }
        }

    }
}






//        if (Integer.parseInt(args[0]) == PLAYER_HOST) {
//        ServerManager serverGameManager = new ServerManager();//TODO cambiar puertos para probar!
//        Printer.clearScreen();
//        new HostServer();
//        System.out.println("Server Start.....");
//        HostServer.waitForClient();
//        serverGameManager.connect();
//        Printer.clearScreen();
//
//        System.out.println("Game Start!");
//        serverGameManager.setPlayer();
//        serverGameManager.setNinjas();//TODO mover!
//        Printer.clearScreen();
//
//    }else{
//        ClientManager clientGameManager = new ClientManager();
//
//        clientGameManager.connect();
//        clientGameManager.setPlayer();
//    }
//
//}