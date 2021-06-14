package com.sabu.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;


public class Mapper {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static <T> T fromJson(InputStream in, Type type) {
        return gson.fromJson(new InputStreamReader(in), type);
    }
}
