package suite.suite;

import suite.suite.util.*;

import java.util.function.Supplier;

class MultiSubject extends Subject {

    private final Chain chain;

    MultiSubject() {
        this.chain = new Chain();
    }

    MultiSubject(Link ward) {
        super(ward.front.subject.origin);
        this.chain = new Chain(ward);
    }

    @Override
    protected Subject materialize() {
        var link = chain.first();
        if(link == chain.ward) {
            chain.put(new Suite.Auto(), Suite.set());
        } else {
            link.subject = link.subject.materialize();
        }
        return this;
    }

    @Override
    protected Subject materialize(Object element) {
        var link = chain.get(element);
        if(link == chain.ward) {
            chain.put(element, Suite.set());
        } else {
            link.subject = link.subject.materialize(element);
        }
        return this;
    }

    @Override
    protected Subject jump() {
        return chain.first().subject.jump();
    }

    @Override
    protected Subject jump(Object element) {
        return chain.get(element).subject.jump(element);
    }

    @Override
    protected boolean real(Object element) {
        return chain.get(element).subject.real(element);
    }

    @Override
    public Subject getFirst() {
        return chain.first().subject;
    }

    @Override
    public Subject getLast() {
        return chain.getLast().subject;
    }

    @Override
    public Subject get(Object element) {
        return chain.get(element).subject;
    }

    @Override
    public Object direct() {
        return chain.first().subject.direct();
    }
    
    @Override
    public <B> B asExpected() {
        return chain.first().subject.asExpected();
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        return chain.first().subject.as(requestedType);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return chain.first().subject.as(requestedType);
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        return chain.first().subject.as(requestedType, reserve);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return chain.first().subject.as(requestedType, reserve);
    }

    @Override
    public <B> B orGiven(B reserve) {
        return chain.first().subject.orGiven(reserve);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return chain.first().subject.orDo(supplier);
    }

    @Override
    public boolean is(Class<?> type) {
        return chain.first().subject.is(type);
    }

    @Override
    public boolean present() {
        return chain.size() > 0;
    }

    @Override
    public boolean present(Object element) {
        return chain.get(element) != chain.ward;
    }

    @Override
    public boolean absent() {
        return chain.size() == 0;
    }

    @Override
    public boolean absent(Object element) {
        return chain.get(element) == chain.ward;
    }

    @Override
    public int size() {
        return chain.size();
    }

    @Override
    public Browser iterator(boolean reverse) {
        return chain.iterator(reverse);
    }

    @Override
    public Subject set(Object element) {
        chain.put(element);
        return this;
    }

    @Override
    public Subject set(Object element, Subject $set) {
        chain.put(element, $set);
        return this;
    }

    @Override
    public Subject strictSet(Object sequent, Object element) {
        chain.put(chain.get(sequent), element);
        return this;
    }

    @Override
    public Subject strictSet(Object sequent, Object element, Subject $set) {
        chain.put(chain.get(sequent), element, $set);
        return this;
    }

    @Override
    public Subject shift(Object out, Object in) {
        chain.push(out, in);
        return this;
    }

    @Override
    public Subject unset() {
        chain.clear();
        return this;
    }

    @Override
    public Subject unset(Object element) {
        chain.remove(element);
        return this;
    }

    @Override
    public Subject separate() {
        return this;
    }
}
