package com.meti.lib.fx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class FXWizard<T> extends AbstractWizard<T> {
    private final Stage stage = new Stage();
    private final Parent root;

    public FXWizard(String name, Parent root) {
        super(name);
        this.root = root;
    }

    @Override
    public void open() {
        super.open();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void close() {
        super.close();

        stage.close();
    }
}
