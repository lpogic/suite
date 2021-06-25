package suite.suite.util;

import suite.suite.Sub;
import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.action.Action;
import suite.suite.selector.Index;
import suite.suite.selector.Type;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Series extends Iterable<Subject> {
    Browser iterator();

    default Cascade<Subject> cascade() {
        return new Cascade<>(iterator());
    }

    default Series select(Predicate<Subject> predicate) {
        return () -> new Browser() {
            final Iterator<Subject> origin = iterator();
            Subject next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return true;
                while (origin.hasNext()) {
                    Subject v = origin.next();
                    if(predicate.test(v)) {
                        next = v;
                        return nextFound = true;
                    }
                }
                return false;
            }

            @Override
            public Subject next() {
                nextFound = false;
                return next;
            }
        };
    }

    default Series select(int i) {
        return select(new Index(i));
    }

    default Series select(Class<?> type) {
        return select(new Type(type));
    }

    default<T> Sequence<T> selectAs(Class<T> type) {
        return select(new Type(type)).eachAs(type);
    }

    default Subject at(Predicate<Subject> predicate) {
        return select(predicate).in().get();
    }

    default Subject at(int i) {
        return select(new Index(i)).in().get();
    }

    default Subject at() {
        return first().in().get();
    }

    default Series enumerate() {
        return () -> new Browser() {
            final Iterator<Subject> origin = iterator();
            int i = 0;

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.inset(i++, origin.next());
            }
        };
    }

    default Series exclude(Predicate<Subject> predicate) {
        return select(predicate.negate());
    }

    default Subject first() {
        Browser wave = iterator();
        return wave.hasNext() ? wave.next() : Suite.set();
    }

    default Series convert(Action action) {
        return () -> new Browser() {
            final Iterator<Subject> origin = iterator();

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public Subject next() {
                return action.play(origin.next());
            }
        };
    }

    default Series join(Iterable<Subject> iterable) {
        return () -> new Browser() {
            Browser firstWave = Series.this.iterator();
            final Iterator<Subject> secondWave = iterable.iterator();

            @Override
            public boolean hasNext() {
                if(firstWave != null) {
                    if(firstWave.hasNext()) return true;
                    else firstWave = null;
                }
                return secondWave.hasNext();
            }

            @Override
            public Subject next() {
                return firstWave != null ? firstWave.next() : secondWave.next();
            }
        };
    }

    default Sequence<?> eachRaw() {
        return () -> new Iterator<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Object next() {
                return subIt.next().raw();
            }
        };
    }

    default<T> Sequence<T> eachAs(Class<T> type) {
        return () -> new Iterator<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public T next() {
                return subIt.next().as(type);
            }
        };
    }

    default<T> Sequence<T> eachAs(Glass<? super T,T> type) {
        return () -> new Iterator<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public T next() {
                return subIt.next().as(type);
            }
        };
    }

    default Series eachIn() {
        return () -> new Browser() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Subject next() {
                return subIt.next().at();
            }
        };
    }

    default Series list() {
        return () -> new Browser() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Subject next() {
                var next = subIt.next();
                return next.is(Suite.Auto.class) ? next.at() : next;
            }
        };
    }

    default Sub in() {
        return first().in();
    }

    default Object raw() {
        return first().raw();
    }

    default <B> B asExpected() {
        return first().asExpected();
    }

    default <B> B as(Class<B> requestedType) {
        return first().as(requestedType);
    }

    default <B> B as(Glass<? super B, B> requestedType) {
        return first().as(requestedType);
    }

    default <B> B as(Class<B> requestedType, B reserve) {
        return first().as(requestedType, reserve);
    }

    default <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return first().as(requestedType, reserve);
    }

    default <B> B orGiven(B reserve) {
        return first().orGiven(reserve);
    }

    default <B> B orDo(Supplier<B> supplier) {
        return first().orDo(supplier);
    }

    default boolean is(Class<?> type) {
        return first().is(type);
    }

    default boolean present() {
        return first().present();
    }

    default boolean absent() {
        return first().absent();
    }

    default Subject set() {
        return Suite.alter(this);
    }

    static Series ofEntire(Iterable<Subject> iterable) {
        return iterable instanceof Series ? (Series)iterable : () -> Browser.of(iterable.iterator());
    }

    static Series of(Subject ... subjects) {
        return ofEntire(List.of(subjects));
    }

    static Series empty() {
        return Browser::empty;
    }

    static Series engage(Iterable<?> keys, Iterable<?> values) {
        return () -> new Browser() {
            final Iterator<?> keyIt = keys.iterator();
            final Iterator<?> valIt = values.iterator();

            @Override
            public boolean hasNext() {
                return keyIt.hasNext() && valIt.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.inset(keyIt.next(), Suite.set(valIt.next()));
            }
        };
    }

    static Series parallel(Series s1, Series s2, Series ... rest) {

        return () -> new Browser() {
            final Subject $its = Suite.put(0, s1.iterator()).put(1, s2.iterator()); {
                int i = 2;
                for(Series s : rest) {
                    $its.put(i++, s.iterator());
                }
            }
            Subject $collected = Suite.set();

            @Override
            public boolean hasNext() {
                var $c = Suite.set();
                for(var $ : $its) {
                    Browser b = $.in().asExpected();
                    if(b.hasNext()) $c.inset($.raw(), b.next());
                    else return false;
                }
                $collected = $c;
                return true;
            }

            @Override
            public Subject next() {
                var $c = $collected;
                $collected = Suite.set();
                return $c;
            }
        };
    }

    default Series print() {
        System.out.println(Suite.toString(this));
        return this;
    }

    default int asInt() {
        return as(Number.class).intValue();
    }

    default byte asByte() {
        return as(Number.class).byteValue();
    }

    default long asLong() {
        return as(Number.class).longValue();
    }

    default long asShort() {
        return as(Number.class).shortValue();
    }

    default float asFloat() {
        return as(Number.class).floatValue();
    }

    default double asDouble() {
        return as(Number.class).doubleValue();
    }

    default char asChar() {
        return as(Character.class);
    }

    default boolean asBoolean() {
        return as(Boolean.class);
    }

    default String asString() {
        return as(String.class);
    }

    default int asInt(int reserve) {
        return as(Number.class, reserve).intValue();
    }

    default byte asByte(byte reserve) {
        return as(Number.class, reserve).byteValue();
    }

    default long asLong(long reserve) {
        return as(Number.class, reserve).longValue();
    }

    default long asShort(short reserve) {
        return as(Number.class, reserve).shortValue();
    }

    default float asFloat(float reserve) {
        return as(Number.class, reserve).floatValue();
    }

    default double asDouble(double reserve) {
        return as(Number.class, reserve).doubleValue();
    }

    default char asChar(char reserve) {
        return as(Character.class, reserve);
    }

    default boolean asBoolean(boolean reserve) {
        return as(Boolean.class, reserve);
    }

    default String asString(String reserve) {
        return as(String.class, reserve);
    }
}
