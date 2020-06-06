package lpo.suite.action;

import lpo.suite.Subject;
import lpo.suite.Suite;

@FunctionalInterface
public interface DiceyAction extends Action {

    @Override
    default Subject play() {
        try {
            return gamble(Suite.set());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Subject play(Subject in) {
        try {
            return gamble(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Subject gamble() throws Exception {
        return gamble(Suite.set());
    }

    @Override
    Subject gamble(Subject in) throws Exception;
}
