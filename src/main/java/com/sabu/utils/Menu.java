package com.sabu.utils;

import com.sabu.http.ClientServer;
import com.sabu.http.HostServer;
import com.sabu.manager.gamemanager.ClientManager;
import com.sabu.manager.gamemanager.ServerManager;

public class Menu {

    private static final String messageHost = "Do you want to send an invite? else you will have to wait for client yo connect! Y/N";
    private static final String messageClient = "Do you want to try to connect to game Host? else you will have to wait for server to invite you! Y/N";


    public static boolean gameInitHost() {
        Printer.print("Starting game!");
        ServerManager serverManager = new ServerManager();
        Printer.print("Host Server Start.....");
        char scan = 'Y';
        int count = 0;
        new HostServer();

        while (!serverManager.isClientConnected() && scan == 'Y'){//PEDIR IPV4

            if (Input.getConnectionMode(messageHost).equals("Y")) {

                serverManager.setIp(Input.getIp());
                if (!serverManager.connect()) {
                      scan = tryAgain();
                      if (scan == 'N') return false;
                }else {
                    Printer.print("Client connected!");
                }

            }else {

                serverManager.waitForClient();
                if (!serverManager.isClientConnected() && count == 10) {
                    scan = tryAgain();
                    if (scan == 'N') return false;
                    count = 0;
                }else Printer.print("Client connected!");
                count ++;
            }
        }
        return true;
    }


    private static char tryAgain(){
        Printer.print("Do you want to try again? Y/N");
        return Input.scanChar("Only Y/N", "YN");
    }

    public static boolean gameInitClient() {
        Printer.print("Starting game!");
        ClientManager clientManager = new ClientManager();
        new ClientServer();
        Printer.print("Client Server Start.....");
        char scan = 'Y';
        int count = 0;

        while (!clientManager.isHostConnected() && scan == 'Y'){

            if (Input.getConnectionMode(messageClient).equals("Y")) {
                clientManager.setIp(Input.getIp());

                if (!clientManager.connect()) {
                    scan = tryAgain();
                    if (scan == 'N') return false;
                }else {
                    Printer.print("Connected to server!!");
                }

            }else {
                clientManager.waitForHost();
                if (!clientManager.isHostConnected() && count == 10) {
                    scan = tryAgain();
                    if (scan == 'N') return false;
                    count = 0;
                }
                count++;
            }


        }
        return true;
    }


    public static char selectionMenu(){
        Printer.print("Choose from these Actions");
        Printer.print("-------------------------\n");
        Printer.print("1 - Host a Game");//
        Printer.print("2 - Connect to a server");//
        Printer.print("3 - Exit Program");//
        return Input.scanChar("Only valid options are 1, 2 , 3","123");
    }


    public static char clientConnectionMenu() {
        Printer.print("Choose from these Actions");
        Printer.print("-------------------------\n");
        Printer.print("1 - Host Ninja Game");//
        Printer.print("2 - Connect to Ninja server");//

        return Input.scanChar("Only valid options are 1, 2 , 3","123");
    }
}

