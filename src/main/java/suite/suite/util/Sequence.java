package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Sequence<T> extends Iterable<T>{
    Iterator<T> iterator();

    default Cascade<T> cascade() {
        return new Cascade<>(iterator());
    }

    default <F> Sequence<F> filter(Class<F> requestedType) {
        return filter(Glass.of(requestedType));
    }

    default <F> Sequence<F> filter(Glass<? super F, F> requestedType) {
        return () -> new Iterator<>() {
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

    default Sequence<T> filter(Predicate<T> predicate) {
        return () -> new Iterator<>() {
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

    static<I> Sequence<I> empty() {
        return () -> new Iterator<>() {
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

    default<O> Sequence<O> each(Function<T, O> function) {
        return () -> new Iterator<>() {
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

    default<P, O> Sequence<O> each(P param, BiFunction<T, P, O> function) {
        return () -> new Iterator<>() {
            final Iterator<T> origin = iterator();

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public O next() {
                return function.apply(origin.next(), param);
            }
        };
    }

    default Sequence<T> skip(int from, int to) {
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

    default Subject set() {
        return Suite.setAll(this);
    }

    default Series series() {
        return () -> new Browser() {
            final Iterator<T> b = Sequence.this.iterator();

            @Override
            public boolean hasNext() {
                return b.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.set(b.next());
            }
        };
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

    @SafeVarargs
    static<I> Sequence<I> of(I ... is) {
        return ofEntire(List.of(is));
    }

    static<I> Sequence<I> ofEntire(Iterable<I> iterable) {
        return iterable instanceof Sequence ? (Sequence<I>)iterable : iterable::iterator;
    }

    default<I extends T> Sequence<T> and(I i) {
        return andEntire(List.of(i));
    }

    default<I extends T> Sequence<T> andEntire(Iterable<I> iterable) {
        return () -> new Iterator<>() {
            Iterator<T> selfWave = Sequence.this.iterator();
            final Iterator<I> thatIterator = iterable.iterator();

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
