package suite.suite.action;

import suite.suite.Vendor;
import suite.suite.Suite;

@FunctionalInterface
public interface DiceyStatement extends DiceyImpression {

    void strive() throws Exception;

    @Override
    default void strive(Vendor in) throws Exception {
        strive();
    }

    default Vendor play() {
        try {
            strive();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Suite.set();
    }

    default Vendor play(Vendor in) {
        try {
            strive();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Suite.set();
    }

    default Vendor gamble() throws Exception {
        strive();
        return Suite.set();
    }

    default Vendor gamble(Vendor in) throws Exception {
        strive();
        return Suite.set();
    }
}
