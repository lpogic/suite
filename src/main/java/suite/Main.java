package suite;

import suite.suite.Suite;
import suite.suite.util.Slime;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.set("a", Suite.set("b", Suite.set("d")));
        System.out.println(Suite.describe($));
        $.inset("c", "d", "e");
        $.at("c").inset("f", "g").set("h").add(Suite.set("i"));
        $.at("c").setBefore("f", "j", Suite.set("k").set("l"));
        System.out.println(Suite.describe($));
        var $1 = $.at("c").take("j");
        $1.at("j").addBefore("l", Suite.set("m"));
        System.out.println(Suite.describe($1.at("j").takeAll(Slime.of("k").andEntire(List.of("l", "m")))));
        System.out.println(Suite.describe($1));
        System.out.println(Suite.describe($, true));
    }
}
