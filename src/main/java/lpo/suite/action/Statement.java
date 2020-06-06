package lpo.suite.action;

import lpo.suite.Subject;
import lpo.suite.Suite;

@FunctionalInterface
public interface Statement extends Action {

    void revel();

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
