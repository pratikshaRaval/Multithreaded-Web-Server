=======
# Multithreaded-Web-Server

This Program is built with maven. creates a jar file. This jar along with supported-libs can be deployed as individual HTTP Web Server that supports GET request..

1. Extract the tar.gz and Move to the extracted directory. 
   
2. Build the Project. We need maven installed on our system. I will use full path of the maven. 

    [INFO] Scanning for projects...
    [INFO] 
    [INFO] ------------------< com.distributed:java-web-server >-------------------
    [INFO] Building java-web-server 1.0
    [INFO] --------------------------------[ jar ]---------------------------------
    [INFO] 
    [INFO] --- maven-clean-plugin:3.1.0:clean (default-clean) @ java-web-server ---
    [INFO] 
    [INFO] --- maven-resources-plugin:2.7:resources (default-resources) @ java-web-server

    .............

    INFO] Copying 5 resources
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  11.666 s
    [INFO] Finished at: 2020-04-28T20:53:38-07:00
    [INFO] ------------------------------------------------------------------------

3. Move to the target/java-web-server-deployment.


4. Run the jar with -document_root and -port arguments. 

    $ java -jar java-web-server.jar -document_root ./base_directory -port 6789
    Starting the Web Server
    Web Server running with : 6789  root Folder : 
    java-web-server-master/target/java-web-server-deployment/./base_directory
    Web Server Started on : 6789 with Document Root : ./base_directory
    ```

Make various Test Cases  ( Step 3)
===================================

make various requests for different codes 

| Plugin | README | Content-Type |
| ------ | ------ | ------------ |
  | localhost:6789/                                                                |         200        |  text/html      |
  | localhost:6789/index.html                                                      |         200        |  text/html      |
  | localhost:6789/test.txt                                                        |         200        |  text/plain     |
  | localhost:6789/test.png                                                        |         200        |  image/png      |
  | localhost:6789/test.jpg                                                        |         200        |  image/jpg      |  
  | localhost:6789/test.gif                                                        |         200        |  image/gif      | 
  | localhost:6789/temp         ( tmp should be present under your document_root ) |         400        |                 |     
  | localhost:6789/temp.txt                                                        |         404        |                 |   
  | localhost:6789/test_perm.txt                                                   |         403        |                 |  
  | POST localhost:6789/index.html                                                 |         405        |                 | 
=======
# Multithreaded-Web-Server

I have used approach of MultiThreading. For every incoming request to the server, a new thread is being created. This Program meets all the requirements specified in required assignment document. Web Server is able to

1. Send 200 if requested resource is present under document_root and has proper permission for other users
2. Send 400 if requested resources if directory instead of file.
3. Send 403 if requested resources is file and present under document_root but permissions for "OTHER" users is not "READ"
4. Send 404 if requested resource(file/directory) is not found under document_root
5. Send 405 if any other method other than GET is used
