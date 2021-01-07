package suite.suite;

import suite.suite.util.Series;
import suite.suite.util.Glass;
import suite.suite.util.Browser;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public interface Subject extends Series {

    Subject at(Object element);
    default Subject at() {
        return at(new Suite.AutoKey());
    }
    Subject burn(Object element);
    Subject jump(Object element);
    boolean burned(Object element);

    Subject getFirst();
    Subject getLast();
    Subject get(Object element);
    Object direct();
    <B> B asExpected();
    <B> B as(Class<B> requestedType);
    <B> B as(Glass<? super B, B> requestedType);
    <B> B as(Class<B> requestedType, B reserve);
    <B> B as(Glass<? super B, B> requestedType, B reserve);
    <B> B orGiven(B reserve);
    <B> B orDo(Supplier<B> supplier);
    boolean is(Class<?> type);
    boolean present();
    boolean present(Object element);
    boolean absent();
    boolean absent(Object element);
    int size();
    Browser<Subject> iterator(boolean reverse);
    default Browser<Subject> iterator() {
        return iterator(false);
    }
    default Series front() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Series reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    Subject set(Object element);
    Subject set(Object element, Subject $set);
    Subject setBefore(Object sequent, Object element);
    Subject setBefore(Object sequent, Object element, Subject $set);
    Subject unset();
    Subject unset(Object element);
    default Subject setIf(Object element, BiPredicate<Subject, Object> tester) {
        return tester.test(this, element) ? set(element) : this;
    }
    default Subject setIf(Object element, Subject $sub, BiPredicate<Subject, Object> tester) {
        return tester.test(this, element) ? set(element, $sub) : this;
    }
    default Subject add(Subject $sub) {
        return set(new Suite.AutoKey(), $sub);
    }
    default Subject addBefore(Object sequent, Subject $sub) {
        return setBefore(sequent, new Suite.AutoKey(), $sub);
    }
    default Subject insert(Object ... elements) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject take(Object key) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject alter(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject alterBefore(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject getAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject takeAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    @Override
    default Subject set() {
        return this;
    }
    Subject separate();
}
