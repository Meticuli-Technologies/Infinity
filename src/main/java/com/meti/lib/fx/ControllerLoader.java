package com.meti.lib.fx;

import com.meti.lib.module.ModuleManager;
import com.meti.lib.reflect.ClassSource;
import com.meti.lib.state.State;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class ControllerLoader extends FXMLLoader {
    private final State state;
    private final ClassSource[] classSources;

    public ControllerLoader(URL location, State state, ClassSource... classSources) {
        super(location);
        this.state = state;
        this.classSources = classSources;
    }

    public static <T> T load(URL location, State state) throws IOException {
        return load(location, state, state.singleContent(ModuleManager.class)
                .modules
                .values()
                .stream()
                .map(module -> module.source)
                .distinct()
                .toArray(ClassSource[]::new)
        );
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
            controller.state.set(state);

            if (classSources.length != 0) {
                Optional<Class<? extends Wizard>> wizardClass = controller.getWizardClass();
                for (ClassSource classSource : classSources) {
                    wizardClass.ifPresent(aClass -> loadWizards(controller, aClass, classSource));
                }
            }

            controller.confirm();
        }

        return parent;
    }

    private void loadWizards(Controller controller, Class<? extends Wizard> wizardClass, ClassSource classSource) {
        Set<Class<?>> filtered = filterForWizards(wizardClass, classSource);

        Set<Wizard> wizards = filtered.stream()
                .map(aClass -> {
                    try {
                        Object o = aClass.getDeclaredConstructor().newInstance();
                        Object wizardToken = aClass.getMethod("loadModules", state.getClass()).invoke(o, state);
                        return Optional.ofNullable(wizardToken);
                    } catch (Exception e) {
                        return Optional.empty();
                    }
                })
                .flatMap(Optional::stream)
                .filter(o -> Wizard.class.isAssignableFrom(o.getClass()))
                .map(Wizard.class::cast)
                .collect(Collectors.toSet());

        wizards.forEach(controller::addWizard);
    }

    private Set<Class<?>> filterForWizards(Class<? extends Wizard> wizardClass, ClassSource classSource) {
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
