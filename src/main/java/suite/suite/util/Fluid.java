package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.action.Action;

import java.util.Iterator;
import java.util.function.Predicate;

public interface Fluid extends Iterable<Subject> {
    FluidIterator<Subject> iterator();

    default Cascade<Subject> cascade() {
        return new Cascade<>(iterator());
    }

    default Fluid filter(Predicate<Subject> predicate) {
        return () -> new FluidIterator<>() {
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

    default Fluid map(Action action) {
        return () -> new FluidIterator<>() {
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

    default FluidObject<Object> keys() {
        return () -> new FluidIterator<>() {
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

    default<T> FluidObject<T> keys(Class<T> type) {
        return () -> new FluidIterator<>() {
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

    default FluidObject<Object> values() {
        return () -> new FluidIterator<>() {
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

    default<T> FluidObject<T> values(Class<T> type) {
        return () -> new FluidIterator<>() {
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
        return Suite.insetAll(this);
    }

    static Fluid empty() {
        return FluidIterator::empty;
    }

    static Fluid engage(Iterable<Object> keys, Iterable<Object> values) {
        return () -> new FluidIterator<>() {
            final Iterator<Object> keyIt = keys.iterator();
            final Iterator<Object> valIt = values.iterator();

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
}
