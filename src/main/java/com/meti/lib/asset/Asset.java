package com.meti.lib.asset;

import com.meti.app.User;

public interface Asset {
    String getName();

    User lastModified();

    int size();
}
