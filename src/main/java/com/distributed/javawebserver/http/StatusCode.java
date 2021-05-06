package com.distributed.javawebserver.http;

import java.util.HashMap;
import java.util.Map;

public class StatusCode {

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;

    public static final Map<Integer,String> statusDescription = new HashMap<Integer,String>();

    static{
        statusDescription.put(OK,"OK");
        statusDescription.put(BAD_REQUEST,"Requested Resource is Directory");
        statusDescription.put(FORBIDDEN,"You are not allowed to access the specified resource.Specified Resource doesn't have Permission for Other User to read the file");
        statusDescription.put(NOT_FOUND,"Specified resource is not present on the server");
        statusDescription.put(METHOD_NOT_ALLOWED,"This Method is not supported by Server");
    }

    public static String getResponseStatusCode(int resStatusCode) {
        return statusDescription.get(resStatusCode);
    }
}
