package com.distributed.javawebserver.http;

import com.distributed.javawebserver.http.Request;
import com.distributed.javawebserver.http.Response;
import com.distributed.javawebserver.http.StatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Date;
import java.util.Set;

public class GenericResponseType {

    public String publicDirectory;

    public GenericResponseType(String filepath) {
        publicDirectory = filepath;
    }

    public String getPublicDirectory() {
        return publicDirectory;
    }

    public Response processAndSetResponse(Request request, Response response) throws IOException {

        response.setResHeaders("Date",new Date().toString());
        response.setResVersion(request.getReqVersion());

        if(!request.getReqMethod().equals("GET")){
            response.setResStatus(StatusCode.METHOD_NOT_ALLOWED);
            response.setResHeaders("Content-Length","0");
            return response;
        }
        String finalPath = getPublicDirectory() + request.getReqPath();
        if(finalPath.endsWith("/")){
            finalPath = finalPath + "index.html";
        }
        File file = new File(finalPath);
        if(!file.exists() ){
            response.setResStatus(StatusCode.NOT_FOUND);
            response.setResHeaders("Content-Length","0");
            return response;
        }
        if(file.isDirectory()){
            response.setResStatus(StatusCode.BAD_REQUEST);
            response.setResHeaders("Content-Length","0");
            return response;
        }

        if(file.exists() && !file.isDirectory()){
            response.setResStatus(StatusCode.OK);
            final Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());


            if(perms.contains(PosixFilePermission.OTHERS_READ)){
                if(finalPath.endsWith("html") || finalPath.endsWith("htmls") || finalPath.endsWith("HTML") || finalPath.endsWith("HTMLS") || finalPath.equals("/")){
                    response.setResHeaders("Content-Type","text/html");
                }else if(finalPath.endsWith("txt")  || finalPath.endsWith("TXT") ){
                    response.setResHeaders("Content-Type","text/plain");
                    response.setResHeaders("charset","UTF-8");
                }else if(finalPath.endsWith("jpeg")  || finalPath.endsWith("JPEG")  || finalPath.endsWith("jpg")  || finalPath.endsWith("JPG") ){
                    response.setResHeaders("Content-Type","image/jpeg");
                }else if(finalPath.endsWith("GIF")  || finalPath.endsWith("gif") ){
                    response.setResHeaders("Content-Type","image/gif");
                }else if(finalPath.endsWith("png")  || finalPath.endsWith("PNG") ){
                    response.setResHeaders("Content-Type","image/png");
                }

                byte[] fullBodyContents = null;
                fullBodyContents = getBody(finalPath);
                response.setResHeaders("Content-Length",String.valueOf(fullBodyContents.length));
                response.setResBody(fullBodyContents);
                return response;
            }else{
                response.setResHeaders("Content-Length","0");
                response.setResStatus(StatusCode.FORBIDDEN);
                return response;
            }
        }
        return null;
    }

    private byte[] getBody(String filePath) {
        if(filePath.equals("./")){
            filePath = filePath + "index.html";
        }
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new byte[0];
        }
    }


}

