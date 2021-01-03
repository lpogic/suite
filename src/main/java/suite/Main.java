package suite;

import suite.suite.selector.AtIndex;
import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var sub = Suite.set();
        sub.set("a").set("b").setBefore(sub.select(new AtIndex(1)).direct(),"b.a");
        System.out.println(Suite.describe(sub));
        sub.setBefore(sub.reverse().select(s -> s.as(String.class).startsWith("b")).direct(), "c");
        System.out.println(Suite.describe(sub));
        sub.setBefore(sub.select(s -> s.instanceOf(String.class)).select(new AtIndex(1)).direct(), "f");
        System.out.println(Suite.describe(sub));
        System.out.println("unsetting");
        for (var s : sub) {
            System.out.println("s: " + Suite.describe(s));
            if(s.direct() == "a") {
                sub.unset().set("A").set("B").set("C");
            }
            System.out.println(Suite.describe(sub));
        }

        System.out.println("end");
        System.out.println(Suite.describe(sub));
        sub.join(Suite.set("B", Suite.set("b")).set("A", Suite.set("a")).set("C", Suite.set("c")));
        System.out.println(Suite.describe(sub));
        var $1 = Suite.set("d");
        var $ = Suite.set("a", Suite.set("b").set("c", $1).set("c1", Suite.set("d").set("h", Suite.set("i").set("j")).
                set($1)).set("e", Suite.set("f")).set("g").set("k", Suite.set("l", Suite.set("m").set("n"))));
        System.out.println("\n\n");
        System.out.println(Suite.describe($));
        System.out.println(Suite.describe($, true));
        for(var ss : Suite.dfs($)) {
            System.out.println(">".repeat(20));
            System.out.println(Suite.describe(ss));
        }
    }
}
