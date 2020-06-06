package suite.suite.action;

import suite.suite.Subject;
import suite.suite.Suite;

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
