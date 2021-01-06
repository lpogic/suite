package suite.suite.tester;

import suite.suite.Subject;

import java.util.function.Predicate;

public class IsFree implements Predicate<Subject> {
    Object key;

    public IsFree(Object key) {
        this.key = key;
    }

    @Override
    public boolean test(Subject subject) {
        return subject.get(key).absent();
    }
}
