package com.meti.app.control;

import com.meti.lib.console.Console;
import com.meti.lib.fx.Controller;

import java.util.Properties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
public class InfinityController extends Controller {
    public Console getConsole() {
        return state.get().singleContent(Console.class);
    }

    public Properties getProperties() {
        return state.get().singleContent(Properties.class);
    }
}
