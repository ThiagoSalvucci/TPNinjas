package com.sabu.utils;

public class Constants {

    public static final char BOSS = 'B';
    public static final int BOSS_HP = 2;
    public static final char BLANK = ' ';
    public static final char NINJA = 'N';
    public static final int NINJA_HP = 1;
    public static final char BROKE = 'X';
    public static final int HIT = 1;

    public static final char ATTACK = 'A';
    public static final char MOVE = 'M';
    public static final char NOTHING = 'N';

    public static final String SPACE = " ";
    public static final String HIGHLINE = "---------------------";
    public static final String COLUMN = "|";

    public static final int PLAYER_HOST = 0;
    public static final int PLAYER_CLIENT = 1;
    public static final int MAX_PLAYERS = 2;
    public static final int MAX_BOARD_SIZE = 5;
    public static final int MAX_NINJAS = 3;

    public static final String IP_REGEX = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final String PORT_REGEX = "^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
}
