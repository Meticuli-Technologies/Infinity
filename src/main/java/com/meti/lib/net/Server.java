package com.meti.lib.net;

import com.meti.lib.util.Loop;

public abstract class Server extends LoopHandler {

    @Override
    public Loop getLoop() {
        return new Loop() {
            @Override
            public void loop() throws Exception {

            }
        };
    }

    public abstract Client accept();
}
