package suite;

import suite.suite.Suite;
import suite.suite.util.Sequence;
import suite.suite.util.Series;

import static suite.suite.$uite.*;

public class Main {

    public static void main(String[] args) {
//        var $ = arm("a", "b", "c").join(arm("a", "d")).join(arm("a", "d", "f"));
        var $ = $$($("a", "b", "c"), $("a", "d", "e"), $("a", "d", "f"));
//        Suite.preDfs($).print();
        $.introspect(s -> s.shift(s.as(String.class).toUpperCase())).print();
        $ = set$(1, 2, 3 ,4 , 5);
        Series.parallel($, $.reverse()).convert(s -> $(s.in().asInt() * s.in(1).asInt())).print();
        $.convert(Suite::inset).set().print();
        Sequence.of(1,2,3).series().convert(Suite::inset).set().print();

        var $2 = $(1);
        var $21 = $2.in();
        if($21.absent(2)) $21.set(2);
        $2.print();
        $2.put(3,4,5).print();

    }
}
