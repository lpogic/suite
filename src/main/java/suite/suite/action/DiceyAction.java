package suite.suite.action;

import suite.suite.Vendor;
import suite.suite.Suite;

@FunctionalInterface
public interface DiceyAction extends Action {

    @Override
    default Vendor play() {
        try {
            return gamble(Suite.set());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Vendor play(Vendor in) {
        try {
            return gamble(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Vendor gamble() throws Exception {
        return gamble(Suite.set());
    }

    @Override
    Vendor gamble(Vendor in) throws Exception;
}
