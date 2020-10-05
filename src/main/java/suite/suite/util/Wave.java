package suite.suite.util;

import java.util.Iterator;

public interface Wave<T> extends Iterator<T> {

    default Cascade<T> cascade() {
        return new Cascade<>(this);
    }

    static<I> Wave<I> empty() {
        return new Wave<>() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public I next() {
                return null;
            }
        };
    }
}
