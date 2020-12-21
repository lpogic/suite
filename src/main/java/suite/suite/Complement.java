package suite.suite;

import java.util.function.Predicate;

public class Complement implements Predicate<Subject> {

    Subject $;

    public Complement(Subject $) {
        this.$ = $;
    }

    @Override
    public boolean test(Subject subject) {
        return $.at(subject.direct()).isEmpty();
    }
}
