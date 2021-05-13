package suite.suite.selector;

import suite.suite.Subject;

import java.util.function.Predicate;

public record Type(Class<?> type) implements Predicate<Subject> {

    @Override
    public boolean test(Subject subject) {
        return subject.is(type);
    }
}
