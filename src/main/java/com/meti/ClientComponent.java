package com.meti;

import java.util.Collection;

public interface ClientComponent {
    Collection<? extends TokenHandler> getHandlers();
}
