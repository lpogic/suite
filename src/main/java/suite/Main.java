package suite;


import suite.suite.Suite;
import suite.suite.util.Sequence;
import suite.suite.util.Series;

import static suite.suite.$uite.*;

public class Main {

    public static void main(String[] args) {
//        var $ = join$($("a", $("b", $("c"))), $("a", $("d", $("e"))), $("a", $("d", $("f"))));
//        var $ = join$(Suite.put("a", "b", "c"), Suite.put("a", "d", "e"), Suite.put("a", "d", "f"));
//        $.introspect(s -> s.shift(s.as(String.class).toUpperCase())).print();
//        $ = $(1, 2, 3 ,4, 5);
//        Series.parallel($, $.reverse()).convert(s -> $(s.in().asInt() * s.at(1).asInt())).print();
//        $.convert(Suite::inset).set().print();
//        Sequence.of(1,2,3).series().convert(Suite::inset).set().print();
//
//        var $2 = $uite.set$(1);
//        var $21 = $2.in();
//        if($21.absent(2)) $21.set(2);
//        $2.print();
//        $2.put(3,4,5).print();

//        var $3 = $(1, 2, 3, 4, 5);
//        $3.print();
//        $3.aimedSet($3.front(2).select(2).raw(), 3.5);
//        $3.print();

        var i = $(1);
        var d = Series.pull(() -> $(true)).first(3);
        d.print();
    }
}
