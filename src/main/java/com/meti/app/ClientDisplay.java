package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.command.GetCommand;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Executors;

public class ClientDisplay extends Controller implements PostInitializable  {
    @Override
    public void postInitialize() {
        Optional<Client> clientOptional = state.firstOfType(Client.class);
        if(clientOptional.isPresent()){
            Client<?> client = clientOptional.get();
            try {
                loadClient(client);
            } catch (Exception e) {
                getLogger().error("Failed to load client", e);
            }
        }
        else{
            throw new IllegalStateException("Client not found");
        }
    }

    private void loadClient(Client<?> client) throws Exception {
        GetCommand<String, ArrayList<String>, ArrayList> command = new GetCommand<>(new ArrayList<>(), ArrayList.class);
        ArrayList<?> result = client.runReturnableCommand(command, Executors.newSingleThreadExecutor(), Duration.ofSeconds(1));

        //TODO: load client in ClientDisplay.postInitialize()
    }
}
