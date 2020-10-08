package com.ryan;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Calvin Ryan
 */
public class FahrenheitToCelsiusServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //specify the port to listen on
        int port = 5555;
        //specify the number of threads in the thread pool
        int threadCount = 100;
        //specify the default timeout in milliseconds.
        int timeoutLength = 3000;
        
        //get an ExecutorService to manage the thread pool.
        ExecutorService executorService =
                Executors.newFixedThreadPool(threadCount);
        //Instantiate a ServerSocket to do the listening
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.printf("ServerSocket listening on port %d\n", port);

            // A local variable for incoming Socket objects
            Socket socket;
            /*
                Under normal circumstances, do not use <code>while(true)</code>
                for loops.  It is used here so the example can be done without
                having to build complex architecture.
                Normally, a server will listen on more than one port.  In
                addition to the service port, there will be a control port used
                by a local program to give instructions to the server.  That
                port would get then instruction to shut down.  The shut down
                instruction would change the loop variable to cause this loop
                to exit.
            */
            while(true){
                // wait for a connection
                socket = serverSocket.accept();
                // Set the timeout to three seconds
                socket.setSoTimeout(timeoutLength);

                // hand the connected Socket to a RequestHandler in a thread
                executorService.execute(new RequestHandler(socket));
            }

        } catch (IOException ex) {
            System.out.println("CATASTROPHIC FAILURE:  Server Stopping!");
            System.out.println(ex.getMessage());
        }
        
    }

}
