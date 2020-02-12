package block3.cp.mapreduce;

import java.util.List;

public abstract class MapReduceBase<I, O, R> {
    protected abstract O map(I in);
    protected abstract R reduce(List<O> in);

    public R mapReduce(List<I> values) {
        //Concurrent implementation
        throw new UnsupportedOperationException("TODO");
    }
}
