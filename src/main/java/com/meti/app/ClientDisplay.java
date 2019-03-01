package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;
import com.meti.lib.net.Client;
import com.meti.lib.net.Command;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class ClientDisplay extends Controller  {
    @FXML
    private TextField input;

    @FXML
    private TextArea output;

    private Client client;

    public ClientDisplay(State state) {
        super(state);
    }

    public void load(Socket socket) {
        try {
            this.client = new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Client> getClient() {
        return Optional.ofNullable(client);
    }

    @FXML
    public void handleInput(KeyEvent event){
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                processToken(input.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void processToken(String line) throws IOException {
        Command command = new Comand("chat", "add", line);
        client.write(command);
        client.flush();
    }
}
