package com.meti.app.core.start;

import com.meti.app.core.state.Toolkit;
import com.meti.lib.javafx.Injector;
import com.meti.lib.source.URLSource;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Starter implements StarterImpl {
    @Override
    public void start(Toolkit toolkit) throws IOException {
        Injector.read(URLSource.fromResource("/com/meti/app/control/Menu.fxml"));
       /* toolkit.getStageManager();*/
    }
}
