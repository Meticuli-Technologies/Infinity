package com.meti.app.client;

import com.meti.app.server.MappedHandlerProvider;
import com.meti.lib.handle.Handler;
import com.meti.lib.handle.HandlerHopper;
import com.meti.lib.handle.MappedHandler;
import com.meti.lib.handle.MappedHandlerImpl;
import com.meti.lib.source.ObjectImpl;
import com.meti.lib.source.ObjectSourceImpl;
import com.meti.lib.source.ReadableSource;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.meti.lib.concurrent.ExecutorUtil.terminate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class InfinityClient extends HandlerHopper<ReadableSource<ObjectInputStream>> implements ClientImpl, MappedHandlerProvider {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(4L);
    private final ObjectImpl implementation;

    private final MappedHandlerImpl<Object, ReadableSource<ObjectInputStream>> handler;
    private final ExecutorService service = Executors.newSingleThreadExecutor();
    private final Duration timeout;

    public InfinityClient(ReadableSource<ObjectInputStream> objectSource, ObjectImpl implementation) {
        this(objectSource, implementation, new MappedHandler<>(), DEFAULT_TIMEOUT);
    }

    private InfinityClient(ReadableSource<ObjectInputStream> objectSource, ObjectImpl implementation, MappedHandlerImpl<Object, ReadableSource<ObjectInputStream>> handler, Duration timeout) {
        super(objectSource, handler);
        this.implementation = implementation;
        this.handler = handler;
        this.timeout = timeout;
    }

    public static ClientImpl build(ObjectSourceImpl source, boolean shared) throws IOException {
        return new InfinityClient(source, source.getImplementation(shared));
    }

    @Override
    public void close() throws IOException {
        readable.close();
        terminate(service, timeout);
    }

    @Override
    public void flush() throws IOException {
        implementation.flush();
    }

    @Override
    public MappedHandlerImpl<Object, ? extends ReadableSource<ObjectInputStream>> getHandler() {
        return handler;
    }

    @Override
    public Object read() throws IOException, ClassNotFoundException {
        return implementation.read();
    }

    @Override
    public void write(Serializable serializable) throws IOException {
        implementation.write(serializable);
    }

    @Override
    public void add(Handler<Object, ReadableSource<ObjectInputStream>> subHandler) {
        handler.add(subHandler);
    }

    @Override
    public void listen() {
        service.submit(this);
    }
}
