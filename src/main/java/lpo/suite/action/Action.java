package lpo.suite.action;

import lpo.suite.Subject;
import lpo.suite.Suite;

@FunctionalInterface
public interface Action {

    default Subject play() {
        return play(Suite.set());
    }

    Subject play(Subject in);

    default Subject gamble() throws Exception {
        return play(Suite.set());
    }

    default Subject gamble(Subject in) throws Exception {
        return play(in);
    }

}
