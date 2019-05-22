package com.meti.lib.javafx;

import com.meti.lib.source.Readable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Injector extends FXMLLoader {
    private Injector(Object... dependencies) {
        super(null, null, null, new InjectorFactory(Arrays.asList(dependencies)));
    }

    public static Scene readAsScene(Readable<?> readable, Object... dependencies) throws IOException {
        return new Scene(read(readable, dependencies));
    }

    private static <T> T read(Readable<?> readable, Object... dependencies) throws IOException {
        return new Injector(dependencies).readImpl(readable);
    }

    private <T> T readImpl(Readable<?> readable) throws IOException {
        //TODO: run test if FXMLLoader.readImpl(InputStream stream) closes the stream or not
        return this.load(readable.getInputStream());
    }
}