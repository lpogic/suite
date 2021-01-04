package suite.suite;

import suite.suite.util.Glass;
import suite.suite.util.Wave;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class BasicSubject implements Subject {

    private final Object element;
    private Link ward;

    public BasicSubject(Object element) {
        this.element = element;
    }

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
        return Objects.equals(this.element, element) ? this : ZeroSubject.getInstance();
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
        return element;
    }

    @Override
    public <B> B asExpected() {
        return (B)element;
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        return requestedType.cast(element);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return requestedType.cast(element);
    }

    @Override
    public <B> B as(Class<B> requestedType, B substitute) {
        return requestedType.isInstance(element) ? requestedType.cast(element) : substitute;
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B substitute) {
        return requestedType.isInstance(element) ? requestedType.cast(element) : substitute;
    }

    @Override
    public <B> B orGiven(B substitute) {
        return element == null ? substitute : (B)element;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return element == null ? supplier.get() : (B)element;
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return type.isInstance(element);
    }

    @Override
    public boolean notEmpty() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    private Link link() {
        if(ward == null) {
            ward = new Link();
            Link link = new Link(ward, ward, this);
            ward.front = ward.back = link;
        }
        return ward;
    }

    @Override
    public Wave<Vendor> iterator(boolean reverse) {
        link();
        return new LinkIterator(reverse, ward, ward);
    }

    @Override
    public Subject set(Object element) {
        if(Objects.equals(this.element, element)) {
            return this;
        } else {
            return new MultiSubject(link()).set(element);
        }
    }

    @Override
    public Subject set(Object element, Vendor $set) {
        if(Objects.equals(this.element, element)) {
            return new MonoSubject(element, $set);
        } else {
            return new MultiSubject(link()).set(element, $set);
        }
    }

    @Override
    public Subject setBefore(Object sequent, Object element) {
        if(Objects.equals(this.element, element)) {
            return this;
        } else {
            return new MultiSubject(link()).setBefore(sequent, element);
        }
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Vendor $set) {
        if(Objects.equals(this.element, element)) {
            return new MonoSubject(element, $set);
        } else {
            return new MultiSubject(link()).setBefore(sequent, element, $set);
        }
    }

    @Override
    public Subject unset() {
        return ZeroSubject.getInstance();
    }

    @Override
    public Subject unset(Object element) {
        return Objects.equals(this.element, element) ? ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject separate() {
        return new BasicSubject(element);
    }
}
