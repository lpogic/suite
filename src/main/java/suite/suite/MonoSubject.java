package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class MonoSubject extends Subject {

    private final Object element;
    private Subject subject;
    private Link ward;

    public MonoSubject(Object element) {
        this.element = element;
        this.subject = Suite.set();
    }

    public MonoSubject(Object element, Subject subject) {
        this.element = element;
        this.subject = subject;
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
    protected Subject materialize() {
        return this;
    }

    @Override
    protected Subject materialize(Object element) {
        return Objects.equals(this.element, element) ?
                this : new MultiSubject(link()).materialize(element);
    }

    @Override
    protected Subject jump() {
        return subject;
    }

    @Override
    protected Subject jump(Object element) {
        return Objects.equals(this.element, element) ?
                subject : Suite.set();
    }

    @Override
    protected boolean real(Object element) {
        return Objects.equals(this.element, element);
    }

    @Override
    protected int origin() {
        return hashCode();
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
        return Objects.equals(this.element, element) ? this : ZeroSubject.getInstance();
    }

    @Override
    public Object direct() {
        return element;
    }

    @Override
    public <B> B asExpected() {
        return (B) element;
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
        return element == null ? substitute : (B) element;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return element == null ? supplier.get() : (B) element;
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

    @Override
    public Browser<Subject> iterator(boolean reverse) {
        link();
        return new LinkIterator(reverse, ward, ward);
    }

    @Override
    public Subject set(Object element) {
        if(Objects.equals(this.element, element)) {
            subject = Suite.set();
            return this;
        } else {
            return new MultiSubject(link()).set(element);
        }
    }

    @Override
    public Subject set(Object element, Subject $set) {
        if(Objects.equals(this.element, element)) {
            subject = $set;
            return this;
        } else {
            return new MultiSubject(link()).set(element, $set);
        }
    }

    @Override
    public Subject setBefore(Object sequent, Object element) {
        if(Objects.equals(this.element, element)) {
            subject = Suite.set();
            return this;
        } else {
            return new MultiSubject(link()).setBefore(sequent, element);
        }
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Subject $set) {
        if(Objects.equals(this.element, element)) {
            subject = $set;
            return this;
        } else {
            return new MultiSubject(link()).setBefore(sequent, element, $set);
        }
    }

    @Override
    public Subject shift(Object out, Object in) {
        if(Objects.equals(this.element, out)) {
            return new MonoSubject(in, subject);
        } else {
            return new MultiSubject(link()).set(in);
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
        return new MonoSubject(element, subject);
    }
}
