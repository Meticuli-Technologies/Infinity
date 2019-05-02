package com.meti.lib.io.respond;

import java.io.Serializable;

public interface Response extends Serializable {
    Exception getException();

    String getValue();
}
