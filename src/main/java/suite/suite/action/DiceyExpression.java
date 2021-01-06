package suite.suite.action;

import suite.suite.Subject;

@FunctionalInterface
public interface DiceyExpression extends Action {

    @Override
    default Subject play() {
        try {
            return gamble();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Subject play(Subject in) {
        try {
            return gamble();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    Subject gamble() throws Exception;

    @Override
    default Subject gamble(Subject in) throws Exception {
        return gamble();
    }
}
