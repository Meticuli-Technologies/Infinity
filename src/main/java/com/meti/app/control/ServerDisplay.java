package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.io.Server;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import java.util.logging.Level;

public class ServerDisplay extends InfinityServerController implements Initializable {
    @FXML
    private Text portText;

    @FXML
    private ListView<String> clientListView;

    public ServerDisplay(State state) {
        super(state);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Future<Server> future = executorServiceManager.service.submit(server.getListener());
        AnimationTimer timer = new ServerDisplayTimer(future);
        timer.start();
    }

    private class ServerDisplayTimer extends AnimationTimer {
        private final Future<Server> future;

        public ServerDisplayTimer(Future<Server> future) {
            this.future = future;
        }

        @Override
        public void handle(long now) {
            if (future.isDone()) {
                handleResult();
            }
        }

        public void handleResult() {
            try {
                future.get();
            } catch (Exception e) {
                console.log(Level.SEVERE, e);
                Platform.exit();
            }
        }
    }
}
