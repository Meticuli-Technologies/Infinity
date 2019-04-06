package com.meti.lib.collect.manage;

import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
public abstract class Manager<T> extends ArrayList<T> {
    public abstract T allocate();
}
