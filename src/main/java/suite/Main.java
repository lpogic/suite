package suite;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.Sequence;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.setAll(Sequence.of(1, 2, 3));
        var $1 = Suite.setAll(Sequence.of(1)).set(2, $).set(3, $);
        System.out.println(Suite.describe($));
//        System.out.println(Suite.describe($.take(1, 3)));
        System.out.println(Suite.describe($1));
        Suite.dfs($, Subject::reverse);
    }
}
