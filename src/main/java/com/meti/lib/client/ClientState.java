package com.meti.lib.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.net.InetAddress;

public class ClientState {
    public final ObservableMap<InetAddress, Client<SocketConnection>> clientMap = FXCollections.observableHashMap();

    public InetAddress addClient(Client<SocketConnection> client){
        InetAddress address = client.connection.socket.getInetAddress();
        clientMap.put(address, client);
        return address;
    }
}
