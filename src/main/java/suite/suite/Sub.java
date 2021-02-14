package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;
import suite.suite.util.Series;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Sub extends Series {

    Subject get();
    Subject set();

    default Sub up() {
        return new FirstSub(this);
    }

    default Sub up(Object element) {
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


    default Subject set(Object element, Subject $set) {
        return set().set(element, $set);
    }


    default Subject strictSet(Object sequent, Object element) {
        return set().strictSet(sequent, element);
    }


    default Subject strictSet(Object sequent, Object element, Subject $set) {
        return set().strictSet(sequent, element, $set);
    }

    default Subject shift(Object out, Object in) {
        return set().shift(out, in);
    }


    default Subject unset() {
        return set().unset();
    }


    default Subject unset(Object element) {
        return set().unset(element);
    }

    default Sub setIf(Predicate<Subject> tester, Object element) {
        return tester.test(get()) ? set(element) : this;
    }

    default Sub setIf(Predicate<Subject> tester, Object element, Subject $sub) {
        return tester.test(get()) ? set(element, $sub) : this;
    }

    default Sub useIf(Predicate<Subject> tester, Supplier<?> supplier) {
        return tester.test(get()) ? set(supplier.get()) : this;
    }
}
