package suite.suite;

import java.util.function.Predicate;

public interface Subject extends Vendor {

    Subject set(Object element);
    Subject set(Object element, Vendor $set);
    Subject setBefore(Object sequent, Object element);
    Subject setBefore(Object sequent, Object element, Vendor $set);
    Subject unset();
    Subject unset(Object element);
    default Subject in() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject in(Object element) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject setIf(Object element, Predicate<Vendor> test) {
        return test.test(this) ? set(element) : this;
    }

    default Subject setIfEmpty(Object element) {
        return at(element).isEmpty() ? set(element) : this;
    }

    default Subject add(Subject $sub) {
        return set(new Suite.AutoKey(), $sub);
    }

    default Subject inset(Object ... elements) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Vendor take(Object key) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject join(Iterable<? extends Vendor> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject joinBefore(Object sequent, Iterable<? extends Vendor> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    @Override
    default Subject set() {
        return this;
    }
}
