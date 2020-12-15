package com.markvarte.tcp.server;
import java.net.*;
import java.io.*;

public class ServerApp {
    private ServerSocket serverSocket;
    private SocketHandler handeledSocket;
    boolean continueWork = true;

    ServerApp(SocketHandler handler) {
        handeledSocket = handler;
    }


    public void start(int port) {
        try {
            System.out.println("Start server on port " + port);
            serverSocket = new ServerSocket(port);
            while(continueWork) {
                // The server waits, listening to the socket for a client to make a connection request.
                var clientSocket = serverSocket.accept();
                System.out.println("Accept TCP connection. IP: " + clientSocket.getInetAddress());
                // Execute request handling.
                handeledSocket.handle(clientSocket, this);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void StopContinueWork() {
        continueWork = false;
    }


    public void stop() throws IOException {
        // Close server.
        serverSocket.close();
        // Exit form system.
        System.exit(0);
    }

    public static void main(String[] args) {
        // Here: new SingleThread() for 1 thread approach.
        ServerApp server = new ServerApp(new SingleThread());
        // 6666 - port number.
        server.start(6666);
    }
}
