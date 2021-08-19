package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.Iterator;
import java.util.List;
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
        T next = privateNext();
        setNext(next);
        return predicate.test(next);
    }

    @Override
    public T next() {
        ++falls;
        return privateNext();
    }


    public T nextOr(T substitute) {
        ++falls;
        return hasNext() ? privateNext() : substitute;
    }

    private T privateNext() {
        return stored.present() ? stored.take(stored.raw()).asExpected() : iterator.next();
    }

    public T nextOrNull() {
        return hasNext() ? next() : null;
    }

    public void setNext(T t) {
        stored.aimedAdd(stored.raw(), t);
    }

    public int getFalls() {
        return falls;
    }

    public boolean firstFall() {
        return falls == 1;
    }

    @SuppressWarnings("next")
    public Sequence<T> until(Predicate<T> predicate) {
        return () -> new Iterator<>() {

            @Override
            public boolean hasNext() {
                if(Cascade.this.hasNext()) {
                    T next = Cascade.this.privateNext();
                    if(predicate.test(next)) {
                        setNext(next);
                        return true;
                    } else {
                        setNext(next);
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
                        setNext(next);
                        return true;
                    } else {
                        if(!consume) setNext(next);
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
