package com.meti.lib.respond;

import java.io.Serializable;

public interface Respondable<R extends Response> extends Serializable {
    Class<? extends R> getResponseClass();
}
