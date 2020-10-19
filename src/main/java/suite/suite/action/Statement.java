package suite.suite.action;

import suite.suite.Subject;
import suite.suite.Suite;

@FunctionalInterface
public interface Statement extends Impression {

    void revel();

    @Override
    default void revel(Subject in) {
        revel();
    }

    default Subject play() {
        revel();
        return Suite.set();
    }

    default Subject play(Subject in) {
        revel();
        return Suite.set();
    }

    default Subject gamble() {
        revel();
        return Suite.set();
    }

    default Subject gamble(Subject in) {
        revel();
        return Suite.set();
    }
}
