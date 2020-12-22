package suite.suite;

import suite.suite.util.*;

import java.util.function.Supplier;

class MultiSubject implements Subject {

    private final Chain chain;

    MultiSubject() {
        this.chain = new Chain();
    }

    MultiSubject(Link ward) {
        this.chain = new Chain(ward);
    }

    @Override
    public Subject atFirst() {
        return chain.getFirst().subject;
    }

    @Override
    public Subject atLast() {
        return chain.getLast().subject;
    }

    @Override
    public Subject at(Object element) {
        return chain.get(element).subject;
    }

    @Override
    public Subject get() {
        return atFirst().get();
    }

    @Override
    public Subject get(Object element) {
        return at(element).get();
    }

    @Override
    public Object direct() {
        return chain.getFirst().subject.direct();
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
    public boolean instanceOf(Class<?> type) {
        return chain.getFirst().subject.instanceOf(type);
    }

    @Override
    public boolean notEmpty() {
        return chain.size() > 0;
    }

    @Override
    public boolean isEmpty() {
        return chain.size() == 0;
    }

    @Override
    public int size() {
        return chain.size();
    }

    @Override
    public Wave<Subject> iterator(boolean reverse) {
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
    public Subject setBefore(Object sequent, Object element) {
        chain.put(chain.get(sequent), element);
        return this;
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Subject $set) {
        chain.put(chain.get(sequent), element, $set);
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
}
