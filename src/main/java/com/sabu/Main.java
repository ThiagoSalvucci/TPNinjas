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
                    ServerManager serverManager = Menu.gameInitHost();
                    if (serverManager != null) {
                        serverManager.run();
                        running = false;
                    }
                    break;
                case '2':
                    ClientManager manager = Menu.gameInitClient();
                    if (manager != null) {
                        manager.run();
                        running = false;
                    }
                    break;

                case '3':
                    running = false;
            }
        }


    }
}





