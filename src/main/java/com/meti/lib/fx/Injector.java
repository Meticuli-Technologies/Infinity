package com.meti.lib.fx;

import javafx.fxml.FXMLLoader;

import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Injector extends FXMLLoader {
    public Injector(List<Object> dependencies) {
        //noinspection OverridableMethodCallDuringObjectConstruction
        this.setControllerFactory(new InjectorFactory(dependencies));
    }
}