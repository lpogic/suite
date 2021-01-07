package suite.suite.util;

import java.util.Iterator;

public interface Browser<T> extends Iterator<T> {

    @Override
    boolean hasNext();

    @Override
    T next();

    default Cascade<T> cascade() {
        return new Cascade<>(this);
    }

    static<I> Browser<I> empty() {
        return new Browser<>() {

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

    static<I> Browser<I> of(Iterator<I> it) {
        return it instanceof Browser ? (Browser<I>)it : new Browser<>() {
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
