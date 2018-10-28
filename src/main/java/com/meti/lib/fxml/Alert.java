package com.meti.lib.fxml;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/28/2018
 */
public class Alert {
    @FXML
    private Text titleText;

    @FXML
    private TextArea contentText;

    public String log(String title, Exception exception) {
        String prevTitle = setTitle(title);
        String content = setContent(exception);
        return prevTitle + "\n" + content;
    }

    public String setTitle(String title) {
        String previousTitle = titleText.getText();
        titleText.setText(title);
        return previousTitle;
    }

    public String setContent(Exception exception) {
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        return setContent(writer.toString());
    }

    public String setContent(String content) {
        String previousText = contentText.getText();
        contentText.setText(content);
        return previousText;
    }
}
