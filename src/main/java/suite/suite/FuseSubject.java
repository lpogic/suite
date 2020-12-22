package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Fluid;
import suite.suite.util.Glass;

import java.util.function.Supplier;

class FuseSubject {}/* implements Subject {

    private Subject subject;
    private boolean active = true;

    FuseSubject(Subject subject) {
        this.subject = subject;
    }

    private void safe() {
        if(active) {
            subject = Suite.inset(subject.front());
            active = false;
        }
    }

    @Override
    public Subject set(Object element) {
        safe();
        return subject.set(element);
    }

    @Override
    public Subject set(Object key, Object value) {
        safe();
        return subject.set(key, value);
    }

    @Override
    public Subject put(Object element) {
        safe();
        return subject.put(element);
    }

    @Override
    public Subject put(Object key, Object value) {
        safe();
        return subject.put(key, value);
    }

    @Override
    public Subject add(Object element) {
        safe();
        return subject.add(element);
    }

    @Override
    public Subject unset(Object key) {
        safe();
        return subject.unset(key);
    }

    @Override
    public Subject unset(Object key, Object value) {
        safe();
        return subject.unset(key, value);
    }

    @Override
    public Subject unsetAt(Slot slot) {
        safe();
        return subject.unsetAt(slot);
    }

    @Override
    public Subject prime() {
        return subject.prime();
    }

    @Override
    public Subject recent() {
        return subject.recent();
    }

    @Override
    public Subject get(Object key) {
        return subject.get(key);
    }

    @Override
    public Subject get(Object ... keys) {
        return subject.get(keys);
    }

    @Override
    public Subject getAt(Slot slot) {
        return subject.getAt(slot);
    }

    @Override
    public Subject getAt(int slotIndex) {
        return subject.getAt(slotIndex);
    }

    @Override
    public Subject key() {
        return subject.key();
    }

    @Override
    public Object direct() {
        return subject.direct();
    }
    
    @Override
    public <B> B asExpected() {
        return subject.asExpected();
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        return subject.as(requestedType);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return subject.as(requestedType);
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        return subject.as(requestedType, reserve);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return subject.as(requestedType, reserve);
    }

    @Override
    public <B> B orGiven(B reserve) {
        return subject.orGiven(reserve);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return subject.orDo(supplier);
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return subject.instanceOf(type);
    }

    @Override
    public boolean notEmpty() {
        return subject.notEmpty();
    }

    @Override
    public boolean isEmpty() {
        return subject.isEmpty();
    }

    @Override
    public int size() {
        return subject.size();
    }

    @Override
    public Fluid front() {
        return subject.front();
    }

    @Override
    public Fluid reverse() {
        return subject.reverse();
    }

    @Override
    public Wave<Subject> iterator() {
        return subject.iterator();
    }

    @Override
    public boolean fused() {
        return true;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        safe();
        return subject.setAt(slot, element);
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        safe();
        return subject.setAt(slot, key, value);
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        safe();
        return subject.putAt(slot, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        safe();
        return subject.putAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        safe();
        return subject.addAt(slot, element);
    }

    @Override
    public Wave<Subject> iterator(Slot slot, boolean reverse) {
        return subject.iterator(slot, reverse);
    }
}
*/