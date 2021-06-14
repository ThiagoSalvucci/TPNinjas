package com.sabu.utils;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import static com.sabu.utils.Constants.IP_REGEX;

public class Config {
    private static final Properties properties = new Properties();

    public static void init() {
        try {
            properties.load(new FileInputStream("config.ini"));
        } catch (Exception e) {
            System.out.println("Failed to load config.ini");
            System.out.println("Loaded default values");
        }
    }

    public static String getIp() {
        String ip = properties.getProperty("ip", "127.0.0.1");
        if (!ip.matches(IP_REGEX)) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                Printer.print(e.getMessage());
            }
            return "127.0.0.1";
        }
        return ip;
    }

    public static int getPort() {
        try {
            return Integer.parseInt(properties.getProperty("port", "8080"));
        } catch (Exception e) {
            return 8080;
        }
    }

}
