package com.meti.app.io.update;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class UpdateBundle extends ArrayList<Update> implements Serializable {
    public UpdateBundle(Collection<? extends Update> updates) {
        addAll(updates);
    }
}
