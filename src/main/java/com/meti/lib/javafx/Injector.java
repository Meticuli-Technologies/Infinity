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

    public static <T> T read(Readable<?> readable, List<Object> dependencies) throws IOException {
        return new Injector(dependencies).read(readable);
    }

    public static <T> T read(Readable<?> readable) throws IOException {
        //TODO: run test if FXMLLoader.read(InputStream stream) closes the stream or not
        return this.load(readable.getInputStream());
    }
}