package com.sabu.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpUtils {
    /**
     * Envia respuesta al cliente con status 200, indicando exito
     *
     * @param response El cuerpo de la respuesta
     * @param exchange El conjunto de datos
     */
    public static void ok(String response, HttpExchange exchange) {
        sendResponse(200, response, exchange);
    }

    /**
     * Envia respuesta al cliente con status 400, indicando falla(ej Json incorrecto, Valores invalidos, etc)
     *
     * @param response El cuerpo de la respuesta
     * @param exchange El conjunto de datos
     */
    public static void badRequest(String response, HttpExchange exchange) {
        sendResponse(400, response, exchange);
    }

    /**
     * Envia respuesta al cliente con status 400, indicando que no se encuentra el recurso
     *
     * @param response El cuerpo de la respuesta
     * @param exchange El conjunto de datos
     */
    public static void notFound(String response, HttpExchange exchange) {
        sendResponse(404, response, exchange);
    }


    public static void sendResponse(int statusCode, String response, HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(statusCode, response.length());
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(response.getBytes(StandardCharsets.UTF_8));
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
