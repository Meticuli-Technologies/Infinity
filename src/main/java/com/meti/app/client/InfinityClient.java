package com.meti.app.client;

import com.meti.lib.handle.Handler;
import com.meti.lib.handle.HandlerHopper;
import com.meti.lib.handle.MappedHandler;
import com.meti.lib.handle.MappedHandlerImpl;
import com.meti.lib.net.Listener;
import com.meti.lib.source.ReadableSource;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.meti.lib.concurrent.ExecutorUtil.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class InfinityClient extends HandlerHopper<ReadableSource<ObjectInputStream>> implements Closeable, Listener {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(4);
    private final MappedHandlerImpl<Object, ReadableSource<ObjectInputStream>> handler;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final Duration timeout;

    public InfinityClient(ReadableSource<ObjectInputStream> readable) {
        this(readable, new MappedHandler<>(), DEFAULT_TIMEOUT);
    }

    private InfinityClient(ReadableSource<ObjectInputStream> readable, MappedHandlerImpl<Object, ReadableSource<ObjectInputStream>> handler, Duration timeout) {
        super(readable, handler);
        this.handler = handler;
        this.timeout = timeout;
    }

    public void add(Handler<Object, ReadableSource<ObjectInputStream>> subHandler) {
        handler.add(subHandler);
    }

    @Override
    public void listen() {
        service.submit(this);
    }

    @Override
    public void close() throws IOException {
        readable.close();
        terminate(service, timeout);
    }
}
