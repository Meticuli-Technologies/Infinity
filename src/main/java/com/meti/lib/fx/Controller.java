package com.meti.lib.fx;

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
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Controller {
    protected final Singleton<State> state = new Singleton<>();
    private final Map<String, Wizard<?>> wizards = new HashMap<>();
    private final Singleton<Parent> root = new Singleton<>();

    public void onto(URL url) throws IOException {
        Parent parent = ControllerLoader.load(url, state.get());
        state.get().singleContent(StageManager.class).stages.get(0).setScene(new Scene(parent));
    }

    public void addWizard(Wizard<?> wizard) {
        wizards.put(wizard.getName().orElse("null"), wizard);
    }

    public Object loadWizard(String name) {
        if (root.get() == null) {
            throw new IllegalStateException("Root is null, cannot proceed");
        } else {
            Wizard<?> wizard = wizards.get(name);
            wizard.open();

            while (wizard.isRunning()) {
                root.get().setDisable(true);
            }

            root.get().setDisable(false);
            wizard.close();

            return wizard.getResult();
        }
    }

    public void confirm() {
    }

    public Optional<Class<? extends Wizard>> getWizardClass() {
        return Optional.empty();
    }

    public void addAll(Set<Wizard<?>> wizards) {
        wizards.forEach(this::addWizard);
    }
}
