package suite.suite.action;

import suite.suite.Vendor;

@FunctionalInterface
public interface DiceyExpression extends Action {

    @Override
    default Vendor play() {
        try {
            return gamble();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Vendor play(Vendor in) {
        try {
            return gamble();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Vendor gamble() throws Exception;

    @Override
    default Vendor gamble(Vendor in) throws Exception {
        return gamble();
    }
}
