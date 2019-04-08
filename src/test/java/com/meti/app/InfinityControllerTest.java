package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.log.LoggerConsole;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InfinityControllerTest {
@Test
    void constructEmptyState(){
    assertThrows(NoSuchElementException.class, ()->new InfinityController(new State()));
}

@Test
    void construct(){
    LoggerConsole console = Mockito.mock(LoggerConsole.class);
    TryableFactory factory = Mockito.mock(TryableFactory.class);
/*    new InfinityController(new State());*/
}
}