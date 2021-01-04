package suite.suite.tester;

import suite.suite.Subject;
import suite.suite.Vendor;

import java.util.function.Predicate;

public class IsFree implements Predicate<Vendor> {
    Object key;

    public IsFree(Object key) {
        this.key = key;
    }

    @Override
    public boolean test(Vendor subject) {
        return subject.at(key).isEmpty();
    }
}
