package suite.suite.action;

import suite.suite.Vendor;
import suite.suite.Suite;

@FunctionalInterface
public interface DiceyImpression extends Action {

    void strive(Vendor in) throws Exception;

    @Override
    default Vendor play() {
        try {
            strive(Suite.set());
            return Suite.set();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Vendor play(Vendor in) {
        try {
            strive(in);
            return Suite.set();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Vendor gamble() throws Exception {
        strive(Suite.set());
        return Suite.set();
    }

    @Override
    default Vendor gamble(Vendor in) throws Exception {
        strive(in);
        return Suite.set();
    }
}
