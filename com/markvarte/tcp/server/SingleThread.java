package com.markvarte.tcp.server;

import com.markvarte.tcp.shared.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class SingleThread implements SocketHandler {

    private static long counter = 0;

    @Override
    public void handle(Socket socket, ServerApp serverApp) {
        try {
            simulateWorking();
            // Get input and output strings of current socket.
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            boolean running = true;
            // Handle client request while client socket exist and
            // While client want to work.
            while (running && !socket.isClosed()) {
                int request = dIn.readInt();
                switch (request) {
                    // Working type
                    case 0:
                        simulateWorking();
                        // Get server response
                        ServerResponse response = new ServerResponse(counter, System.currentTimeMillis());
                        counter++;
                        // Send message
                        dOut.writeLong(response.getNum());
                        dOut.writeLong(response.getSec());
                        System.out.println("sent request number: " + response.getNum() + " , current time in milliseconds: " + response.getSec());
                        // Send off the data
                        dOut.flush();
                        break;
                        // Closing server type
                    case 1:
                        System.out.println("Server closed. ");
                        serverApp.StopContinueWork();
                        serverApp.stop();
                        break;
                    default:
                        System.out.println("Wrong client request");
                        running = false;
                }
            }
            dOut.close();
            dIn.close();
            } catch (IOException | InterruptedException exception) {
            System.err.println("Client from IP: " + socket.getInetAddress() + " handled. ");
        }
    }

    public void simulateWorking() throws InterruptedException {
        // Pause execution
        Random rand = new Random();
        // Random number from 4 to 1 second.
        Thread.sleep((rand.nextInt((4000 - 1000) + 1) + 1000));
    }

}
