package suite;


import static suite.suite.$uite.*;

public class Main {

    public static void main(String[] args) {
//        var $ = set$(arm$("a", "b", "c"), arm$("a", "d", "e"), arm$("a", "d", "f"));
//        $.introspect(s -> s.shift(s.as(String.class).toUpperCase())).print();
//        $ = set$(1, 2, 3 ,4 , 5);
//        Series.parallel($, $.reverse()).convert(s -> $uite.set$(s.in().asInt() * s.in(1).asInt())).print();
//        $.convert(Suite::inset).set().print();
//        Sequence.of(1,2,3).series().convert(Suite::inset).set().print();
//
//        var $2 = $uite.set$(1);
//        var $21 = $2.in();
//        if($21.absent(2)) $21.set(2);
//        $2.print();
//        $2.put(3,4,5).print();

        var $ = add$(1, 2, $('a', 'b'), 3);
        $.at(2).print();
        System.out.println($.at(2).raw());
    }
}
