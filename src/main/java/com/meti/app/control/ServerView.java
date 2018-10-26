package com.meti.app.control;

import com.meti.lib.client.Client;
import com.meti.lib.client.ClientDependency;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Dependency;
import com.meti.lib.fx.PostInitializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.net.InetAddress;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/25/2018
 */
public class ServerView extends Controller implements PostInitializable {
    @FXML
    private ListView<String> fileView;

    public final ObjectProperty<InetAddress> addressProperty = new SimpleObjectProperty<>();

    @Override
    public Set<Class<? extends Dependency>> getDependencyClasses() {
        return Stream.of(ClientDependency.class).collect(Collectors.toSet());
    }

    @Override
    public void postInitialize() {

    }
}
