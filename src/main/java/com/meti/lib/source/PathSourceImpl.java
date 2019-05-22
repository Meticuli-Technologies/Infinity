package com.meti.lib.source;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface PathSourceImpl extends Readable<InputStream>, Writable<OutputStream>, ParentSource<PathSourceImpl> {
    Path getPath();
}
