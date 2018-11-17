package com.meti.app.evaluate;

import com.meti.lib.net.command.GetCommand;
import com.meti.lib.net.server.evaluate.AbstractEvaluatable;
import com.meti.lib.net.server.evaluate.Evaluator;
import com.meti.lib.util.ParameterizedMap;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
@Evaluator
public class GetCommandEvaluatable extends AbstractEvaluatable<GetCommand> {
    private final ParameterizedMap<Serializable, Supplier<Serializable>> map = new ParameterizedMap<>();

    {
        map.put(() -> new ArrayList<>(server.getFiles().stream().map(Path::toString).collect(Collectors.toList())), "files");
        map.put(() -> server.getFileDirectory().toString(), "fileDirectory");
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
        List<Serializable> list = map.getFromParametersNonGeneric(obj.parameters, Serializable.class)
                .stream()
                .map(Supplier::get)
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
