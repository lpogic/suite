package suite.suite;

import java.util.function.Predicate;

public interface Vendor extends Subject {

    default Subject set(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject set(Object element, Subject $set) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject setBefore(Object sequent, Object element)  {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject setBefore(Object sequent, Object element, Subject $set) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject unset() {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject unset(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject in() {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    default Subject in(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject setIf(Object element, Predicate<Subject> test) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject setIfEmpty(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject add(Subject $sub) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject insert(Object ... elements) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject take(Object key) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject alter(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject alterBefore(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    default Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
}
