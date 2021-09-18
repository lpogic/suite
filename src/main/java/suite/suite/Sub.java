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

    default  <B> B one() {
        return get().one();
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
    default Subject put(Object e1, Object ... en) {
        return set().put(e1, en);
    }
    default Subject aimedPut(Object aim, Object e1, Object ... en) {
        return set().aimedPut(aim, e1, en);
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
    default Subject sate(Object element) {
        return set().sate(element);
    }
    default Subject sate(Object element, Subject $set) {
        return set().sate(element, $set);
    }
    default Subject take(Object element) {
        return set().take(element);
    }
    default Subject alter(Iterable<? extends Subject> iterable) {
        return set().alter(iterable);
    }
    default Subject aimedAlter(Object sequent, Iterable<? extends Subject> iterable) {
        return set().aimedAlter(sequent, iterable);
    }

    default Subject merge(Subject $tree) {
        return set().merge($tree);
    }

    default Subject swap(Object o1, Object o2) {
        return set().swap(o1, o2);
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
