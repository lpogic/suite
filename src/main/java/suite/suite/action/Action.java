package suite.suite.action;

import suite.suite.Suite;
import suite.suite.Subject;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface Action extends Function<Subject, Subject>, Supplier<Subject>, Consumer<Subject> {

    @Override
    default Subject apply(Subject subject) {
        return play(subject);
    }

    @Override
    default Subject get() {
        return play(Suite.set());
    }

    @Override
    default void accept(Subject subject) {
        play(subject);
    }

    static Action identity() {
        return $ -> $;
    }

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

    static<T, U> Action function(Function<T, U> function) {
        return $ -> Suite.set(function.apply($.asExpected()));
    }

    static<T, U> Action function(Class<T> argType, Function<T, U> function) {
        return $ -> Suite.set(function.apply($.asExpected()));
    }

    static<T1, T2, U> Action biFunction(Class<T1> arg1Type, Class<T2> arg2Type, BiFunction<T1, T2, U> function) {
        return $ -> Suite.set(function.apply($.asExpected(), $.getLast().asExpected()));
    }

    static<U> Action supplier(Supplier<U> supplier) {
        return $ -> Suite.set(supplier.get());
    }

    static<T> Action consumer(Consumer<T> consumer) {
        return $ -> {
            consumer.accept($.asExpected());
            return Suite.set();
        };
    }

}
