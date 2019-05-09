package com.meti;

import java.util.Collection;

public interface SuperSource extends Source {
    Collection<? extends Source> getSubSources();
}
