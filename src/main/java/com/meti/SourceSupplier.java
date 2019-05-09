package com.meti;

import java.io.IOException;

public interface SourceSupplier {
    Source accept() throws IOException;
}
