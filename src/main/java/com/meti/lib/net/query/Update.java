package com.meti.lib.net.query;

import java.util.ArrayList;

public class Update implements Response {
    private final ArrayList<Object> updates;

    public Update(ArrayList<Object> updates) {
        this.updates = updates;
    }
}
