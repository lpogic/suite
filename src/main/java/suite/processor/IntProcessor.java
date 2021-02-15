package suite.processor;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.function.IntConsumer;

@FunctionalInterface
public interface IntProcessor extends IntConsumer {

    void advance(int i);

    @Override
    default void accept(int value) {
        advance(value);
    }
    default void getReady() {}
    default Subject finish() {
        return Suite.set();
    }

    default Subject process(String str) {
        return process(() -> str.chars().iterator());
    }

    default Subject process(Iterable<Integer> it) {
        getReady();
        for(int i : it) {
            advance(i);
        }
        return finish();
    }
}
