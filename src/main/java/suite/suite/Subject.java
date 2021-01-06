package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Glass;
import suite.suite.util.Wave;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Subject extends Fluid {

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
    boolean absent();
    int size();
    Wave<Subject> iterator(boolean reverse);
    default Wave<Subject> iterator() {
        return iterator(false);
    }
    default Fluid front() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Fluid reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    Subject set(Object element);
    Subject set(Object element, Subject $set);
    Subject setBefore(Object sequent, Object element);
    Subject setBefore(Object sequent, Object element, Subject $set);
    Subject unset();
    Subject unset(Object element);
    default Subject setIf(Object element, Predicate<Subject> test) {
        return test.test(this) ? set(element) : this;
    }
    default Subject setIfEmpty(Object element) {
        return at(element).absent() ? set(element) : this;
    }
    default Subject add(Subject $sub) {
        return set(new Suite.AutoKey(), $sub);
    }
    default Subject addBefore(Object sequent, Subject $sub) {
        return setBefore(sequent, new Suite.AutoKey(), $sub);
    }
    default Subject inset(Object ... elements) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject take(Object key) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject join(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject joinBefore(Object sequent, Iterable<? extends Subject> iterable) {
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
