package com.meti.lib.client;

import com.meti.lib.manage.Manager;

import java.net.InetAddress;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ClientManager extends Manager<InetAddress, Client> {
    private class ClientConsumer implements Consumer {
        @Override
        public void accept(Object o) {

        }
    }
}
