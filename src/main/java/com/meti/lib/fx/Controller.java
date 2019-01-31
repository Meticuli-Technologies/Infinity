package com.meti.lib.fx;

import com.meti.lib.module.ModuleManager;
import com.meti.lib.reflect.ClassSource;
import com.meti.lib.state.State;
import com.meti.lib.util.Singleton;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Controller {
    protected final Singleton<State> state = new Singleton<>();
    protected final Map<String, Wizard<?>> wizards = new HashMap<>();
    public final Singleton<Parent> root = new Singleton<>();

    public <T> T onto(URL url) throws IOException {
        ControllerLoader loader = new ControllerLoader(url, state.get(), state.get().singleContent(ModuleManager.class).getClassSources().toArray(new ClassSource[0]));
        Parent parent = loader.load();

        Stage stage = state.get().singleContent(StageManager.class).getPrimaryStage();
        double previousWidth = stage.getWidth();
        double previousHeight = stage.getHeight();

        stage.setScene(new Scene(parent));
        stage.setWidth(previousWidth);
        stage.setHeight(previousHeight);

        return loader.getController();
    }

    public void addWizard(Wizard<?> wizard) {
        wizards.put(wizard.getName().orElse("null"), wizard);
    }

    public Object loadWizard(String name) throws IOException {
        if (root.get() == null) {
            throw new IllegalStateException("Root is null, cannot proceed");
        } else {
            Wizard<?> wizard = wizards.get(name);
            wizard.open();

            return wizard.getResult();
        }
    }

    public void confirm() {
    }

    public Optional<Class<? extends Wizard>> getWizardClass() {
        return Optional.empty();
    }
}
