package com.meti.app.core.start;

import com.meti.app.core.state.Toolkit;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface StarterImpl {
    void start(Toolkit toolkit) throws IOException;
}
