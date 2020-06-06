package lpo.suite.action;

import lpo.suite.Subject;
import lpo.suite.Suite;

@FunctionalInterface
public interface Impression extends Action {

    void revel(Subject in);

    @Override
    default Subject play() {
        revel(Suite.set());
        return Suite.set();
    }

    @Override
    default Subject play(Subject in) {
        revel(in);
        return Suite.set();
    }

    @Override
    default Subject gamble() throws Exception {
        revel(Suite.set());
        return Suite.set();
    }

    @Override
    default Subject gamble(Subject in) throws Exception {
        revel(in);
        return Suite.set();
    }
}
