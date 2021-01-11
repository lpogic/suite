package suite.suite;

import suite.suite.util.Sequence;
import suite.suite.util.Series;
import suite.suite.util.Glass;
import suite.suite.util.Browser;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public abstract class Subject implements Sub {

    protected abstract Subject materialize();
    protected abstract Subject materialize(Object element);
    protected abstract Subject jump();
    protected abstract Subject jump(Object element);
    protected abstract boolean real(Object element);

    public abstract Subject getFirst();
    public abstract Subject getLast();
    public abstract Subject get(Object element);
    public Subject get(Object... elements) {
        return getAll(Sequence.of(elements)).set();
    }
    public abstract Object direct();
    public abstract <B> B asExpected();
    public abstract <B> B as(Class<B> requestedType);
    public abstract <B> B as(Glass<? super B, B> requestedType);
    public abstract <B> B as(Class<B> requestedType, B reserve);
    public abstract <B> B as(Glass<? super B, B> requestedType, B reserve);
    public abstract <B> B orGiven(B reserve);
    public abstract <B> B orDo(Supplier<B> supplier);
    public abstract boolean is(Class<?> type);
    public abstract boolean present();
    public abstract boolean present(Object element);
    public abstract boolean absent();
    public abstract boolean absent(Object element);
    public abstract int size();
    public abstract Browser<Subject> iterator(boolean reverse);
    public Browser<Subject> iterator() {
        return iterator(false);
    }
    public Series front() {
        throw new UnsupportedOperationException("Solid method");
    }
    public Series reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    public abstract Subject set(Object element);
    public abstract Subject set(Object element, Subject $set);
    public abstract Subject setBefore(Object sequent, Object element);
    public abstract Subject setBefore(Object sequent, Object element, Subject $set);
    public abstract Subject shift(Object out, Object in);
    public abstract Subject unset();
    public abstract Subject unset(Object element);
    public Subject setIf(Object element, BiPredicate<Subject, Object> tester) {
        return tester.test(this, element) ? set(element) : this;
    }
    public Subject setIf(Object element, Subject $sub, BiPredicate<Subject, Object> tester) {
        return tester.test(this, element) ? set(element, $sub) : this;
    }
    public Subject add(Subject $sub) {
        return set(new Suite.Auto(), $sub);
    }
    public Subject addBefore(Object sequent, Subject $sub) {
        return setBefore(sequent, new Suite.Auto(), $sub);
    }
    public Subject insert(Object ... elements) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject take(Object element) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject take(Object... elements) {
        return takeAll(Sequence.of(elements)).set();
    }
    public Subject alter(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject alterBefore(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    public Series getAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Series takeAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    @Override
    public Subject set() {
        return this;
    }
    @Override
    public Subject get() {
        return this;
    }
    abstract Subject separate();
}
