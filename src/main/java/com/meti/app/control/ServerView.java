package com.meti.app.control;

import com.meti.app.server.Location;
import com.meti.app.server.command.RequestCommand;
import com.meti.app.server.command.RequestType;
import com.meti.lib.client.Client;
import com.meti.lib.client.ClientDependency;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.Dependency;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/25/2018
 */
public class ServerView extends Controller {
    private final Logger logger = LoggerFactory.getLogger(ServerView.class);

    @FXML
    private ListView<Location> locationView;

    @Override
    public Set<Class<? extends Dependency>> getDependencyClasses() {
        return Stream.of(ClientDependency.class).collect(Collectors.toSet());
    }

    public void reload(InetAddress address) {
        try {
            reloadLocations(address);
        } catch (Exception e) {
            logger.error("Failed to reload", e);
        }
    }

    private void reloadLocations(InetAddress address) throws Exception {
        Client client = getDependency(ClientDependency.class).loadAddress(address);
        ArrayList<?> locationList = requestLocations(client);
        reloadLocationView(locationList, locationView.getItems());
    }

    private void reloadLocationView(ArrayList<?> locationList, ObservableList<Location> items) {
        locationList.stream()
                .filter((Predicate<Object>) o -> Location.class.isAssignableFrom(o.getClass()))
                .map((Function<Object, Location>) o -> (Location) o)
                .forEach(items::add);
    }

    private ArrayList<?> requestLocations(Client client) throws Exception {
        return (ArrayList<?>) client.runCommand(new RequestCommand<>(ArrayList.class, RequestType.LOCATIONS))
                    .orElse(new ArrayList());
    }
}
