package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.action.Action;

import java.util.Iterator;
import java.util.function.Predicate;

public interface Series extends Iterable<Subject> {
    Browser<Subject> iterator();

    default Cascade<Subject> cascade() {
        return new Cascade<>(iterator());
    }

    default Series select(Predicate<Subject> predicate) {
        return () -> new Browser<>() {
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
                        nextFound = true;
                        return true;
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

    default Series exclude(Predicate<Subject> predicate) {
        return select(predicate.negate());
    }

    default Subject getFirst() {
        Browser<Subject> wave = iterator();
        return wave.hasNext() ? wave.next() : Suite.set();
    }

    default Series convert(Action action) {
        return () -> new Browser<>() {
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
        return () -> new Browser<>() {
            Browser<Subject> selfWave = Series.this.iterator();
            final Iterator<Subject> thatIterator = iterable.iterator();

            @Override
            public boolean hasNext() {
                if(selfWave != null) {
                    if(selfWave.hasNext()) return true;
                    else selfWave = null;
                }
                return thatIterator.hasNext();
            }

            @Override
            public Subject next() {
                return selfWave != null ? selfWave.next() : thatIterator.next();
            }
        };
    }

    default Sequence<?> eachDirect() {
        return () -> new Browser<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Object next() {
                return subIt.next().direct();
            }
        };
    }

    default<T> Sequence<T> eachAs(Class<T> type) {
        return () -> new Browser<>() {
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
        return () -> new Browser<>() {
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

    default Series atEach() {
        return () -> new Browser<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Subject next() {
                var $ = subIt.next();
                return $.at($.direct());
            }
        };
    }

    default Object direct() {
        return getFirst().direct();
    }

    default Subject set() {
        return Suite.alter(this);
    }

    static Series of(Iterable<Subject> iterable) {
        return iterable instanceof Series ? (Series)iterable : () -> Browser.of(iterable.iterator());
    }

    static Series emptyFluid() {
        return Browser::empty;
    }

    static Series engage(Iterable<?> keys, Iterable<?> values) {
        return () -> new Browser<>() {
            final Iterator<?> keyIt = keys.iterator();
            final Iterator<?> valIt = values.iterator();

            @Override
            public boolean hasNext() {
                return keyIt.hasNext() && valIt.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.set(keyIt.next(), Suite.set(valIt.next()));
            }
        };
    }

}
