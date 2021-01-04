package suite;

import suite.suite.selector.AtIndex;
import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.set("a", Suite.set("b", Suite.set("d")));
        System.out.println(Suite.describe($));
        $.inset("c", "d", "e");
        System.out.println(Suite.describe($));
    }
}
