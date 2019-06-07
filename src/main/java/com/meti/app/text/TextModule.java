package com.meti.app.text;

import com.meti.lib.module.ClassCollectionModule;

import java.util.Collections;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class TextModule extends ClassCollectionModule {
    public TextModule() {
        super(Set.of(TextEditor.class));
    }
}
