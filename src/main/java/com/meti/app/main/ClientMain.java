package com.meti.app.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class ClientMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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