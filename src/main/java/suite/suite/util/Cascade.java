package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Cascade<T> implements Iterator<T>, Sequence<T> {

    private final Iterator<T> iterator;
    private final Subject stored;
    private int counter = 0;
    private boolean hasNextFired = false;

    public Cascade(Iterable<T> iterable) {
        this(iterable.iterator());
    }

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
        hasNextFired = true;
        return stored.present() || iterator.hasNext();
    }

    public boolean hasNext(Predicate<T> predicate) {
        if(!hasNext())return false;
        T next = privateNext();
        pushNext(next);
        return predicate.test(next);
    }

    @Override
    public T next() {
        ++counter;
        if(!hasNextFired) hasNext();
        hasNextFired = false;
        return privateNext();
    }

    public T next(boolean preserve) {
        var t = next();
        if(preserve) pushNext(t);
        return t;
    }


    public T nextOr(T substitute) {
        ++counter;
        return hasNext() ? privateNext() : substitute;
    }

    private T privateNext() {
        return stored.present() ? stored.take(stored.raw()).in().asExpected() : iterator.next();
    }

    public T nextOrNull() {
        return hasNext() ? next() : null;
    }

    public void pushNext(T t) {
        stored.aimedAdd(stored.raw(), t);
    }

    public int counter() {
        return counter;
    }

    public boolean opening() {
        return counter == 1;
    }

    public boolean closing() {
        return !hasNext();
    }

    @SuppressWarnings("next")
    public Sequence<T> until(Predicate<T> predicate) {
        return () -> new Iterator<>() {

            @Override
            public boolean hasNext() {
                if(Cascade.this.hasNext()) {
                    T next = Cascade.this.privateNext();
                    if(predicate.test(next)) {
                        pushNext(next);
                        return true;
                    } else {
                        pushNext(next);
                        return false;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                return Cascade.this.next();
            }
        };
    }

    @SuppressWarnings("next")
    public Sequence<T> until(Predicate<T> predicate, boolean consume) {
        return () -> new Iterator<>() {

            @Override
            public boolean hasNext() {
                if(Cascade.this.hasNext()) {
                    T next = Cascade.this.privateNext();
                    if(predicate.test(next)) {
                        pushNext(next);
                        return true;
                    } else {
                        if(!consume) pushNext(next);
                        return false;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                return Cascade.this.next();
            }
        };
    }

    @SafeVarargs
    static<I> Cascade<I> of(I ... is) {
        return ofEntire(List.of(is));
    }

    static<I> Cascade<I> ofEntire(Iterable<I> iterable) {
        return ofEntire(iterable.iterator());
    }

    static<I> Cascade<I> ofEntire(Iterator<I> iterator) {
        return iterator instanceof Cascade ? (Cascade<I>)iterator : new Cascade<>(iterator);
    }

    public<I extends T> Cascade<T> and(I i) {
        return andEntire(List.of(i));
    }

    public<I extends T> Cascade<T> andEntire(Iterable<I> iterable) {
        return andEntire(iterable.iterator());
    }

    public<I extends T> Cascade<T> andEntire(Iterator<I> thatIterator) {
        return new Cascade<>(new Iterator<>() {
            Iterator<T> selfWave = Cascade.this.iterator();

            @Override
            public boolean hasNext() {
                if(selfWave != null) {
                    if(selfWave.hasNext()) return true;
                    else selfWave = null;
                }
                return thatIterator.hasNext();
            }

            @Override
            public T next() {
                return selfWave != null ? selfWave.next() : thatIterator.next();
            }
        });
    }
}
