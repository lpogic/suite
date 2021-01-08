package suite;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.Sequence;

public class Main {

    public static void main(String[] args) {
        var $ = Sequence.of("a", "b", "c").set();
        System.out.println(Suite.describe($));
        $.setIf("b", Suite.set("d"), Subject::present);
        System.out.println(Suite.describe($));
        System.out.println(Suite.describe($.sub("b")));
    }
}
