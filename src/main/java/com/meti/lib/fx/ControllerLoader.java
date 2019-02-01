package com.meti.lib.fx;

import com.meti.lib.module.ModuleManager;
import com.meti.lib.reflect.ClassSource;
import com.meti.lib.state.State;
import com.meti.lib.util.CollectionUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class ControllerLoader extends FXMLLoader {
    private final State state;

    public ControllerLoader(URL location, State state) {
        super(location);
        this.state = state;
    }

    public ControllerLoader(URL location, State state, String controllerClassName) throws Exception {
        super(location);
        this.state = state;

        if (controllerClassName != null) {
            Set<ClassSource> collect = state.singleContent(ModuleManager.class)
                    .getClassSources()
                    .stream()
                    .filter(classSource -> classSource.byName(controllerClassName).isPresent())
                    .collect(Collectors.toSet());

            ClassSource classSource = CollectionUtil.toSingle(collect);
            setClassLoader(classSource.getClassLoader());
        }
    }

    public static <T> T load(URL location, State state) throws IOException {
        return new ControllerLoader(location, state).load();
    }

    public static <T> T load(URL location, State state, String controllerClassName) throws Exception {
        return new ControllerLoader(location, state, controllerClassName).load();
    }

    @Override
    public <T> T load() throws IOException {
        T parent = super.load();

        Object controllerToken = getController();
        if (controllerToken instanceof Controller) {
            Controller controller = (Controller) controllerToken;

            if (!(parent instanceof Parent)) {
                throw new IllegalStateException(parent + " is not an instanceof " + Parent.class);
            } else {
                controller.root.set((Parent) parent);
            }

            loadController(controller);
        }

        return parent;
    }

    private void loadController(Controller controller) {
        controller.state.set(state);

        if (controller instanceof Confirmable) {
            ((Confirmable) controller).confirm();
        }
    }
}
