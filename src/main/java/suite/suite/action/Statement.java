package suite.suite.action;

import suite.suite.Vendor;
import suite.suite.Suite;

@FunctionalInterface
public interface Statement extends Impression {

    void revel();

    @Override
    default void revel(Vendor in) {
        revel();
    }

    default Vendor play() {
        revel();
        return Suite.set();
    }

    default Vendor play(Vendor in) {
        revel();
        return Suite.set();
    }

    default Vendor gamble() {
        revel();
        return Suite.set();
    }

    default Vendor gamble(Vendor in) {
        revel();
        return Suite.set();
    }
}
