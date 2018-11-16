package com.meti.app;

import com.meti.lib.net.command.GetCommand;
import com.meti.lib.net.server.evaluate.AbstractEvaluatable;
import com.meti.lib.net.server.evaluate.Evaluator;
import com.meti.lib.util.StreamUtil;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
@Evaluator
public class GetCommandEvaluatable extends AbstractEvaluatable<GetCommand> {
    private final Map<Set<?>, Supplier<? extends Serializable>> map = new HashMap<>();

    {
        map.put(StreamUtil.asSet("files"), () -> new ArrayList<>(server.getFiles().stream().map(path -> path.toString()).collect(Collectors.toList())));
        map.put(StreamUtil.asSet("fileDirectory"), () -> server.getFileDirectory().toString());
    }

    public Class<GetCommand> getParameterClass() {
        return GetCommand.class;
    }

    @Override
    public boolean canEvaluate(Object obj) {
        return GetCommand.class.isAssignableFrom(obj.getClass());
    }

    @Override
    public Serializable evaluate(GetCommand obj) {
        List<? extends Serializable> list = map.keySet().stream()
                .filter(objects -> objects.size() == obj.parameters.size() && objects.containsAll(obj.parameters))
                .map((Function<Set<?>, Supplier<? extends Serializable>>) map::get)
                .map((Function<Supplier<? extends Serializable>, Serializable>) Supplier::get)
                .collect(Collectors.toList());

        if (list.size() == 0) {
            throw new IllegalArgumentException("No results found");
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            return new ArrayList<>(list);
        }
    }
}
