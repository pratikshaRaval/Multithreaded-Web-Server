package com.distributed.javawebserver;

import com.distributed.javawebserver.http.GenericResponseType;
import com.distributed.javawebserver.util.RequestThreadUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class JavaWebServerApplication {

	private static final Logger logger = LogManager.getLogger(JavaWebServerApplication.class);

	static String webServerDocumentRoot = ".";
	static int webServerPort = 8000;
	static int webServerMaxThreads = 100;
	private static ExecutorService threadExecutor = Executors.newFixedThreadPool(webServerMaxThreads);

	public static void main(String[] args) throws IOException {

		System.out.println("Starting the Web Server");
		parseArgsToWebServer(args);
		System.out.println("Web Server running with : " + webServerPort + "  root Folder : " + new File(webServerDocumentRoot).getAbsolutePath());
		GenericResponseType genericResponseType = new GenericResponseType(webServerDocumentRoot);
		startWebServer(genericResponseType);
		SpringApplication.run(JavaWebServerApplication.class, args);
	}

	static void parseArgsToWebServer(String[] args) {
		HashMap<String, String> cmdLineArgsMap = new HashMap<String,String>();
		if(args.length == 4){
			for (int i = 0; i < 4; i = i + 2) {
				String tempCmdArgument = args[i];
				if (tempCmdArgument.equals("-document_root") || tempCmdArgument.equals("-port")) {
					cmdLineArgsMap.put("-" + tempCmdArgument.charAt(1), args[i + 1]);
				}
			}
			webServerPort = Integer.parseInt(cmdLineArgsMap.getOrDefault("-p",String.valueOf(webServerPort)));
			webServerDocumentRoot = cmdLineArgsMap.getOrDefault("-d",webServerDocumentRoot);
		}else{
			logger.error("Provide Proper arguments with -document_root and -port");
			System.exit(-1);
		}
	}

	static void startWebServer(GenericResponseType genericResponseType) throws IOException {
		ServerSocket serverSocket = new ServerSocket(webServerPort);
		try {
			while (true) {
				System.out.println("Web Server Started on : " + webServerPort + " with Document Root : " + webServerDocumentRoot);
				Socket clientConnectionSocket = serverSocket.accept();
				Runnable connectionHandler = new RequestThreadUtil(clientConnectionSocket, genericResponseType);
				threadExecutor.execute(connectionHandler);
			}
		} catch (IOException e) {
			logger.error(" Port is already in Use : " + webServerPort + e.getMessage());
			System.exit(-1);
		} finally {
			serverSocket.close();
		}
	}
}
