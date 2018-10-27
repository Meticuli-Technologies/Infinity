package com.meti.app.main;

import com.meti.lib.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ServerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();

            boolean running = true;
            while (running) {
                running = server.loop();
            }

            server.stop();
        } catch (Exception e) {
            logger.error("Exception in application:", e);
        }
    }
}
