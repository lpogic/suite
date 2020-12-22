package suite.suite.selector;

import suite.suite.Subject;

import java.util.function.Predicate;

public class AtIndex implements Predicate<Subject> {

    int counter = 0;
    int index;

    public AtIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean test(Subject subject) {
        return counter++ == index;
    }
}
