package suite.suite.selector;

import suite.suite.Subject;

import java.util.function.Predicate;

public class Index<T> implements Predicate<T> {

    int counter = 0;
    final int index;

    public Index(int index) {
        this.index = index;
    }

    @Override
    public boolean test(T t) {
        return counter++ == index;
    }

    public static Index of(int i) {
        return new Index(i);
    }
}
