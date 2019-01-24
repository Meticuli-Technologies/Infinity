package com.meti.app;

import com.meti.lib.fx.Controller;
import com.meti.lib.math.Vector;
import com.meti.lib.math.VectorGrid;
import com.meti.lib.tuple.Tuple2;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class InfinityController extends Controller {
    protected VectorGrid<URL> grid = new VectorGrid<>();

    @Override
    public void complete() {
        VectorGrid<?> token = getState().storedElement(VectorGrid.class);
        Map<Vector, ?> map = token.content;
        map.keySet().stream()
                .map(vector -> new Tuple2<>(vector, map.get(vector)))
                .filter((Predicate<Tuple2<Vector, ?>>) tuple -> URL.class.isAssignableFrom(tuple.b.getClass()))
                .map((Function<Tuple2<Vector, ?>, Tuple2<Vector, URL>>) vectorTuple2 -> new Tuple2<>(vectorTuple2.a, (URL) vectorTuple2.b))
                .forEach(vectorURLTuple2 -> grid.content.put(vectorURLTuple2.a, vectorURLTuple2.b));
    }

    public void load(Vector vector) throws IOException {
        load(grid.move(vector));
    }
}
