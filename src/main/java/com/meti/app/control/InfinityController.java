package com.meti.app.control;

import com.meti.lib.console.Console;
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
public class InfinityController extends Controller {
    public final ObjectProperty<URL> backURLProperty = new SimpleObjectProperty<>();

    protected Console console;
    protected Properties properties;
    protected StageManager stageManager;
    protected ModuleManager moduleManager;

    public void ontoBack() {
        URL url = backURLProperty.get();
        if (url == null) {
            console.log(Level.WARNING, "Back URL not set!");
        }
        try {
            onto(url);
        } catch (IOException e) {
            console.log(Level.WARNING, e);
        }
    }

    @Override
    public void confirm() {
        console = getItem(Console.class);
        properties = getItem(Properties.class);
        stageManager = getItem(StageManager.class);
        moduleManager = getItem(ModuleManager.class);
    }

    public <T> T getItem(Class<T> itemClass) {
        return state.get().singleContent(itemClass);
    }
}
