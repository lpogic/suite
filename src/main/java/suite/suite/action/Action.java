package suite.suite.action;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
public interface Action {

    default Subject play() {
        return play(Suite.set());
    }

    Subject play(Subject in);

    default Subject gamble() throws Exception {
        return play(Suite.set());
    }

    default Subject gamble(Subject in) throws Exception {
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
