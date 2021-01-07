package suite.suite.selector;

import suite.suite.Subject;

import java.util.function.Predicate;

public class Intersect implements Predicate<Subject> {

    Subject $;

    public Intersect(Subject $) {
        this.$ = $;
    }

    @Override
    public boolean test(Subject subject) {
        return $.get(subject.direct()).present();
    }

    public static Intersect respect(Subject $s) {
        return new Intersect($s);
    }
}
