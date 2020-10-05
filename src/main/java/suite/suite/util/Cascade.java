package suite.suite.util;

import suite.suite.Slot;
import suite.suite.Subject;
import suite.suite.Suite;

import java.util.Iterator;
import java.util.function.Predicate;

public class Cascade<T> implements Wave<T> {

    private final Iterator<T> iterator;
    private final Subject stored;
    private int falls = 0;

    public Cascade(Iterator<T> iterator) {
        this.iterator = iterator;
        stored = Suite.set();
    }

    @Override
    public boolean hasNext() {
        return stored.settled() || iterator.hasNext();
    }

    public boolean hasNext(Predicate<T> predicate) {
        if(!hasNext())return false;
        T next = next();
        store(next);
        return predicate.test(next);
    }

    @Override
    public T next() {
        return stored.settled() ? stored.takeAt(Slot.PRIME).asExpected() : iterator.next();
    }

    public void store(T t) {
        stored.add(t);
    }

    public int getFalls() {
        return falls;
    }

    public Slime<T> until(Predicate<T> predicate) {
        falls = 0;
        return () -> new Wave<>() {

            @Override
            public boolean hasNext() {
                if(Cascade.this.hasNext()) {
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

    public Slime<T> toEnd() {
        falls = 0;
        return () -> new Wave<T>() {
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
