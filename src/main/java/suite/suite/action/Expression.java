package suite.suite.action;

import suite.suite.Vendor;
import suite.suite.Suite;

import java.util.function.Supplier;

@FunctionalInterface
public interface Expression extends Action {

    static Expression fromSupplier(Supplier<?> supplier) {
        return () -> Suite.set(supplier.get());
    }

    Vendor play();

    default Vendor play(Vendor in) {
        return play();
    }

    default Vendor gamble() throws Exception {
        return play();
    }

    default Vendor gamble(Vendor in) throws Exception {
        return play();
    }
}
