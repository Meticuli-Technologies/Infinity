package com.meti.app.control;

import com.meti.lib.console.Console;
import com.meti.lib.fx.Confirmable;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.StageManager;
import com.meti.lib.module.ModuleManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
public class InfinityController extends Controller implements Confirmable {
    public final ObjectProperty<URL> backURL = new SimpleObjectProperty<>();
    public final ObjectProperty<URL> nextURL = new SimpleObjectProperty<>();

    protected Console console;
    protected Properties properties;
    protected StageManager stageManager;
    protected ModuleManager moduleManager;

    public void toBack() {
        URL url = backURL.get();
        if (url == null) {
            console.log(Level.WARNING, "Back URL not set!");
        }
        try {
            onto(url);
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    public void toNext() {
        URL url = nextURL.get();
        if (url == null) {
            console.log(Level.WARNING, "Next URL not set!");
        }
        try {
            onto(url);
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    public final void confirm() {
        console = getItem(Console.class);
        properties = getItem(Properties.class);
        stageManager = getItem(StageManager.class);
        moduleManager = getItem(ModuleManager.class);

        confirmInfinity();
    }

    public void confirmInfinity(){

    }

    public <T> T getItem(Class<T> itemClass) {
        return state.get().singleContent(itemClass);
    }
}
