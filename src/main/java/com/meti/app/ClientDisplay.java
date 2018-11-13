package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.PostInitializable;
import com.meti.lib.net.Client;

import java.util.Optional;

public class ClientDisplay extends Controller implements PostInitializable  {
    @Override
    public void postInitialize() {
        Optional<Client> clientOptional = state.firstOfType(Client.class);
        if(clientOptional.isPresent()){
            Client<?> client = clientOptional.get();
        }
        else{
            throw new IllegalStateException("Client not found");
        }
    }
}
