package suite;

import suite.suite.Suite;
import suite.suite.util.Sequence;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.setAll(Sequence.of(1, 2, 3));
        System.out.println(Suite.describe($));
        System.out.println(Suite.describe($.take(1, 3)));
        System.out.println(Suite.describe($));
    }
}
