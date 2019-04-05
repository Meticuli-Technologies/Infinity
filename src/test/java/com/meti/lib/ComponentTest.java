package com.meti.lib;

import com.meti.lib.event.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class ComponentTest {
    @Test
    void construct(){
        Component<Enum<?>, Event> component = new Component<>();
        assertNotNull(component.eventManager);
    }

}
