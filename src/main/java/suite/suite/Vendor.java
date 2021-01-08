package suite.suite;

public abstract class Vendor extends Subject {

    @Override
    public Subject set(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject set(Object element, Subject $set) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject setBefore(Object sequent, Object element)  {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Subject $set) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject unset() {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject unset(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject add(Subject $sub) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject insert(Object ... elements) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject take(Object key) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject alter(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject alterBefore(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject takeAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }


}
