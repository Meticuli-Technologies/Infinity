package com.meti.lib.fx;

import com.meti.lib.state.State;
import com.meti.lib.util.Singleton;
import javafx.scene.Parent;

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
    private Singleton<Parent> root = new Singleton<>();

    public void add(Wizard<?> wizard) {
        wizards.put(wizard.getName().orElse("null"), wizard);
    }

    public Object load(String name) {
        if (root == null) {
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
        wizards.forEach(this::add);
    }
}
