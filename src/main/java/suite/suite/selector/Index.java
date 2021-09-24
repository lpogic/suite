package suite.suite.selector;

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

    public static<T> Index<T> of(int i) {
        return new Index<>(i);
    }
}
