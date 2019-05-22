package com.meti.app.core.start;

import com.meti.app.core.state.Toolkit;
import com.meti.lib.javafx.StageManagerImpl;
import javafx.scene.Scene;

import java.io.IOException;

import static com.meti.lib.javafx.Injector.*;
import static com.meti.lib.source.url.URLSource.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Starter implements StarterImpl {
    @Override
    public void start(Toolkit toolkit) throws IOException {
        Scene menuScene = readAsScene(fromResource("/com/meti/app/control/Menu.fxml"), toolkit);
        StageManagerImpl stageManager = toolkit.getStageManager();
        this.loadMenu(menuScene, stageManager);
    }

    private void loadMenu(Scene menuScene, StageManagerImpl stageManager) {
        stageManager.ontoPrimaryStage(menuScene);
    }
}
