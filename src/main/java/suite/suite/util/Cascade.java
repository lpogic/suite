package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.Iterator;
import java.util.function.Predicate;

public class Cascade<T> implements Iterator<T>, Sequence<T> {

    private final Iterator<T> iterator;
    private final Subject stored;
    private int falls = 0;

    public Cascade(Iterator<T> iterator) {
        this.iterator = iterator;
        stored = Suite.set();
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public Cascade<T> cascade() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return stored.present() || iterator.hasNext();
    }

    public boolean hasNext(Predicate<T> predicate) {
        if(!hasNext())return false;
        T next = next();
        store(next);
        return predicate.test(next);
    }

    @Override
    public T next() {
        return stored.present() ? stored.take(stored.raw()).asExpected() : iterator.next();
    }

    public void store(T t) {
        stored.in().set(t);
    }

    public int getFalls() {
        return falls;
    }

    @SuppressWarnings("next")
    public Sequence<T> until(Predicate<T> predicate) {
        falls = 0;
        return () -> new Iterator<>() {

            @Override
            public boolean hasNext() {
                if(Cascade.this.hasNext()) {
                    //noinspection IteratorHasNextCallsIteratorNext
                    T next = Cascade.this.next();
                    if(predicate.test(next)) {
                        store(next);
                        return true;
                    } else {
                        store(next);
                        return false;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                ++falls;
                return Cascade.this.next();
            }
        };
    }

    public Sequence<T> toEnd() {
        falls = 0;
        return () -> new Iterator<>() {
            @Override
            public boolean hasNext() {
                return Cascade.this.hasNext();
            }

            @Override
            public T next() {
                ++falls;
                return Cascade.this.next();
            }
        };
    }
}
