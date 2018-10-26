package com.meti.lib.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.net.InetAddress;

public class ClientState {
    public final ObservableMap<InetAddress, Client> clientMap = FXCollections.observableHashMap();
}
