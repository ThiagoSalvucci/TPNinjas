package com.sabu.manager;

import com.sabu.http.HttpUtils;
import com.sabu.http.Response;

public class RequestManager {
    private static String host;

    public Response sendPost(Object object, String endpoint) {
        return HttpUtils.doPost(host + endpoint, object, Response.class);
    }

    public Response sendGet(String endpoint) {
        return HttpUtils.doGet(host + endpoint, Response.class);
    }

    public void setIp(String ip, int port) {
        host = "http://" + ip + ":" + port;
    }

}
