package com.sabu.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;


public class Mapper {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type;

    public Mapper(Type type) {
        this.type = type;
    }

    public String toJson(Object object) {
        return gson.toJson(object, type);
    }

    public Object fromJson(String json) {
        return gson.fromJson(json, type);
    }

    public Object fromJson(InputStream in) {
        return gson.fromJson(new InputStreamReader(in), type);
    }

}
