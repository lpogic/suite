package suite.suite.util;

import suite.suite.Vendor;
import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.action.Action;

import java.util.Iterator;
import java.util.function.Predicate;

public interface Fluid extends Iterable<Vendor> {
    Wave<Vendor> iterator();

    default Cascade<Vendor> cascade() {
        return new Cascade<>(iterator());
    }

    default Fluid select(Predicate<Vendor> predicate) {
        return () -> new Wave<>() {
            final Iterator<Vendor> origin = iterator();
            Vendor next = null;
            boolean nextFound = false;

            @Override
            public boolean hasNext() {
                if(nextFound) return true;
                while (origin.hasNext()) {
                    Vendor v = origin.next();
                    if(predicate.test(v)) {
                        next = v;
                        nextFound = true;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Vendor next() {
                nextFound = false;
                return next;
            }
        };
    }

    default Fluid exclude(Predicate<Vendor> predicate) {
        return select(predicate.negate());
    }

    default Vendor atFirst() {
        Wave<Vendor> wave = iterator();
        return wave.hasNext() ? wave.next() : Suite.set();
    }

    default Fluid convert(Action action) {
        return () -> new Wave<>() {
            final Iterator<Vendor> origin = iterator();

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public Vendor next() {
                return action.play(origin.next());
            }
        };
    }

    default Fluid append(Iterable<Vendor> iterable) {
        return () -> new Wave<>() {
            Wave<Vendor> selfWave = Fluid.this.iterator();
            final Iterator<Vendor> thatIterator = iterable.iterator();

            @Override
            public boolean hasNext() {
                if(selfWave != null) {
                    if(selfWave.hasNext()) return true;
                    else selfWave = null;
                }
                return thatIterator.hasNext();
            }

            @Override
            public Vendor next() {
                return selfWave != null ? selfWave.next() : thatIterator.next();
            }
        };
    }

    default Slime<?> eachDirect() {
        return () -> new Wave<>() {
            final Iterator<Vendor> subIt = iterator();

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

    default<T> Slime<T> eachAs(Class<T> type) {
        return () -> new Wave<>() {
            final Iterator<Vendor> subIt = iterator();

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

    default<T> Slime<T> eachAs(Glass<? super T,T> type) {
        return () -> new Wave<>() {
            final Iterator<Vendor> subIt = iterator();

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

    default Fluid eachGet() {
        return () -> new Wave<>() {
            final Iterator<Vendor> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public Vendor next() {
                return subIt.next().get();
            }
        };
    }

    default Object direct() {
        return atFirst().direct();
    }

    default Subject set() {
        return Suite.join(this);
    }

    static Fluid source(Iterable<Vendor> iterable) {
        return iterable instanceof Fluid ? (Fluid)iterable : () -> Wave.wave(iterable.iterator());
    }

    static Fluid emptyFluid() {
        return Wave::emptyWave;
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
            public Vendor next() {
                return Suite.set(keyIt.next(), Suite.set(valIt.next()));
            }
        };
    }

}
