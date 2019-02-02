package com.meti.app.control;

import com.meti.lib.console.Console;
import com.meti.lib.fx.*;
import com.meti.lib.module.ModuleManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    protected FXMLSequencerManager sequencerManager;

    public final void confirm() {
        console = getItem(Console.class);
        properties = getItem(Properties.class);
        stageManager = getItem(StageManager.class);
        moduleManager = getItem(ModuleManager.class);
        sequencerManager = getItem(FXMLSequencerManager.class);

        confirmInfinity();
    }

    public boolean setSequence(String name, int index) {
        return setSequence(sequencerManager.sequences.get(name), index);
    }

    public boolean setSequence(Sequencer<URL> sequencer, int index) {
        Optional<URL> back = sequencer.back(index);
        Optional<URL> next = sequencer.next(index);
        back.ifPresent(backURL::set);
        next.ifPresent(nextURL::set);
        return back.isPresent() && next.isPresent();
    }

    public <T> Optional<T> toBack() {
        URL url = backURL.get();
        if (url == null) {
            console.log(Level.WARNING, "Back URL not set!");
        }
        try {
            return Optional.of(onto(url));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
            return Optional.empty();
        }
    }

    public <T> Optional<T> toNext() {
        URL url = nextURL.get();
        if (url == null) {
            console.log(Level.WARNING, "Next URL not set!");
        }
        try {
            return Optional.of(onto(url));
        } catch (IOException e) {
            console.log(Level.WARNING, e);
            return Optional.empty();
        }
    }

    public void confirmInfinity(){
    }

    public <T> T getItem(Class<T> itemClass) {
        return state.get().singleContent(itemClass);
    }
}
