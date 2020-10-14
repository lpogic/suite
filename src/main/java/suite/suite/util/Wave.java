package suite.suite.util;

import suite.suite.Subject;

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

    static Wave<Object> wave(Iterator<?> it) {
        return new Wave<>() {
            final Iterator<?> primary = it;
            Iterator<?> secondary = null;
            Object next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return true;
                if(secondary != null) {
                    if(secondary.hasNext()) return true;
                    else secondary = null;
                }
                while (primary.hasNext()) {
                    Object o = primary.next();
                    if(o instanceof Iterable) {
                        secondary = ((Iterable<?>) o).iterator();
                        if(secondary.hasNext()) return true;
                        else secondary = null;
                    } else {
                        next = o;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Object next() {
                nextFound = false;
                return secondary == null ? next : secondary.next();
            }
        };
    }
}
