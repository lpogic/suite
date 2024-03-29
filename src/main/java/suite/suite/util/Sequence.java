package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.selector.Index;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public interface Sequence<T> extends Iterable<T>{
    Iterator<T> iterator();

    default Cascade<T> cascade() {
        return new Cascade<>(iterator());
    }

    default <F> Sequence<F> select(Class<F> requestedType) {
        return select(Glass.of(requestedType));
    }

    default <F> Sequence<F> select(Glass<? super F, F> requestedType) {
        return () -> new Iterator<>() {
            final Iterator<T> origin = iterator();
            F next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if (nextFound) return true;
                while (origin.hasNext()) {
                    Object o = origin.next();
                    if (requestedType.isInstance(o)) {
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

    default Sequence<T> select(Predicate<T> predicate) {
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

    default<F extends T> Sequence<F> select(Class<F> type, Predicate<F> predicate) {
        return select(type).select(predicate);
    }

    default T select(int i) {
        return select(new Index<>(i)).first();
    }

    default Sequence<T> until(Predicate<T> predicate) {
        return () -> new Iterator<>() {
            final Iterator<T> origin = iterator();
            T next = null;
            boolean nextFound = false;
            boolean testFailed = false;

            @Override
            public boolean hasNext() {
                if(testFailed) return false;
                if(nextFound) return true;
                if (origin.hasNext()) {
                    var v = origin.next();
                    testFailed = !predicate.test(v);
                    if(testFailed) return false;
                    next = v;
                    return nextFound = true;
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

    default Sequence<T> first(int limit) {
        return until(new Index<T>(limit).negate());
    }

    default Sequence<T> skip(int skipped) {
        return () -> new Iterator<>() {
            final Iterator<T> origin = iterator();
            {
                for(int i = 0;i < skipped;++i) {
                    if(origin.hasNext()) origin.next();
                }
            }

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public T next() {
                return origin.next();
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

    default<O> Sequence<O> convert(Function<T, O> function) {
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

    default<O> O entire(Function<Sequence<T>, O> function) {
        return function.apply(this);
    }

    default<O, F> O entire(Class<F> type, Function<Sequence<F>, O> function) {
        return function.apply(select(type));
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

    default boolean isEmpty() {
        for(T ignored : this) return false;
        return true;
    }

    default boolean isAny() {
        return !isEmpty();
    }

    default T first() {
        var it = iterator();
        return it.hasNext() ? it.next() : null;
    }

    default int indexOf(Predicate<T> predicate) {
        int i = 0;
        for(T t : this) {
            if(predicate.test(t))return i;
            ++i;
        }
        return -1;
    }

    default List<T> toList() {
        List<T> list = new ArrayList<>();
        forEach(list::add);
        return list;
    }

    @SuppressWarnings("unchecked")
    default T[] toArray() {
        return (T[])toList().toArray();
    }

    default Subject set() {
        return Suite.setEntire(this);
    }

    default Subject list() {
        return Suite.addEntire(this);
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

    default Series index(Iterable<?> indices) {
        return () -> new Browser() {
            final Iterator<?> k = indices.iterator();
            final Iterator<?> v = iterator();

            @Override
            public boolean hasNext() {
                return k.hasNext() && v.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.put(k.next(), v.next());
            }
        };
    }

    default String string() {
        return string(", ");
    }

    default String string(String separator) {
        Cascade<T> c = cascade();
        if(!c.hasNext()) return "";
        StringBuilder stringBuilder = new StringBuilder();
        var n1 = c.next();
        for(var n2 : c) {
            stringBuilder.append(n1.toString()).append(separator);
            n1 = n2;
        }
        stringBuilder.append(n1.toString());
        return stringBuilder.toString();
    }

    default String string(String separator, String lastSeparator) {
        Cascade<T> c = cascade();
        if(!c.hasNext()) return "";
        var n1 = c.next();
        if(!c.hasNext()) return "" + n1;
        StringBuilder stringBuilder = new StringBuilder();
        var n2 = c.next();
        for(var n3 : c) {
            stringBuilder.append(n1.toString()).append(separator);
            n1 = n2;
            n2 = n3;
        }
        stringBuilder.append(n1.toString()).append(lastSeparator).append(n2.toString());
        return stringBuilder.toString();
    }

    default Sequence<T> sort(Comparator<T> cmp) {
        var a = toArray();
        Arrays.sort(a, cmp);
        return Sequence.ofEntire(Arrays.asList(a));
    }

    default Sequence<T> sort() {
        var a = toArray();
        Arrays.sort(a);
        return Sequence.ofEntire(Arrays.asList(a));
    }

    default Sequence<T> mix() {
        var list = toList();
        Collections.shuffle(list);
        return Sequence.ofEntire(list);
    }

    default T max(Comparator<T> cmp) {
        return Collections.max(toList(), cmp);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default T max() {
        return Collections.max(toList(), (o1, o2) -> ((Comparable)o1).compareTo(o2));
    }

    default T min(Comparator<T> cmp) {
        return Collections.min(toList(), cmp);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default T min() {
        return Collections.min(toList(), (o1, o2) -> ((Comparable)o1).compareTo(o2));
    }

    static<S> Sequence<S> pull(Supplier<S> supplier) {
        return () -> new Iterator<>() {

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public S next() {
                return supplier.get();
            }
        };
    }

    @SafeVarargs
    static<I, IN extends I> Sequence<I> of(I i0, IN ... is) {
        return ((Sequence<I>) () -> new Iterator<I>() {
            boolean has = true;

            @Override
            public boolean hasNext() {
                return has;
            }

            @Override
            public I next() {
                has = false;
                return i0;
            }
        }).andEntire(List.of(is));
    }

    @SafeVarargs
    static<I> Sequence<I> ofEntire(I ... is) {
        return ofEntire(List.of(is));
    }

    static<I> Sequence<I> ofEntire(Iterable<I> iterable) {
        if(iterable instanceof Sequence<I> s) return s;
        if(iterable instanceof List<I> l) return new Sequence<>() {
            @Override
            public Iterator<I> iterator() {
                return l.iterator();
            }

            @Override
            public List<I> toList() {
                return l;
            }
        };
        return iterable::iterator;
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
