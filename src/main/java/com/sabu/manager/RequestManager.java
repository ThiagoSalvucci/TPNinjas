package com.sabu.manager;

import com.sabu.http.HttpUtils;
import com.sabu.http.Response;
import com.sabu.utils.Config;

public class RequestManager {
    private static String host;
    public RequestManager() {

    }

    public Response sendPost(Object object, String endpoint){
       String host = this.host + endpoint;
        return HttpUtils.doPost(host + endpoint, object, Response.class);
    }

    public Response sendGet(String endpoint){
        String host = this.host + endpoint;
        return HttpUtils.doGet(host + endpoint, Response.class);
    }

    public void setIp(String ip , int port){
        this.host = "http://" + ip + ":" + port;
    }

}
