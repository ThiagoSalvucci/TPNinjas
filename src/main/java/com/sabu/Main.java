package com.sabu;

import com.sabu.manager.gamemanager.ClientManager;
import com.sabu.manager.gamemanager.ServerManager;
import com.sabu.utils.Config;
import com.sabu.utils.Menu;
import com.sabu.utils.Printer;


public class Main {


    public static void main(String[] args) {
        Config.init();
        boolean rematch = true;
        boolean running = true;
        char choice;

        while (running) {
            choice = Menu.selectionMenu();
            switch (choice) {
                case '1':
                    ServerManager serverManager = Menu.gameInitHost();

                    while (serverManager != null && rematch) {
                        serverManager.run();
                        rematch = Menu.rematch();
                    }
                    running = false;
                    break;
                case '2':
                    ClientManager manager = Menu.gameInitClient();
                    while (manager != null && rematch) {
                        manager.run();
                        rematch = Menu.rematch();
                    }
                    running = false;
                    break;

                case '3':
                    running = false;
            }
        }


    }
}





