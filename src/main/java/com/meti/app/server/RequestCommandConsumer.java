package com.meti.app.server;

import com.meti.app.server.command.RequestCommand;
import com.meti.app.server.command.RequestType;
import com.meti.lib.Requestable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.meti.lib.CollectionsUtil.toSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public class RequestCommandConsumer extends CommandConsumer<Serializable, RequestCommand> {
    private static final Map<Set<RequestType>, Requestable<Server, Serializable>> requestableMap = new HashMap<>();

    static {
        requestableMap.put(toSet(RequestType.LOCATIONS), parameter ->
                new ArrayList<>(parameter.getFiles().orElse(new HashSet<>())
                        .stream()
                        .map(path -> new Location(path.toString()))
                        .collect(Collectors.toList())));
    }

    @Override
    protected Serializable process(RequestCommand command, Server server) {
        Set<RequestType> parameters = Arrays.stream(command.getParameters())
                .filter(serializable -> serializable instanceof RequestType)
                .map(serializable -> (RequestType) serializable)
                .collect(Collectors.toSet());

        return requestableMap.get(parameters).request(server);
    }

    @Override
    protected Class<RequestCommand> getCommandClass() {
        return RequestCommand.class;
    }
}
