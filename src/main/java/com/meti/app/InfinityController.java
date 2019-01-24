package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.math.MapNode;
import com.meti.lib.math.VectorGrid;
import com.meti.lib.math.VectorNode;
import com.meti.lib.tuple.Tuple2;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class InfinityController extends Controller implements MapNode<> {
    protected VectorGrid<> grid = new VectorGrid<>(current);

    @Override
    public void complete() {

    }

    public void load(int[] vector) throws IOException {
        load(grid.move(vector));
    }
}
