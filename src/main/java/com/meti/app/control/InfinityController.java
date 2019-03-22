package com.meti.app.control;

import com.meti.app.User;
import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import com.meti.lib.net.Client;
import com.meti.lib.net.Server;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class InfinityController extends Controller {
    protected final ExecutorService service;

    public InfinityController(State state) {
        super(state);

        this.service = state.byClassToSingle(ExecutorService.class).orElseThrow();
    }

    public Client getClient() {
        return state.byClassToSingle(Client.class).orElseThrow();
    }

    public Server getServer(){
        return state.byClassToSingle(Server.class).orElseThrow();
    }

    public User getUser() {
        return state.byClassToSingle(User.class).orElseThrow();
    }
}
