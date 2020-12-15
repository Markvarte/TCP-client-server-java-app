package com.markvarte.tcp.server;

import java.net.Socket;

public class MultiThread implements SocketHandler {
    @Override
    public void handle(Socket socket, ServerApp serverApp) {
        // Run single thread logic in new thread, so using multi thread approach.
        new Thread(() -> new SingleThread().handle(socket, serverApp)).start();
    }
}
