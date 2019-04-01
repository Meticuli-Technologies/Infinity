package com.meti.app.core;

import com.meti.lib.collection.State;
import com.meti.lib.log.Console;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static com.meti.lib.fx.ControllerLoader.load;
import static com.meti.lib.trys.TryableFactory.DEFAULT_FACTORY;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Infinity {
    private final ExecutorService service = Executors.newCachedThreadPool();
    private final Console console = new Console();
    private final State state = new State();

    public void start(Stage primaryStage){
        console.log(Level.INFO, "Setting up state.");
        state.add(primaryStage);
        state.add(service);
        state.add(console);

        try {
            console.log(Level.INFO, "Loading Menu.");
            Scene scene = new Scene(load(getClass().getResource("/com/meti/app/control/Menu.fxml"), state));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            console.log(Level.SEVERE, "Failed to start Infinity.", e);
        }
    }

    public void stop(){
        try {
            state.byClass(Closeable.class)
                    .forEach(DEFAULT_FACTORY.newConsumer(Closeable::close));
            DEFAULT_FACTORY.catcher.throwAll();

            service.shutdown();
            if(!service.isTerminated()){
                List<Runnable> runnables = service.shutdownNow();
                String runnableList = runnables
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining("\n\t"));

                if(runnables.isEmpty()){
                    console.log(Level.INFO, "Shutdown server successfully.");
                }
                else{
                    console.log(Level.SEVERE, runnables.size() + " runnables were still running:\n\t" + runnableList);
                }
            }
        } catch (Exception e) {
            console.log(Level.SEVERE, "Failed to stop Infinity.", e);
        }
    }
}
