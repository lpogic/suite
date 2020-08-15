package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FluidSubject extends Iterable<Subject> {
    FluidIterator<Subject> iterator();

    default Cascade<Subject> cascade() {
        return new Cascade<>(iterator());
    }

    default FluidSubject filter(Predicate<Subject> predicate) {
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

    default FluidSubject advance(Function<Subject, Subject> function) {
        return () -> new FluidIterator<>() {
            final Iterator<Subject> origin = iterator();

            @Override
            public boolean hasNext() {
                return origin.hasNext();
            }

            @Override
            public Subject next() {
                return function.apply(origin.next());
            }
        };
    }

    default<O> Fluid<O> map(Function<Subject, O> function) {
        return () -> new FluidIterator<>() {
            final Iterator<Subject> origin = iterator();

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

    default FluidSubject skip(int from, int to) {
        return filter(new Predicate<>() {
            int counter = 0;

            @Override
            public boolean test(Subject s) {
                ++counter;
                return counter <= from || counter > to;
            }
        });
    }

    default Fluid<Object> keys() {
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

    default Fluid<Object> values() {
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

    default Subject toSubject() {
        return Suite.insetAll(this);
    }

    static FluidSubject empty() {
        return FluidIterator::empty;
    }

    static FluidSubject engage(Iterable<Object> keys, Iterable<Object> values) {
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
