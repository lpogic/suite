package suite.suite.util;

import java.util.Iterator;

public interface Wave<T> extends Iterator<T> {

    @Override
    boolean hasNext();

    @Override
    T next();

    default Cascade<T> cascade() {
        return new Cascade<>(this);
    }

    static<I> Wave<I> emptyWave() {
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

    static<I> Wave<I> wave(Iterator<I> it) {
        return it instanceof Wave ? (Wave<I>)it : new Wave<>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public I next() {
                return it.next();
            }
        };
    }
}
