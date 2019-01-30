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
    protected Console console;
    protected Properties properties;

    @Override
    public void confirm() {
        console = getItem(Console.class);
        properties = getItem(Properties.class);
    }

    public <T> T getItem(Class<T> itemClass) {
        return state.get().singleContent(itemClass);
    }
}
