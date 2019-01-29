package com.meti.app;

import com.meti.lib.fx.FXWizard;
import com.meti.lib.net.Client;
import javafx.scene.Parent;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class ConnectionFinderWizard extends FXWizard<Client> {
    public ConnectionFinderWizard(String name, Parent root) {
        super(name, root);
    }
}
