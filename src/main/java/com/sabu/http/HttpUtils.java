package com.sabu.http;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.sabu.exception.Error;
import com.sabu.mapper.Mapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class HttpUtils {

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERRPR = 500;
    public static final int NO_CONTENT = 204;
    /**
     * Sends the response to the client with status 200, indicating success.
     *
     * @param response The body of the response
     * @param exchange The attributes
     */
    public static void ok(String response, HttpExchange exchange) {
        sendResponse(OK, response, exchange);
    }

    /**
     * Sends the response to the client with status 400, indicating failure (ex incorrect Json, invalid attributes, etc)
     *
     * @param response The body of the response
     * @param exchange The attributes
     */
    public static void badRequest(String response, HttpExchange exchange) {
        sendResponse(BAD_REQUEST, response, exchange);
    }

    /**
     * Sends the response to the client with status 404, indicating that the recurse was not found.
     *
     * @param response The body of the response
     * @param exchange The attributes
     */
    public static void notFound(String response, HttpExchange exchange) {
        sendResponse(NOT_FOUND, response, exchange);
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

    public static Response doGet(String endpoint, Type type) {
        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        Response response = null;

        try {
            HttpRequest httpRequest = requestFactory.buildGetRequest(new GenericUrl(endpoint));
            HttpResponse httpResponse = httpRequest.execute();
            int responseCode = httpResponse.getStatusCode();
            Object responseBody = Mapper.fromJson(httpResponse.parseAsString(), type);
            response = new Response(responseCode,"",responseBody);//TODO
            httpResponse.disconnect();
        } catch (IOException e) {
            System.out.println("Connection refused");
        }

        return response;
    }


    public static Response doPost(String endpoint,Object body,Type responseType){

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        Response response = null;
        try {
            HttpContent content = ByteArrayContent.fromString(null,Mapper.toJson(body));
            HttpRequest httpRequest = requestFactory.buildPostRequest(new GenericUrl(endpoint),content);
            httpRequest.getHeaders().setContentType("application/json");
            HttpResponse httpResponse = httpRequest.execute();
            int responseCode = httpResponse.getStatusCode();
            Object responseBody = Mapper.fromJson(httpResponse.parseAsString(), responseType);
            response = new Response(responseCode,"TODO",responseBody);// TODO
            httpResponse.disconnect();
        }catch (HttpResponseException e){
            Error error = Mapper.fromJson(e.getContent(),Error.class);
            response = new Response(error.getStatusCode(), error.getMessage(), error);
        } catch (IOException e) {
            System.out.println("Connection refused");
        }

        return response;
    }

}
