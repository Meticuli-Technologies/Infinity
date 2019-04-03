package com.meti.lib.net.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Update implements Response {
    public final ArrayList<Serializable> content;

    public Update(Collection<Serializable> serializables) {
        this(new ArrayList<>(serializables));
    }

    public Update() {
        this(new ArrayList<>());
    }

    public Update(ArrayList<Serializable> content) {
        this.content = content;
    }

    public static Update of(Collection<?> collection) {
        Update update = new Update();
        for(Object obj : collection){
            if(obj instanceof Serializable){
                update.content.add((Serializable) obj);
            }
        }
        return update;
    }
}
