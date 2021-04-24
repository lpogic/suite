package suite.processor;

import suite.suite.Subject;
import suite.suite.Suite;

@FunctionalInterface
public interface ByteProcessor {

    void advance(byte i);

    default void getReady() {}
    default Subject finish() {
        return Suite.set();
    }

    default Subject process(byte ... bytes) {
        getReady();
        for(var b : bytes) {
            advance(b);
        }
        return finish();
    }

    default Subject process(Iterable<Byte> it) {
        getReady();
        for(var i : it) {
            advance(i);
        }
        return finish();
    }
}
