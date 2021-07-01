package suite.graph;

import suite.suite.Subject;
import suite.suite.util.Series;

import static suite.suite.$uite.$;

public class Graph {
    Subject $pool;

    public Graph(Subject $pool) {
        this.$pool = $pool;
    }

    public Graph() {
        this($());
    }

    public void link(Object a, Object b) {
        $pool.in(a).set(b);
        $pool.in(b).set(a);
    }

    public void unlink(Object a, Object b) {
        $pool.in(a).unset(b);
        $pool.in(b).unset(a);
    }

    public void linkAll(Series $a, Series $b) {
        for (var a : $a.eachRaw()) {
            for (var b : $b.eachRaw()) {
                link(a, b);
            }
        }
    }

    public Subject getLinked(Object o) {
        return $pool.in(o).get();
    }

    public void detach(Object o) {
        for(var i : getLinked(o).eachRaw()) {
            $pool.in(i).unset(o);
        }
        $pool.unset(o);
    }
}
