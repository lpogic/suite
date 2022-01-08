package suite.suite.action;

import suite.suite.Subject;
import suite.suite.Suite;

@FunctionalInterface
public interface Statement extends Impression, Runnable {

    @Override
    default void run() {
        revel();
    }

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

    default Subject gamble() throws Exception {
        revel();
        return Suite.set();
    }

    default Subject gamble(Subject in) throws Exception {
        revel();
        return Suite.set();
    }

    static void vain() {}
}
