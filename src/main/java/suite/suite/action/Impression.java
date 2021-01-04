package suite.suite.action;

import suite.suite.Vendor;
import suite.suite.Suite;

@FunctionalInterface
public interface Impression extends Action {

    void revel(Vendor in);

    @Override
    default Vendor play() {
        revel(Suite.set());
        return Suite.set();
    }

    @Override
    default Vendor play(Vendor in) {
        revel(in);
        return Suite.set();
    }

    @Override
    default Vendor gamble() throws Exception {
        revel(Suite.set());
        return Suite.set();
    }

    @Override
    default Vendor gamble(Vendor in) throws Exception {
        revel(in);
        return Suite.set();
    }
}
