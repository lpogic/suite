package suite;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.Sequence;
import suite.suite.util.Series;

import static suite.suite.$uite.$$;
import static suite.suite.Suite.*;

public class Main {

    public static void main(String[] args) {
//        var $ = arm("a", "b", "c").merge(arm("a", "d")).merge(arm("a", "d", "f"));
        var $ = merge(arm("a", "b", "c"), arm("a", "d", "e"), arm("a", "d", "f"));
//        Suite.preDfs($).print();
        $.introspect(s -> s.shift(s.as(String.class).toUpperCase())).print();
        $ = set(1, 2, 3 ,4 , 5);
        Series.parallel($, $.reverse()).convert(s -> set(s.in().asInt() * s.in(1).asInt())).print();
        $.convert(Suite::inset).set().print();
        Sequence.of(1,2,3).series().convert(Suite::inset).set().print();
        add(1,2,3).print();
        $$(1,2,3).convert(Suite::inset).set().print();

        var $2 = set(1);
        var $21 = $2.in();
        if($21.absent(2)) $21.set(2);
        $2.print();
        $2.arm(3,4,5).print();

    }
}
