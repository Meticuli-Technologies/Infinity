package com.meti.app.client;

import com.meti.lib.client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.net.InetAddress;

public class ClientState {
    public final ObservableMap<InetAddress, Client> clientMap = FXCollections.observableHashMap();
}
