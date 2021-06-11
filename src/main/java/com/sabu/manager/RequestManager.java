package com.sabu.manager;

import com.sabu.http.HttpUtils;
import com.sabu.http.Response;
import com.sabu.utils.Config;

public class RequestManager {
    private String host;

    public Response sendPost(Object object, String endpoint){//TODO probar
        return HttpUtils.doPost(host + endpoint, object, Response.class);
    }

    public Response sendGet(String endpoint){
        return HttpUtils.doGet(host + endpoint,Response.class);
    }

    public void setIp(String ip) {
        this.host = "http://" + ip + ":" + Config.getPort();
    }
}
