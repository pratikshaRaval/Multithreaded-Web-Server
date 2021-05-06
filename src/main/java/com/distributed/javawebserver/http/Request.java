package com.distributed.javawebserver.http;

import com.distributed.javawebserver.util.RequestUtil;
import java.util.HashMap;

public class Request {

    private RequestUtil requestUtil;
    private String reqMethod;
    private String reqPath;
    private String reqVersion;
    private HashMap<String, String> headers = new HashMap<>();

    public Request() {}

    public void setRequestLine(String requestAsString) {
        requestUtil = new RequestUtil(requestAsString);
        reqMethod = requestUtil.getReqMethod();
        reqPath = requestUtil.getReqPath();
        reqVersion = requestUtil.getReqVersion();
    }

    public void setHeaders(String headerInput) {
        headers = requestUtil.getHeaders(headerInput);
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public String getReqPath() {
        return reqPath;
    }

    public String getReqVersion() {
        return reqVersion;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestUtil=" + requestUtil +
                ", reqMethod='" + reqMethod + '\'' +
                ", reqPath='" + reqPath + '\'' +
                ", reqVersion='" + reqVersion + '\'' +
                ", headers=" + headers +
                '}';
    }
}
