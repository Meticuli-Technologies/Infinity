package com.meti.lib.fx;

import com.meti.lib.module.ModuleManager;
import com.meti.lib.reflect.ClassSource;
import com.meti.lib.state.State;
import com.meti.lib.util.Clause;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
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

    private Set<Wizard> loadController(Controller controller) {
        controller.state.set(state);

        Optional<Class<? extends Wizard>> wizardClass = controller.getWizardClass();
        Set<Wizard> wizards = new HashSet<>();

        if (wizardClass.isPresent()) {
            WizardLoader wizardLoader = new WizardLoader(controller, wizardClass.get());
            wizards = classSources.stream()
                    .flatMap(wizardLoader::load)
                    .collect(Collectors.toSet());
        }

        controller.confirm();
        return wizards;
    }

    private class WizardLoader {
        private final Class<? extends Wizard> wizardClass;
        private final Controller controller;

        public WizardLoader(Controller controller, Class<? extends Wizard> wizardClass) {
            this.wizardClass = wizardClass;
            this.controller = controller;
        }

        private Stream<Wizard> load(ClassSource classSource) {
            return classSource.bySuper(wizardClass)
                    .stream()
                    .map(Clause.wrap(aClass -> aClass.getDeclaredConstructor(State.class).newInstance(state)))
                    .flatMap(Optional::stream)
                    .map(Wizard.class::cast)
                    .peek(controller::addWizard);
        }
    }
}
