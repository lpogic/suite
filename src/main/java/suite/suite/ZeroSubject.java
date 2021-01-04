package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

class ZeroSubject implements Subject {

    private static final ZeroSubject instance = new ZeroSubject();

    static ZeroSubject getInstance() {
        return instance;
    }

    private ZeroSubject() {}

    @Override
    public Vendor atFirst() {
        return this;
    }

    @Override
    public Vendor atLast() {
        return this;
    }

    @Override
    public Vendor at(Object element) {
        return this;
    }

    @Override
    public Vendor get() {
        return Suite.set();
    }

    @Override
    public Vendor get(Object element) {
        return Suite.set();
    }

    @Override
    public Object direct() {
        return null;
    }

    @Override
    public <B> B asExpected() {
        throw new NoSuchElementException("ZeroSubject");
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        throw new NoSuchElementException("ZeroSubject");
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        throw new NoSuchElementException("ZeroSubject");
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        return reserve;
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return reserve;
    }

    @Override
    public <B> B orGiven(B reserve) {
        return reserve;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return supplier.get();
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return false;
    }

    @Override
    public boolean notEmpty() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Wave<Vendor> iterator(boolean reverse) {
        return Wave.emptyWave();
    }

    @Override
    public Subject set(Object element) {
        return new MonoSubject(element);
    }

    @Override
    public Subject set(Object element, Vendor $set) {
        return new MonoSubject(element, $set);
    }

    @Override
    public Subject setBefore(Object sequent, Object element) {
        return new MonoSubject(element);
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Vendor $set) {
        return new MonoSubject(element, $set);
    }

    @Override
    public Subject unset() {
        return this;
    }

    @Override
    public Subject unset(Object element) {
        return this;
    }
}
