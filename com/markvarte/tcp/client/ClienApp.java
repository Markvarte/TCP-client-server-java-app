package com.markvarte.tcp.client;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ClienApp {
    private Socket clientSocket;
    private DataOutputStream dOut;
    private DataInputStream dIn;
    private int clientID;

    ClienApp() {
        // Client random number in hexadecimal system.
        clientID = new Random().nextInt((0x300 - 0x100) + 1) + 0x100;
    }


    public void startConnection(String ip, int port) throws IOException {
        // Creates a new socket.
        clientSocket = new Socket(ip, port);
        // Return an output stream of this socket.
        dOut = new DataOutputStream(clientSocket.getOutputStream());
        // Return an input stream of this socket.
        dIn = new DataInputStream(clientSocket.getInputStream());
    }


    public void receiveMessage() throws IOException {
        long num = dIn.readLong();
        long sec = dIn.readLong();
        // Convert sec to date and time
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        Date date = new Date(sec);
        System.out.println("request #: " + num + " , current date & time: " + sdf.format(date) + " , from client #: " + clientID);
    }

    public void sentMessage(int ConnType) throws IOException {
        dOut.writeInt(ConnType);
        dOut.flush();
    }


    public void stopConnection() throws IOException {
        dOut.close();
        dIn.close();
        clientSocket.close();
    }

    public static void run(int connType) {
        ClienApp client = new ClienApp();
        try {
            System.out.println("Client nr.: " + client.clientID + " start connection");
            client.startConnection("127.0.0.1", 6666);
            client.sentMessage(connType);
            if (connType == 0) {
                System.out.println("Waiting for response from client nr.: " + client.clientID);
                client.receiveMessage();
            }
            client.stopConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter 0 to work, 1 to stop server...");
        int connType = Integer.parseInt(obj.readLine());
        ClienApp.run(connType);
    }
}
