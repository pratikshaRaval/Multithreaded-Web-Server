package com.distributed.javawebserver.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Response {

    String resVersion = "";
    int resStatus;
    HashMap<String, String> resHeaders = new HashMap<String,String>();
    byte[] resBody = new byte[0];

    public void setResStatus(int resStatus) {
        this.resStatus = resStatus;
    }

    public void setResVersion(String resVersion) {
        this.resVersion = resVersion;
    }

    public void setResBody(byte[] resBody) {
        this.resBody = resBody;
    }

    public byte[] getResBody() {
        return resBody;
    }

    public void setResHeaders(String key,String value) {
        this.resHeaders.put(key,value);
    }

    public String getResponseHeader() {

        StringBuilder sb = new StringBuilder();
        sb.append(resVersion);
        sb.append(" ");
        sb.append(resStatus);
        sb.append(" ");
        sb.append(StatusCode.getResponseStatusCode(this.resStatus));
        sb.append(System.lineSeparator());

        StringBuilder headerBuilder = new StringBuilder();
        for (Map.Entry<String, String> header : resHeaders.entrySet()) {
            headerBuilder.append(header.getKey());
            headerBuilder.append(": ");
            headerBuilder.append(header.getValue());
            headerBuilder.append(System.lineSeparator());
        }
        sb.append(headerBuilder.toString());
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Response{" +
                "resVersion='" + resVersion + '\'' +
                ", resStatus=" + resStatus +
                ", resHeaders=" + resHeaders +
                ", resBody=" + resBody.length +
                '}';
    }
}
