package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.action.Action;

import java.util.Iterator;
import java.util.function.Predicate;

public interface Fluid extends Iterable<Subject> {
    Wave<Subject> iterator();

    default Cascade<Subject> cascade() {
        return new Cascade<>(iterator());
    }

    default Fluid all(Predicate<Subject> predicate) {
        return () -> new Wave<>() {
            final Iterator<Subject> origin = iterator();
            Subject next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return true;
                while (origin.hasNext()) {
                    Subject s = origin.next();
                    if(predicate.test(s)) {
                        next = s;
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

    default Fluid first(Predicate<Subject> predicate) {
        return () -> new Wave<>() {
            final Iterator<Subject> origin = iterator();
            Subject next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return false;
                while (origin.hasNext()) {
                    Subject s = origin.next();
                    if(predicate.test(s)) {
                        next = s;
                        nextFound = true;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Subject next() {
                return next;
            }
        };
    }

    default Fluid map(Action action) {
        return () -> new Wave<>() {
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

    default Slime<?> keys() {
        return () -> new Wave<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Object next() {
                return subIt.next().key().direct();
            }
        };
    }

    default<T> Slime<T> keys(Class<T> type) {
        return () -> new Wave<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public T next() {
                return subIt.next().key().asExpected();
            }
        };
    }

    default Slime<?> values() {
        return () -> new Wave<>() {
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

    default<T> Slime<T> values(Class<T> type) {
        return () -> new Wave<>() {
            final Iterator<Subject> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public T next() {
                return subIt.next().asExpected();
            }
        };
    }

    default Subject set() {
        return Suite.inset(this);
    }
    default Subject put() {
        return Suite.input(this);
    }

    static Fluid empty() {
        return Wave::empty;
    }

    static Fluid engage(Iterable<?> keys, Iterable<?> values) {
        return () -> new Wave<>() {
            final Iterator<?> keyIt = keys.iterator();
            final Iterator<?> valIt = values.iterator();

            @Override
            public boolean hasNext() {
                return keyIt.hasNext() && valIt.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.set(keyIt.next(), valIt.next());
            }
        };
    }

    static Fluid engageS(Iterable<?> keys, Iterable<?> values) {
        return () -> new Wave<>() {
            final Iterator<?> keyIt = Wave.waveS(keys.iterator());
            final Iterator<?> valIt = Wave.waveS(values.iterator());

            @Override
            public boolean hasNext() {
                return keyIt.hasNext() && valIt.hasNext();
            }

            @Override
            public Subject next() {
                return Suite.set(keyIt.next(), valIt.next());
            }
        };
    }

    static Fluid source(Iterable<Subject> iterable) {
        return iterable instanceof Fluid ? (Fluid)iterable : () -> Wave.wave(iterable.iterator());
    }

    default Fluid append(Iterable<Subject> iterable) {
        return () -> new Wave<>() {
            Wave<Subject> selfWave = Fluid.this.iterator();
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

}
