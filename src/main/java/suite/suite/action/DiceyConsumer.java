package suite.suite.action;

import java.util.function.Consumer;

@FunctionalInterface
public interface DiceyConsumer<T> extends Consumer<T> {

    void strive(T t) throws Exception;

    @Override
    default void accept(T t) {
        try {
            strive(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
