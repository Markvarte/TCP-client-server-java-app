package com.markvarte.tcp.shared;

public class ServerResponse {
    private long num;
    private long sec;

    public ServerResponse(long num, long sec) {
        this.num = num;
        this.sec = sec;
    }

    public long getNum() {
        return num;
    }

    public long getSec() {
        return sec;
    }

}
