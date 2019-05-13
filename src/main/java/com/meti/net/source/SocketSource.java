package com.meti.net.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketSource implements Source {
    private final Socket socket;

    public SocketSource(Socket socket) {
        this.socket = socket;
    }

    public SocketSource(int port) throws IOException {
        this(InetAddress.getByName("localhost"), port);
    }

    public SocketSource(InetAddress address, int port) throws IOException {
        this.socket = new Socket(address, port);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    public InetAddress getInetAddress() {
        return socket.getInetAddress();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    public int getPort() {
        return socket.getPort();
    }
}
