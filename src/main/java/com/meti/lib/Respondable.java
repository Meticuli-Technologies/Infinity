package com.meti.lib;

import java.io.Serializable;

public interface Respondable<R extends Response> extends Serializable {
    Class<? extends R> getResponseClass();
}
