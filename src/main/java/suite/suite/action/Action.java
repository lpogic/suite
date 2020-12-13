package suite.suite.action;

import suite.suite.Suite;
import suite.suite.Vendor;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Action {

    default Vendor play() {
        return play(Suite.set());
    }

    Vendor play(Vendor in);

    default Vendor gamble() throws Exception {
        return play(Suite.set());
    }

    default Vendor gamble(Vendor in) throws Exception {
        return play(in);
    }

    static<T, U> Action wrap(Function<T, U> function) {
        return s -> Suite.set(function.apply(s.asExpected()));
    }

    static<T, U> Action wrap(Class<T> argType, Function<T, U> function) {
        return s -> Suite.set(function.apply(s.asExpected()));
    }

    static<T1, T2, U> Action wrap(Class<T1> arg1Type, Class<T2> arg2Type, BiFunction<T1, T2, U> function) {
        return s -> Suite.set(function.apply(s.asExpected(), s.getAt(1).asExpected()));
    }

}
