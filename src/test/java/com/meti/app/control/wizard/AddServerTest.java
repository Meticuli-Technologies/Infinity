package com.meti.app.control.wizard;

import com.meti.FXMLTest;
import com.meti.lib.fx.depend.WindowedDependency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/28/2018
 */
class AddServerTest extends FXMLTest<AddServer> {
    @Override
    public URL getFxmlUrl() {
        return getClass().getResource("/com/meti/app/fxml/wizard/AddServer.fxml");
    }

    @Test
    void getDependencyClasses() {
        assertIterableEquals(Stream.of(WindowedDependency.class).collect(Collectors.toSet()), controller.getDependencyClasses());
    }

    @Test
    void cancel() {
    }

    @Test
    void apply() {
    }
}