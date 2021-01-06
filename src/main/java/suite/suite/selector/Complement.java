package suite.suite.selector;

import suite.suite.Subject;

import java.util.function.Predicate;

public class Complement implements Predicate<Subject> {

    Subject $;

    public Complement(Subject $) {
        this.$ = $;
    }

    @Override
    public boolean test(Subject subject) {
        return $.get(subject.direct()).absent();
    }
}
