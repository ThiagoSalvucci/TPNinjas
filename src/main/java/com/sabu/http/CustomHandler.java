package com.sabu.http;

import com.sabu.exception.Error;
import com.sabu.exception.ErrorException;
import com.sabu.mapper.Mapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class CustomHandler implements HttpHandler {

    @Override
    final public void handle(HttpExchange exchange) {
        try {
            handler(exchange);
        } catch (ErrorException ex) {
            Error error = ex.getAsError();
            HttpUtils.sendResponse(error.getStatusCode(), Mapper.toJson(error), exchange);
        } catch (Exception e) {
            Error error = new Error("Unknown error", 500);
            HttpUtils.sendResponse(error.getStatusCode(), Mapper.toJson(error), exchange);
        }
    }

    public abstract void handler(HttpExchange exchange);
}
