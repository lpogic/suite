package lpo;

import lpo.suite.Slot;
import lpo.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var s = Suite.set("a").set("b").setAt(Slot.in(1), "b.a");
        System.out.println(s);
    }
}
