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
        var link = chain.getFirst();
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
        return chain.getFirst().subject.jump();
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
    public Subject first() {
        return chain.getFirst().subject;
    }

    @Override
    public Subject last() {
        return chain.getLast().subject;
    }

    @Override
    public Subject get(Object element) {
        return chain.get(element).subject;
    }

    @Override
    public Object raw() {
        return chain.getFirst().subject.raw();
    }
    
    @Override
    public <B> B asExpected() {
        return chain.getFirst().subject.asExpected();
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        return chain.getFirst().subject.as(requestedType);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return chain.getFirst().subject.as(requestedType);
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        return chain.getFirst().subject.as(requestedType, reserve);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return chain.getFirst().subject.as(requestedType, reserve);
    }

    @Override
    public <B> B orGiven(B reserve) {
        return chain.getFirst().subject.orGiven(reserve);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return chain.getFirst().subject.orDo(supplier);
    }

    @Override
    public boolean is(Class<?> type) {
        return chain.getFirst().subject.is(type);
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
    public Browser browser(Object start, boolean reverse) {
        var link = chain.get(start);
        if(link == chain.ward) return iterator(reverse);
        return reverse ? chain.iterator(true, link.back) :
                chain.iterator(false, link.front);
    }

    @Override
    public Subject set(Object element) {
        chain.put(element);
        return this;
    }

    @Override
    public Subject aimedSet(Object aim, Object element) {
        chain.put(chain.get(aim), element);
        return this;
    }

    @Override
    public Subject inset(Object in, Subject $set) {
        chain.put(in, $set);
        return this;
    }

    @Override
    public Subject aimedInset(Object aim, Object in, Subject $set) {
        chain.put(chain.get(aim), in, $set);
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
