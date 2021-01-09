package suite;

import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.set();
        System.out.println(Suite.describe($));
        $.in("A").in().in("B").set("C");
        System.out.println(Suite.describe($));
    }
}
