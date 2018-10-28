package com.meti;

import com.meti.app.main.ClientMain;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/28/2018
 */
@ExtendWith(ApplicationExtension.class)
public class ClientTest {
    @Start
    void start(Stage primaryStage) throws IOException {
        ClientMain.loadDependents(primaryStage);
    }
}
