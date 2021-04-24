package suite.processor;

import suite.suite.Subject;
import suite.suite.Suite;

@FunctionalInterface
public interface BitProcessor {

    void advance(boolean i);

    default void getReady() {}
    default Subject finish() {
        return Suite.set();
    }

    default Subject process(byte ... bytes) {
        getReady();
        for(var b : bytes) {
            for(int i = 0; i < 8; ++i) {
                advance((b & (0x80 >> i)) != 0);
            }
        }
        return finish();
    }

    default Subject process(Iterable<Boolean> it) {
        getReady();
        for(var i : it) {
            advance(i);
        }
        return finish();
    }
}
