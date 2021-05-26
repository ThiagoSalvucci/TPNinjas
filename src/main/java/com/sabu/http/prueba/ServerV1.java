//package com.sabu.http;
//
//import com.sabu.Exceptions.Error;
//import com.sabu.Exceptions.ErrorException;
//import com.sabu.entities.Ninja;
//import com.sabu.mapper.Mapper;
//import com.sabu.validator.Validator;
//import com.sun.net.httpserver.HttpExchange;
//import com.sun.net.httpserver.HttpHandler;
//VERSION PT
//public class ServerV1 {

//public void postSetNinja() {
//        server.createContext("/setninja", new HttpHandler() {
//@Override
//public void handle(HttpExchange exchange) {
//        Mapper mapper = new Mapper(Ninja.class);
//        Ninja ninja = (Ninja) mapper.fromJson(exchange.getRequestBody());  // pasa a inputStream
//        try {
//        Validator.isNotNull(ninja, "Ninja is null", 400);
//        Validator.isValidRange(ninja.getX(), 0, 4, "Invalid X range");
//        Validator.isValidRange(ninja.getY(), 0, 4, "Invalid Y range");
//        }catch (ErrorException ex){
//        Error error = ex.getAsError();
//        HttpUtils.sendResponse(error.getStatusCode(), error.getMessage(), exchange);
//        return;
//        }
//        HttpUtils.ok("Operacion Exitosa", exchange);
//        }
//        });
//        }

////
//public void postSetNinja() {
//        server.createContext("/setninja", new CustomHandler() {
//@Override
//public void handler(HttpExchange exchange) {
//        Mapper mapper = new Mapper(Ninja.class);
//        Ninja ninja = (Ninja) mapper.fromJson(exchange.getRequestBody());  // pasa a inputStream
//        Validator.isNotNull(ninja, "Ninja is null", 400);
//        Validator.isValidRange(ninja.getX(), 0, 4, "Invalid X range");
//        Validator.isValidRange(ninja.getY(), 0, 4, "Invalid Y range");
//        HttpUtils.ok("Operacion Exitosa", exchange);
//        }
//        });
//        }

