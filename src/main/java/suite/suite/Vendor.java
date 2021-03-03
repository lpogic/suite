package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;

import java.util.Iterator;
import java.util.function.Supplier;

public abstract class Vendor extends Subject {

    final Subject $sub;

    public Vendor(Subject $sub) {
        this.$sub = $sub;
    }

    protected Subject monoWrap(Subject $) {
        if($.absent()) return $;
        return Suite.inset($.raw(), wrap($.in().get()));
    }

    @Override
    protected Subject materialize() {
        throw new UnsupportedOperationException("Vendor is immutable");
    }
    @Override
    protected Subject materialize(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    protected abstract Subject wrap(Subject $);

    protected abstract Subject factor(Subject $);

    protected abstract Subject factor(Subject $, Class<?> type);

    @Override
    protected Subject jump() {
        return wrap($sub.jump());
    }

    @Override
    protected Subject jump(Object o) {
        return wrap($sub.jump(o));
    }

    @Override
    protected boolean real(Object element) {
        return $sub.real(element);
    }

    @Override
    public Subject first() {
        return monoWrap($sub.first());
    }

    @Override
    public Subject last() {
        return monoWrap($sub.last());
    }

    @Override
    public Subject get(Object o) {
        return monoWrap($sub.get(o));
    }

    @Override
    public Object raw() {
        return factor($sub).raw();
    }

    @Override
    public <B> B asExpected() {
        return factor($sub).asExpected();
    }

    @Override
    public <B> B as(Class<B> aClass) {
        return factor($sub, aClass).as(aClass);
    }

    @Override
    public <B> B as(Glass<? super B, B> glass) {
        return factor($sub, glass.getMainClass()).as(glass);
    }

    @Override
    public <B> B as(Class<B> aClass, B b) {
        return factor($sub, aClass).as(aClass, b);
    }

    @Override
    public <B> B as(Glass<? super B, B> glass, B b) {
        return factor($sub, glass.getMainClass()).as(glass, b);
    }

    @Override
    public <B> B orGiven(B b) {
        return factor($sub).orGiven(b);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return factor($sub).orDo(supplier);
    }

    @Override
    public boolean is(Class<?> aClass) {
        return factor($sub).is(aClass);
    }

    @Override
    public boolean present() {
        return factor($sub).present();
    }

    @Override
    public boolean present(Object o) {
        return get(o).present();
    }

    @Override
    public boolean absent() {
        return factor($sub).absent();
    }

    @Override
    public boolean absent(Object o) {
        return get(o).absent();
    }

    @Override
    public int size() {
        return $sub.size();
    }

    @Override
    public Browser iterator(boolean b) {
        return new Browser() {
            final Iterator<Subject> source = $sub.iterator(b);

            @Override
            public boolean hasNext() {
                return source.hasNext();
            }

            @Override
            public Subject next() {
                return monoWrap(source.next());
            }
        };
    }

    @Override
    Subject separate() {
        return this;
    }

    @Override
    public Subject set(Object element) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject aimedSet(Object aim, Object element)  {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject inset(Object in, Subject $set) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject aimedInset(Object aim, Object in, Subject $set) {
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
    public Subject shift(Object out, Object in) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject inset(Subject $set) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject arm(Object e1, Object e2, Object... rest) {
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
    public Subject aimedAlter(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject setEntire(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject unsetEntire(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }

    @Override
    public Subject takeEntire(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Vendor is immutable");
    }


}
