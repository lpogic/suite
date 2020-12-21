package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class BubbleSubject implements Subject {

    private final Object bubbled;
    private Subject subject;
    private Link ward;

    public BubbleSubject(Object bubbled) {
        this.bubbled = bubbled;
        this.subject = Suite.set();
    }

    public BubbleSubject(Object bubbled, Subject subject) {
        this.bubbled = bubbled;
        this.subject = subject;
    }

    @Override
    public Subject atFirst() {
        return this;
    }

    @Override
    public Subject atLast() {
        return this;
    }

    @Override
    public Subject at(Object element) {
        return Objects.equals(bubbled, element) ? this : ZeroSubject.getInstance();
    }

    @Override
    public Subject get() {
        return subject;
    }

    @Override
    public Subject get(Object element) {
        return Objects.equals(bubbled, element) ? subject : ZeroSubject.getInstance();
    }

    @Override
    public Object direct() {
        return bubbled;
    }

    @Override
    public <B> B asExpected() {
        return (B)bubbled;
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return requestedType.cast(bubbled);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return requestedType.cast(bubbled);
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return requestedType.isInstance(bubbled) ? requestedType.cast(bubbled) : substitute;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return requestedType.isInstance(bubbled) ? requestedType.cast(bubbled) : substitute;
    }

    @Override
    public <B> B orGiven(B substitute) {
        return bubbled == null ? substitute : (B)bubbled;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return bubbled == null ? supplier.get() : (B)bubbled;
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return type.isInstance(bubbled);
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
    public Wave<Subject> iterator(boolean reverse) {
        link();
        return new LinkIterator(reverse, ward, ward);
    }

    @Override
    public Subject set(Object element) {
        if(Objects.equals(bubbled, element)) {
            subject = Suite.set();
            return this;
        } else {
            return new MultiSubject(link()).set(element);
        }
    }

    @Override
    public Subject set(Object element, Subject $set) {
        if(Objects.equals(bubbled, element)) {
            subject = $set;
            return this;
        } else {
            return new MultiSubject(link()).set(element, $set);
        }
    }

    @Override
    public Subject unset() {
        return ZeroSubject.getInstance();
    }

    @Override
    public Subject unset(Object element) {
        return Objects.equals(bubbled, element) ? ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject in() {
        return new MultiSubject(link()).in();
    }

    @Override
    public Subject in(Object element) {
        if(Objects.equals(bubbled, element)) {
            return subject;
        } else {
            return new MultiSubject(link()).in(element);
        }
    }

    @Override
    public Subject separate() {
        return new BubbleSubject(bubbled, subject);
    }
}
