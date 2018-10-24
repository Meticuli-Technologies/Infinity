package com.meti.app.control.wizard;

import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Dependency;
import com.meti.lib.fx.depend.WindowedDependency;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class AddServer extends Controller {
    @FXML
    private TextField address;

    @FXML
    private TextField port;

    @Override
    public Set<Class<? extends Dependency>> getDependencyClasses() {
        return Stream.of(WindowedDependency.class).collect(Collectors.toSet());
    }

    public void cancel() {
        getDependency(WindowedDependency.class).stage.close();
    }

    public void apply() {
        getDependency(WindowedDependency.class).stage.close();
    }
}
