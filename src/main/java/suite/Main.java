package suite;

import suite.suite.Slot;
import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var sub = Suite.set("a").set("b").setAt(Slot.in(1), "b.a");
        System.out.println(sub);
        sub.setAt(Slot.before(sub.reverse().select(s -> s.asString().startsWith("b")).get().key()), "c");
        System.out.println(sub);
        sub.setAt(Slot.after(sub.select(s -> s.instanceOf(String.class)).get().key()), "f");
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
        System.out.println(sub);
        sub.inset(Suite.set("B", "b").set("A", "a").set("C", "c"));
        System.out.println(sub);
    }
}
