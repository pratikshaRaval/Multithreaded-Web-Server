package com.distributed.javawebserver.util;

import com.distributed.javawebserver.JavaWebServerApplication;
import com.distributed.javawebserver.http.Request;
import com.distributed.javawebserver.http.Response;
import com.distributed.javawebserver.http.GenericResponseType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class RequestThreadUtil implements Runnable  {

    private static final Logger logger = LogManager.getLogger(RequestThreadUtil.class);

    private Socket clientSocket;
    private GenericResponseType genericResponseType;

    public RequestThreadUtil(Socket socket, GenericResponseType genericResponseType) {
        this.clientSocket = socket;
        this.genericResponseType = genericResponseType;
    }

    public void run() {
        System.out.println("Started Processing request : " + Thread.currentThread().getName());
        try {
            Request request = parseHTTPRequest(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
            logger.debug(request.toString());
            sendHTTPResponse(request, new PrintStream(clientSocket.getOutputStream()),new BufferedOutputStream(clientSocket.getOutputStream()));
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Completed Processing request : " + Thread.currentThread().getName());
    }

    Request parseHTTPRequest(BufferedReader requestReaderSocket) {
        Request request = new Request();
        try {
            String methodInitString = requestReaderSocket.readLine();
            request.setRequestLine(methodInitString);
            if (requestReaderSocket.ready()) {
                request.setHeaders(getAllHeaders(requestReaderSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    String getAllHeaders(BufferedReader br) throws IOException {
        StringBuilder headerStringBuilder = new StringBuilder();
        String headerLine = br.readLine();
        while (headerLine != null && !headerLine.trim().isEmpty()) {
            headerStringBuilder.append(headerLine.trim());
            headerStringBuilder.append(System.lineSeparator());
            headerLine = br.readLine();
        }
        return headerStringBuilder.toString();
    }

    void sendHTTPResponse(Request request, PrintStream out, BufferedOutputStream dataOut ) throws IOException {
        Response response = genericResponseType.processAndSetResponse(request, new Response());
        String responseStatusString = response.getResponseHeader();
        out.write(responseStatusString.getBytes());
        out.write(response.getResBody());
    }
}


/*

jpatel1@linux10609 base_directory]$ ls
index.html  sample_dir  test.gif  test.jpg  test.png  test.txt
[jpatel1@linux10609 base_directory]$ touch test_perm.txt
[jpatel1@linux10609 base_directory]$ ls
index.html  sample_dir  test.gif  test.jpg  test_perm.txt  test.png  test.txt
[jpatel1@linux10609 base_directory]$ echo "This text should not be shown on browser because this file's permission" > test_perm.txt
[jpatel1@linux10609 base_directory]$ touch test_perm.txt
[jpatel1@linux10609 base_directory]$ cat test_perm.txt
This text should not be shown on browser because this file's permission
[jpatel1@linux10609 base_directory]$ ls -altr test_perm.txt
-rw-r--r--. 1 jpatel1 dip 72 Apr 28 20:34 test_perm.txt
[jpatel1@linux10609 base_directory]$ chmod o=--- test_perm.txt
[jpatel1@linux10609 base_directory]$ ls -altr test_perm.txt
-rw-r-----. 1 jpatel1 dip 72 Apr 28 20:34 test_perm.txt
[jpatel1@linux10609 base_directory]$


 */