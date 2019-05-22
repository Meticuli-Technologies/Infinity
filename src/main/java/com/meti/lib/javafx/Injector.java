package com.meti.lib.javafx;

import com.meti.lib.source.Readable;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Injector extends FXMLLoader {
    public Injector(List<Object> dependencies) {
        super(null, null, new InjectorFactory(dependencies));
    }

    public void read(Readable<?> readable) throws IOException {
        //TODO: run test if FXMLLoader.load(InputStream stream) closes the stream or not
        this.load(readable.getInputStream());
    }
}