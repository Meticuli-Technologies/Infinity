package com.meti.app.client;

import com.meti.app.Controls;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class AdvancedController {
    private final Controls controls;

    public AdvancedController(Controls controls) {
        this.controls = controls;
    }

    protected Controls getControls() {
        return controls;
    }
}