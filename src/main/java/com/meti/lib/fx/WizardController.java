package com.meti.lib.fx;

import com.meti.lib.state.State;
import com.meti.lib.util.Singleton;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public abstract class WizardController<S extends State, W extends Wizard<?>> extends Controller<S> {
    private final Map<String, W> wizards = new HashMap<>();
    private Singleton<Parent> root = new Singleton<>();

    public void add(W wizard) {
        wizards.put(wizard.getName(), wizard);
    }

    public Object load(String name) {
        if (root == null) {
            throw new IllegalStateException("Root is null, cannot proceed");
        } else {
            W wizard = wizards.get(name);
            wizard.open();

            while (wizard.isRunning()) {
                root.get().setDisable(true);
            }

            root.get().setDisable(false);
            wizard.close();

            return wizard.getResult();
        }
    }

    public abstract Class<? extends Wizard> getWizardClass();
}
