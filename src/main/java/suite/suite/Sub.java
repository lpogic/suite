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


    default Object raw() {
        return get().raw();
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
    default Subject aimedSet(Object aim, Object element) {
        return set().aimedSet(aim,element);
    }
    default Subject arm(Object e1, Object ... joints) {
        return set().arm(e1, joints);
    }
    default Subject aimedArm(Object aim, Object e1, Object ... rest) {
        return set().aimedArm(aim, e1, rest);
    }
    default Subject add(Object element) {
        return set().add(element);
    }
    default Subject aimedAdd(Object aim, Object element) {
        return set().aimedAdd(aim, element);
    }
    default Subject inset(Object in, Subject $set) {
        return set().inset(in, $set);
    }
    default Subject aimedInset(Object aim, Object in, Subject $set) {
        return set().aimedInset(aim, in, $set);
    }
    default Subject inset(Subject $set) {
        return set().inset($set);
    }
    default Subject aimedInset(Object aim, Subject $set) {
        return set().aimedInset(aim, $set);
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
