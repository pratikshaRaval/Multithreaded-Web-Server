package com.distributed.javawebserver.util;

import java.util.Arrays;
import java.util.HashMap;

public class RequestUtil {

    private String requestRepresentaion;
    private String reqMethod ;
    private String reqPath;
    private String reqVersion;

    public RequestUtil(String requestRepresentaion) {
        this.requestRepresentaion = requestRepresentaion;
        this.reqMethod = requestRepresentaion.split(" ")[0];
        this.reqPath = requestRepresentaion.split(" ")[1];
        this.reqVersion = requestRepresentaion.split(" ")[2];
    }

    public String getReqMethod() {
        return this.reqMethod;
    }

    public String getReqPath() {
        return this.reqPath;
    }

    public String getReqVersion() {
        return this.reqVersion;
    }

    public HashMap getHeaders(String headerLines) {
        HashMap<String, String> requestHeaderMap = new HashMap<String, String>();
        String[] headersArray = headerLines.split(System.lineSeparator());
        for (String headerLine : headersArray) {
            String[] header = headerLine.split(":");
            String headerKey = header[0];
            String headerValue = String.join(":", Arrays.copyOfRange(header, 1, header.length)).trim();
            requestHeaderMap.put(headerKey, headerValue);
        }
        return requestHeaderMap;
    }
}
