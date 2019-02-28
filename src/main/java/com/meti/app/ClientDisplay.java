package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;

import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class ClientDisplay extends Controller  {
    public ClientDisplay(State state) {
        super(state);
    }

    public void load(Socket socket) {
    }
}
