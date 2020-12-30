package suite.suite;

import suite.suite.util.*;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Subject extends Fluid {

    Subject atFirst();
    Subject atLast();
    Subject at(Object element);
    Subject get();
    Subject get(Object element);
    Object direct();
    <B> B asExpected();
    <B> B as(Class<B> requestedType);
    <B> B as(Glass<? super B, B> requestedType);
    <B> B as(Class<B> requestedType, B reserve);
    <B> B as(Glass<? super B, B> requestedType, B reserve);
    <B> B orGiven(B reserve);
    <B> B orDo(Supplier<B> supplier);
    boolean instanceOf(Class<?> type);
    boolean notEmpty();
    boolean isEmpty();
    int size();
    Wave<Subject> iterator(boolean reverse);
    Subject set(Object element);
    Subject set(Object element, Subject $set);
    Subject setBefore(Object sequent, Object element);
    Subject setBefore(Object sequent, Object element, Subject $set);
    Subject unset();
    Subject unset(Object element);
    default Subject in() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject in(Object element) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Wave<Subject> iterator() {
        return iterator(false);
    }

    default Subject setIf(Object element, Predicate<Subject> test) {
        return test.test(this) ? set(element) : this;
    }

    default Subject setIfEmpty(Object element) {
        return at(element).isEmpty() ? set(element) : this;
    }

    default Subject add(Subject $sub) {
        return set(new Suite.AutoKey(), $sub);
    }

    default Fluid front() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Fluid reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject take(Object key) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject inset(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject insetBefore(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject separate() {
        return this;
    }

}
