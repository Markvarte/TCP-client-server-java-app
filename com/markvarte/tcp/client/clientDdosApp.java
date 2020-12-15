package com.markvarte.tcp.client;

public class clientDdosApp {
    public static void main(String[] args) {
        Thread thread = null;
        // 10 - 10 clients at the same time
        for(int i = 0; i < 10; i++) {
            // In new thread sent requests from client app to server
            // 0 - says server to start working.
            thread = new Thread(() -> ClienApp.run(0));
            thread.start();
        }
        try {
            // Waiting until all thread done their job
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
