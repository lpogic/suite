package suite.suite.action;

import suite.suite.Subject;
import suite.suite.Suite;

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
