package com.meti.app.main;

import com.meti.app.client.ClientState;
import com.meti.lib.client.ClientManager;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.ControllerState;
import com.meti.lib.fx.DependencyManager;
import com.meti.lib.fx.depend.WindowedDependency;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ClientMain extends Application {
    public static final Logger logger = LoggerFactory.getLogger(ClientManager.class);
    public static final ClientState clientState = new ClientState();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DependencyManager defaultDependencyManager = new DependencyManager();
        defaultDependencyManager.put(WindowedDependency.class, new WindowedDependency());

        ControllerLoader.defaultDependencyManager = defaultDependencyManager;
        ControllerLoader.defaultControllerState = ControllerState.of(primaryStage);

        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/fxml/ServerView.fxml"))));
        primaryStage.show();
    }
}

/*        //TODO: place as "Scan for Local Servers" in Client
        try {
            List<InetAddress> addressList = new ArrayList<>();
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                networkInterface.getInterfaceAddresses().stream()
                        .map(InterfaceAddress::getBroadcast)
                        .filter(Objects::nonNull)
                        .forEach(addressList::add);
            }

            addressList.stream().forEach(new Consumer<InetAddress>() {
                @Override
                public void accept(InetAddress inetAddress) {
                    //TODO: add timeout / handler
                }
            });
        } catch (SocketException e) {
            e.printStackTrace();
        }*/