package suite.suite;

import suite.suite.util.*;

import java.util.function.Function;

public class $uite extends SolidSubject {

    public static Subject set$() {
        return new SolidSubject();
    }
    public static Subject set$(Object o) {
        var $ = set$();
        if(o instanceof Series series) {
            $.alter(series);
        } else {
            $.put(o);
        }
        return $;
    }
    public static Subject set$(Object ... o) {
        var $ = set$();
        for(var io : o) {
            if(io instanceof Series series) {
                $.alter(series);
            } else {
                $.put(io);
            }
        }
        return $;
    }
    public static Subject add$() {
        return set$();
    }
    public static Subject add$(Object ... o) {
        var $ = set$();
        for(var io : o) {
            if(io instanceof Subject $s) {
                $.inset($s);
            } else {
                $.inset(new SolidSubject(new BasicSubject(io)));
            }
        }
        return $;
    }
    public static Subject $() {
        return new SolidSubject();
    }
    public static Subject $(Object o) {
        return set$(o);
    }
    public static Subject $(Object ... o) {
        if(o.length == 0) return $();
        var $ = $(o[o.length - 1]);
        for(int i = o.length - 2; i >= 0; --i) {
            $ = new SolidSubject(new MonoSubject(o[i], $));
        }
        return $;
    }
    public static Subject join$(Object o) {
        var $ = set$();
        if(o instanceof Subject $s) {
            $.merge($s);
        } else {
            $.put(o);
        }
        return $;
    }
    public static Subject join$(Object ... o) {
        var $ = set$();
        for(var io : o) {
            if(io instanceof Subject $s) {
                $.merge($s);
            } else {
                $.put(io);
            }
        }
        return $;
    }

//    public static Subject fuse(Subject sub) {
//        if(sub == null) {
//            return Suite.inset();
//        } else if(sub.fused()) {
//            return sub;
//        } else {
//            return new SolidSubject(new FuseSubject(sub));
//        }
//    }

    public static Subject thready$() {
        return new ThreadySubject();
    }

//    public static Subject wonky() {
//        return new WonkySubject();
//    }

    public static Query from$(Subject $) {
        return new Query($);
    }

    public static Subject zip$(Iterable<Object> keys, Iterable<Object> values) {
        return Series.engage(keys, values).set();
    }

    public static String toString$(Series series) {
        return Suite.toString(series);
    }

    public static String toString$(Subject $sub) {
        return Suite.toString($sub);
    }

    public static String toString$(Subject $sub, boolean pack, Function<Object, String> encoder) {
        return Suite.toString($sub, pack, encoder);
    }

    public static String toString$(Subject $sub, boolean pack, Function<Object, String> encoder, boolean compress) {
        return Suite.toString($sub, pack, encoder, compress);
    }

    public static Series postDfs$(Subject $sub) {
        return Suite.postDfs($sub);
    }

    public static Series postDfs$(Subject $sub, Function<Subject, Series> serializer) {
        return Suite.postDfs($sub, serializer);
    }

    public static Series preDfs$(Subject $sub) {
        return Suite.preDfs($sub);
    }

    public static Series preDfs$(Subject $sub, Function<Subject, Series> serializer) {
        return Suite.preDfs($sub, serializer);
    }

    public static Series bfs$(Subject $sub) {
        return Suite.bfs($sub);
    }

    public static Series bfs$(Subject $sub, Function<Subject, Series> serializer) {
        return Suite.bfs($sub, serializer);
    }
}
