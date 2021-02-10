package suite;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.Sequence;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.setUp("a", "b", "c").setUp("a", "d", "e").setUp("a", "d", "f");
//        Suite.preDfs($).print();
        Suite.refactor($, s -> s.shift(s.direct(), s.as(String.class).toUpperCase())).print();
        $ = Suite.setAll(Sequence.of(1, 2, 3 ,4 , 5));
        Suite.parallel($, $.reverse()).convert(s -> Suite.set(s.at(0).as(Integer.class) * s.at(1).as(Integer.class))).print();
    }
}
