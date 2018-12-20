import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(new AnchorPane()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
