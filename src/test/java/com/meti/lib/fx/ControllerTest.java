package com.meti.lib.fx;

import com.meti.lib.collect.State;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
@ExtendWith(ApplicationExtension.class)
class ControllerTest {
    @Test
    void construct() throws ExecutionException, InterruptedException {
        State state = new State();
        Stage stage = FXUtil.call(Stage::new).get();
        Controller controller = new Controller(state, stage);
        assertEquals(stage, controller.stage);
        assertEquals(controller.state, state);
    }

    @Test
    void onto() throws ExecutionException, InterruptedException {
        State state = new State();
        AnchorPane anchorPane = new AnchorPane();
        Stage stage = FXUtil.call(Stage::new).get();

        String sampleFXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<?import javafx.scene.layout.AnchorPane?>\n" +
                "<AnchorPane xmlns=\"http://javafx.com/javafx\"\n" +
                "            xmlns:fx=\"http://javafx.com/fxml\"\n" +
                "            prefHeight=\"400.0\" prefWidth=\"600.0\">\n" +
                "</AnchorPane>\n";
        Future<TestController> current = FXUtil.call(() -> {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(sampleFXML.getBytes());
            Controller previous = new Controller(state, stage);

            TestController result = previous.onto(inputStream, inputStream1 -> new FXMLBundle<>(anchorPane, new TestController(state, stage)));

            assertTrue(stage.isShowing());
            assertEquals(anchorPane, stage.getScene().getRoot());
            return result;
        });

        assertNotNull(current);
    }

    private class TestController extends Controller {

        public TestController(State state, Stage stage) {
            super(state, stage);
        }
    }
}
