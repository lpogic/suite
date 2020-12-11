package suite;

import suite.suite.Slot;
import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var sub = Suite.set("a").set("b").setAt(Slot.in(1), "b.a");
        System.out.println(sub);
        sub.setAt(Slot.recentBefore(s -> s.asString().startsWith("b")), "c");
        System.out.println(sub);
        sub.setAt(Slot.recentBefore(s -> true), "d");
        System.out.println(sub);
        sub.setAt(Slot.primeAfter(s -> false), "e");
        sub.setAt(Slot.primeAfter(s -> s.instanceOf(String.class)), "f");
        System.out.println(sub);
        System.out.println("unsetting");
        for (var s : sub) {
            System.out.println("s: " + s);
            if(s.direct() == "a") {
                sub.unset().set("A").set("B").set("C");
            }
            System.out.println(sub);
        }

        System.out.println("end");
    }
}
