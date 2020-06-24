package suite;

import suite.suite.Slot;
import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var s = Suite.set("a").set("b").setAt(Slot.in(1), "b.a");
        System.out.println(s);
        System.out.println(Suite.from(s).get("X").or("b.a", s1 -> Suite.set(s1.asString().toUpperCase())).direct());
        Suite.from(s).get("size").map(Number.class, Number::doubleValue).orGiven(9.0);
    }
}
