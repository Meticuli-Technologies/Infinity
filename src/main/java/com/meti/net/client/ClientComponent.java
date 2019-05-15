package com.meti.net.client;

import com.meti.handle.TokenHandler;

import java.util.Collection;

public interface ClientComponent {
    Collection<? extends TokenHandler> getHandlers();
}
