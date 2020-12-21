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
        var $1 = Suite.set("d");
        var $ = Suite.set("a", Suite.set("b").set("c", $1).set("c1", Suite.set("d").set($1)).set("e", "f"));
        System.out.println(Suite.asString($));
        $.direct() // = "a"
        $.at("a").set("b") // "b"
        $.at("a").at("b").set("c");
        $.at("e").direct() // "f"
        $.get("a", "e").first().direct() // $
        $.up("a").up("b").up("c");
        $.up("a").up("b").up("d");
        $.up("a").up("b").direct(); // "c"
        $.up("a").up("b").get("c", "d");
        Suite.set("a", Suite.set("x").set("y").set("z"));

        $.set("a", Suite.set())
    }
}
