package suite.suite;

import suite.suite.util.Glass;
import suite.suite.util.Browser;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class BasicSubject extends Subject {

    private final Object element;
    private Link ward;

    public BasicSubject(Object element) {
        this.element = element;
    }

    public BasicSubject(Object element, int origin) {
        super(origin);
        this.element = element;
    }

    @Override
    protected Subject materialize() {
        return new MonoSubject(this.element);
    }

    @Override
    protected Subject materialize(Object element) {
        return Objects.equals(this.element, element) ?
                new MonoSubject(this.element) : new MultiSubject(link()).materialize(element);
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
    public boolean real(Object element) {
        return false;
    }

    @Override
    public Subject first() {
        return this;
    }

    @Override
    public Subject last() {
        return this;
    }

    @Override
    public Subject get(Object element) {
        return Objects.equals(this.element, element) ? this : ZeroSubject.getInstance();
    }

    @Override
    public Object raw() {
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
    public boolean is(Class<?> type) {
        return type.isInstance(element);
    }

    @Override
    public boolean present() {
        return true;
    }

    @Override
    public boolean present(Object element) {
        return Objects.equals(this.element, element);
    }

    @Override
    public boolean absent() {
        return false;
    }

    @Override
    public boolean absent(Object element) {
        return !Objects.equals(this.element, element);
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
    public Browser iterator(boolean reverse) {
        link();
        return new LinkIterator(reverse, ward, ward);
    }

    @Override
    public Browser browser(Object start, boolean reverse) {
        return iterator(reverse);
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
    public Subject aimedSet(Object aim, Object element) {
        if(Objects.equals(this.element, element)) {
            return this;
        } else {
            return new MultiSubject(link()).aimedSet(aim, element);
        }
    }

    @Override
    public Subject inset(Object in, Subject $set) {
        if(Objects.equals(this.element, in)) {
            return new MonoSubject(in, $set);
        } else {
            return new MultiSubject(link()).inset(in, $set);
        }
    }

    @Override
    public Subject aimedInset(Object aim, Object in, Subject $set) {
        if(Objects.equals(this.element, in)) {
            return new MonoSubject(in, $set);
        } else {
            return new MultiSubject(link()).aimedInset(aim, in, $set);
        }
    }


    @Override
    public Subject swap(Object o1, Object o2) {
        if(Objects.equals(this.element, o1)) {
            return new BasicSubject(o2, origin);
        } else if(Objects.equals(this.element, o2)) {
            return new BasicSubject(o1, origin);
        }
        return this;
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
