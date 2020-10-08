package com.ryan;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * This class handles the logic of the request from the client. It runs in a
 * thread and ends on completion of the run() method.
 * 
 * @author Calvin Ryan
 */
public class RequestHandler implements Runnable{
    
    /**
     * The request handler requires a socket or it has no way to communicate
     * with the client.
     */
    private Socket socket;
    
    /**
     * The only constructor requires a reference to a socket.
     * 
     * @param socket 
     */
    public RequestHandler(Socket socket){
        if(null == socket){
            throw new IllegalArgumentException("Socket cannot be null");
        }
        this.socket = socket;
    }

    @Override
    /**
     * Override of run from runnable interface. This performs the interaction
     * with the client;
     */
    public void run() {
        try(DataInputStream inputStream =
                    new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream =
                    new DataOutputStream(socket.getOutputStream());) {
            //displaying the ip address for illustrative purposes
            InetAddress inetAddress = socket.getInetAddress();
            String clientAddress = inetAddress.getHostAddress();
            System.out.println("Connection from: " + clientAddress);
            double fahrenheit = inputStream.readDouble();
            double celsius = (fahrenheit - 32) * (double)5/9;
            // Printing for illustrative purposes
            System.out.printf("\tFahrenheit: %10.5f Celsius: %10.5f\n",
                     fahrenheit, celsius);
            outputStream.writeDouble(celsius);
            outputStream.flush();
        } catch (SocketTimeoutException ste) {
            System.out.println("\tSocket connection timed out: "
                    + ste.getMessage());
        } catch(IOException ioe){
            System.out.println("\tIO Error: " + ioe.getMessage());
        } catch(Exception ex){
            System.out.println("\tERROR: " + ex.getMessage());
        }
    }

}
