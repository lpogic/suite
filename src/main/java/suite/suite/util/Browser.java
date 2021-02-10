package suite.suite.util;

import suite.suite.Subject;
import suite.suite.Suite;

import java.util.Iterator;

public interface Browser extends Iterator<Subject> {

    default Cascade<Subject> cascade() {
        return new Cascade<>(this);
    }

    static Browser empty() {
        return new Browser() {

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Subject next() {
                return Suite.set();
            }
        };
    }

    static Browser of(Iterator<? extends Subject> it) {
        return it instanceof Browser ? (Browser)it : new Browser() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Subject next() {
                return it.next();
            }
        };
    }
}
