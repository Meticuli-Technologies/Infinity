package com.meti.lib.io.query;

import java.io.Serializable;

public interface Query<T> extends Serializable {
    Class<T> getTypeClass();
}
