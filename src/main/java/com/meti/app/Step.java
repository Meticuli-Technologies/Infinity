package com.meti.app;

import com.meti.control.ContinuedController;
import com.meti.control.ControllerLoader;
import com.meti.control.depend.exception.ExceptionDependency;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/7/2018
 */
public class Step extends ContinuedController {
    public URL pre_first;
    public URL post_last;

    public URL[] steps;

    private int index;

    public static void setEnds(URL pre_first, URL post_last, Step step){
        step.pre_first = pre_first;
        step.post_last = post_last;
    }

    @FXML
    private BorderPane contentPane;

    @FXML
    public void back() {
        Objects.requireNonNull(pre_first);

        try {
            index--;

            if (index == -1) {
                load(pre_first);
            } else {
                loadCenter();
            }
        } catch (IOException e) {
            getDependency(ExceptionDependency.class).accept(e);
        }
    }

    private void loadCenter() throws IOException {
       loadStep(index);
    }

    private void loadStep(int index) throws IOException {
        contentPane.setCenter(ControllerLoader.load(steps[index]));
    }

    @FXML
    public void next() {
        Objects.requireNonNull(post_last);

        try {
            index++;

            if (index == steps.length) {
                load(post_last);
            } else {
                loadCenter();
            }
        } catch (IOException e) {
            getDependency(ExceptionDependency.class).accept(e);
        }
    }

    @Override
    public String[] getResourcesStrings() {
        return new String[0];
    }
}
