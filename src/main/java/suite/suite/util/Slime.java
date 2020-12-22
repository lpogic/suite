package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Slime<T> extends Iterable<T>{
    Wave<T> iterator();

    default Cascade<T> cascade() {
        return new Cascade<>(iterator());
    }

    default <F> Slime<F> filter(Class<F> requestedType) {
        return filter(Glass.of(requestedType));
    }

    default <F> Slime<F> filter(Glass<? super F, F> requestedType) {
        return () -> new Wave<>() {
            final Iterator<T> origin = iterator();
            F next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return true;
                while (origin.hasNext()) {
                    Object o = origin.next();
                    if(requestedType.isInstance(o)) {
                        next = requestedType.cast(o);
                        nextFound = true;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public F next() {
                nextFound = false;
                return next;
            }
        };
    }

    default Slime<T> filter(Predicate<T> predicate) {
        return () -> new Wave<>() {
            final Iterator<T> origin = iterator();
            T next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return true;
                while (origin.hasNext()) {
                    T t = origin.next();
                    if(predicate.test(t)) {
                        next = t;
                        nextFound = true;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                nextFound = false;
                return next;
            }
        };
    }

    static<I> Slime<I> empty() {
        return Wave::emptyWave;
    }

    default<O> Slime<O> map(Function<T, O> function) {
        return () -> new Wave<>() {
            final Iterator<T> origin = iterator();

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public O next() {
                return function.apply(origin.next());
            }
        };
    }

    default Slime<T> skip(int from, int to) {
        return filter(new Predicate<>() {
            int counter = 0;

            @Override
            public boolean test(T t) {
                ++counter;
                return counter <= from || counter > to;
            }
        });
    }

    default boolean allTrue(Predicate<T> predicate) {
        for(T t : this) {
            if(!predicate.test(t))return false;
        }
        return true;
    }

    default boolean anyTrue(Predicate<T> predicate) {
        for(T t : this) {
            if(predicate.test(t))return true;
        }
        return false;
    }


    default List<T> toList() {
        List<T> list = new ArrayList<>();
        forEach(list::add);
        return list;
    }

    default Subject toSubject() {
        return Suite.setAll(this);
    }

    default String toString(String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> it = iterator();
        while(it.hasNext()) {
            stringBuilder.append(it.next());
            if(it.hasNext())stringBuilder.append(separator);
        }
        return stringBuilder.toString();
    }

    static<I> Slime<I> source(Iterable<I> iterable) {
        return iterable instanceof Slime ? (Slime<I>)iterable : () -> Wave.wave(iterable.iterator());
    }

    default Slime<T> append(Iterable<T> iterable) {
        return () -> new Wave<>() {
            Wave<T> selfWave = Slime.this.iterator();
            final Iterator<T> thatIterator = iterable.iterator();

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
        };
    }
}
