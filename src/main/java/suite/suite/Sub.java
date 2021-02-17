package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;
import suite.suite.util.Series;

import java.util.function.Supplier;

public interface Sub extends Series {

    Subject get();
    Subject set();

    default Sub in() {
        return new FirstSub(this);
    }

    default Sub in(Object element) {
        return new FrontierSub(this, element);
    }

    default Subject first() {
        return get().first();
    }


    default Subject last() {
        return get().last();
    }


    default Subject get(Object element) {
        return get().get(element);
    }


    default Object direct() {
        return get().direct();
    }


    default <B> B asExpected() {
        return get().asExpected();
    }


    default <B> B as(Class<B> requestedType) {
        return get().as(requestedType);
    }


    default <B> B as(Glass<? super B, B> requestedType) {
        return get().as(requestedType);
    }


    default <B> B as(Class<B> requestedType, B reserve) {
        return get().as(requestedType, reserve);
    }


    default <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return get().as(requestedType, reserve);
    }


    default <B> B orGiven(B reserve) {
        return get().orGiven(reserve);
    }


    default <B> B orDo(Supplier<B> supplier) {
        return get().orDo(supplier);
    }


    default boolean is(Class<?> type) {
        return get().is(type);
    }


    default boolean present() {
        return get().present();
    }


    default boolean present(Object element) {
        return get().present(element);
    }


    default boolean absent() {
        return get().absent();
    }


    default boolean absent(Object element) {
        return get().absent(element);
    }


    default int size() {
        return get().size();
    }


    default Browser iterator(boolean reverse) {
        return get().iterator(reverse);
    }

    default Browser iterator() {
        return iterator(false);
    }

    default Subject set(Object element) {
        return set().set(element);
    }
    default Subject exactSet(Object aim, Object element) {
        return set().exactSet(aim,element);
    }
    default Subject set(Object key, Object value, Object ... rest) {
        return set().set(key, value, rest);
    }
    default Subject exactSet(Object aim, Object key, Object value, Object ... rest) {
        return set().exactSet(aim, key, value, rest);
    }
    default Subject inset(Object element, Subject $set) {
        return set().inset(element, $set);
    }
    default Subject exactInset(Object aim, Object element, Subject $set) {
        return set().exactInset(aim, element, $set);
    }
    default Subject put(Object element) {
        return set().put(element);
    }
    default Subject exactPut(Object aim, Object element) {
        return set().exactPut(aim, element);
    }
    default Subject put(Object value, Object ... rest) {
        return set().put(value, rest);
    }
    default Subject exactPut(Object aim, Object value, Object ... rest) {
        return set().exactPut(aim, value, rest);
    }
    default Subject input(Subject $set) {
        return set().input($set);
    }
    default Subject exactInput(Object target, Subject $set) {
        return set().exactInput(target, $set);
    }

    default Subject merge(Subject $tree) {
        return set().merge($tree);
    }

    default Subject shift(Object out, Object in) {
        return set().shift(out, in);
    }

    default Subject reset(Object element) {
        return set().reset(element);
    }

    default Subject unset() {
        return set().unset();
    }


    default Subject unset(Object element) {
        return set().unset(element);
    }
}
