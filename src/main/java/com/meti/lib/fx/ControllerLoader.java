package com.meti.lib.fx;

import com.meti.lib.clazz.ClassSource;
import com.meti.lib.state.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.meti.lib.util.Clause.wrap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class ControllerLoader extends FXMLLoader {
    private final State state;
    private final ClassSource classSource;

    public ControllerLoader(URL location, State state, ClassSource classSource) {
        super(location);
        this.state = state;
        this.classSource = classSource;
    }

    public static <T> T load(URL location, State state) throws IOException {
        return load(location, state, null);
    }

    public static <T> T load(URL location, State state, ClassSource classSource) throws IOException {
        return new ControllerLoader(location, state, classSource).load();
    }

    @Override
    public <T> T load() throws IOException {
        T parent = super.load();

        Object controllerToken = getController();
        if (controllerToken instanceof Controller) {
            Controller controller = (Controller) controllerToken;
            controller.state.set(state);

            if (classSource != null) {
                Optional<Class<? extends Wizard>> wizardClass = controller.getWizardClass();
                wizardClass.ifPresent(aClass -> loadWizards(controller, aClass));
            }
        }

        return parent;
    }

    private void loadWizards(Controller controller, Class<? extends Wizard> wizardClass) {
        Set<Class<?>> filtered = filterForWizards(wizardClass);

        Set<Wizard> wizards = filtered.stream()
                .map(wrap(aClass -> aClass.getDeclaredConstructor(state.getClass())))
                .flatMap(Optional::stream)
                .map(wrap(constructor -> constructor.newInstance(state)))
                .flatMap(Optional::stream)
                .filter(o -> Wizard.class.isAssignableFrom(o.getClass()))
                .map(Wizard.class::cast)
                .collect(Collectors.toSet());

        wizards.forEach(controller::add);
    }

    private Set<Class<?>> filterForWizards(Class<? extends Wizard> wizardClass) {
        Set<Class<?>> classes = classSource.bySuper(wizardClass);
        Set<Class<?>> filtered = classes.stream()
                .filter(wizardClass::isAssignableFrom)
                .collect(Collectors.toSet());

        if (classes.size() != filtered.size()) {
            throw new IllegalStateException("Classes found do not equal classes filtered");
        }
        return filtered;
    }
}
