package com.meti.lib.fx;

import com.meti.lib.module.ModuleManager;
import com.meti.lib.reflect.ClassSource;
import com.meti.lib.state.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class ControllerLoader extends FXMLLoader {
    private final State state;
    private final Set<ClassSource> classSources;

    public ControllerLoader(URL location, State state, ClassSource... classSources) {
        super(location);

        this.state = state;

        /*
        Probably not the best way from ClassSource[] to Set<ClassSource>, just saying.
         */
        this.classSources = Stream.of(classSources).collect(Collectors.toSet());
    }

    public static <T> T load(URL location, State state) throws IOException {
        return load(location, state, state.singleContent(ModuleManager.class).getClassSources().toArray(new ClassSource[0]));
    }

    public static <T> T load(URL location, State state, ClassSource... classSources) throws IOException {
        return new ControllerLoader(location, state, classSources).load();
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
        controller.confirm();
    }
}
