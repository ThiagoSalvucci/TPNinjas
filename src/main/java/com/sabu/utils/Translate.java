package com.sabu.utils;

public class Translate {

    public static char translateCharToNumber(String toTransform){
        return (char) (toTransform.charAt(0) - 17);
    }

    public static int translateIntToChar(char toTransform,int value){
        return (int) (toTransform - value);
    }


}
