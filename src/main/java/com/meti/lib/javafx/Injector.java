package com.meti.lib.javafx;

import javafx.fxml.FXMLLoader;

import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Injector extends FXMLLoader {
    public Injector(List<Object> dependencies) {
        super(null, null, new InjectorFactory(dependencies));
    }
}