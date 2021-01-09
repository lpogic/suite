package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

class ZeroSubject extends Subject {

    private static final ZeroSubject instance = new ZeroSubject();

    static ZeroSubject getInstance() {
        return instance;
    }

    private ZeroSubject() {}

    @Override
    protected Subject materialize() {
        return new MonoSubject(new Suite.Auto());
    }

    @Override
    protected Subject materialize(Object element) {
        return new MonoSubject(element);
    }

    @Override
    protected Subject jump() {
        return Suite.set();
    }

    @Override
    protected Subject jump(Object element) {
        return Suite.set();
    }

    @Override
    protected boolean real(Object element) {
        return false;
    }

    @Override
    public Subject getFirst() {
        return this;
    }

    @Override
    public Subject getLast() {
        return this;
    }

    @Override
    public Subject get(Object element) {
        return this;
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
    public boolean is(Class<?> type) {
        return false;
    }

    @Override
    public boolean present() {
        return false;
    }

    @Override
    public boolean present(Object element) {
        return false;
    }

    @Override
    public boolean absent() {
        return true;
    }

    @Override
    public boolean absent(Object element) {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Browser<Subject> iterator(boolean reverse) {
        return Browser.empty();
    }

    @Override
    public Subject set(Object element) {
        return new MonoSubject(element);
    }

    @Override
    public Subject set(Object element, Subject $set) {
        return new MonoSubject(element, $set);
    }

    @Override
    public Subject setBefore(Object sequent, Object element) {
        return new MonoSubject(element);
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Subject $set) {
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

    @Override
    public Subject separate() {
        return this;
    }
}
