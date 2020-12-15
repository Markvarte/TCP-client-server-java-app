package com.markvarte.tcp.server;

import java.net.Socket;

public interface SocketHandler {
    public void handle(Socket socket, ServerApp serverApp);
}
