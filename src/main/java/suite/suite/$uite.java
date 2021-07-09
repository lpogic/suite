package suite.suite;

import suite.suite.action.Action;
import suite.suite.action.Expression;
import suite.suite.action.Statement;
import suite.suite.util.*;

import java.util.function.Function;

public class $uite extends SolidSubject {

    public static Subject $() {
        return new SolidSubject();
    }

    public static Subject $(Object o) {
        var $ = $();
        if(o instanceof Subject $o) {
            $.inset($o);
        } else {
            $.set(o);
        }
        return $;
    }

    public static Subject $(Action a) {
        var $ = $();
        $.set(a);
        return $;
    }

    public static Subject $(Statement s) {
        var $ = $();
        $.set(s);
        return $;
    }

    public static Subject $(Expression e) {
        var $ = $();
        $.set(e);
        return $;
    }

    public static Subject $(Object o0, Object ... o) {
        boolean lastSubject = o0 instanceof Subject;
        Subject $;
        Object ol;
        if(lastSubject) {
            $ = $(o0);
            ol = null;
        } else {
            $ = $();
            ol = o0;
        }
        for(var oi : o) {
            if (lastSubject) {
                if (oi instanceof Subject $i) {
                    $.inset($i);
                    lastSubject = true;
                } else {
                    ol = oi;
                    lastSubject = false;
                }
            } else {
                if (oi instanceof Subject $i) {
                    $.inset(ol, $i);
                    lastSubject = true;
                } else {
                    $.set(ol);
                    ol = oi;
                    lastSubject = false;
                }
            }
        }
        if(!lastSubject) $.set(ol);
        return $;
    }

    public static Subject join$(Object o) {
        var $ = $();
        if(o instanceof Subject $s) {
            $.merge($s);
        } else {
            $.put(o);
        }
        return $;
    }
    public static Subject join$(Object ... o) {
        var $ = $();
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
