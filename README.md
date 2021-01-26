# Multithreaded-Web-Server

This Program is built with mavn creates a jar file. This jar along with supported-libs can be deployed as individual HTTP Web Server that supports GET request..

I have used approach of MultiThreading. For every incoming request to the server, a new thread is being created. This Program meets all the requirements specified in required assignment document. Web Server is able to

Send 200 if requested resource is present under document_root and has proper permission for other users
Send 400 if requested resources if directory instead of file.
Send 403 if requested resources is file and present under document_root but permissions for "OTHER" users is not "READ"
Send 404 if requested resource(file/directory) is not found under document_root
Send 405 if any other method other than GET is used
