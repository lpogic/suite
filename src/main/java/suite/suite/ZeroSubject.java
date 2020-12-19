package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

class ZeroSubject implements Subject {

    private static final ZeroSubject instance = new ZeroSubject();

    static ZeroSubject getInstance() {
        return instance;
    }

    private ZeroSubject() {}

    @Override
    public Object key() {
        throw new NullPointerException("Zero subject is empty");
    }

    @Override
    public Subject get() {
        return this;
    }

    @Override
    public Subject get(Object key) {
        return this;
    }

    @Override
    public Subject get(Object ... keys) {
        return this;
    }

    @Override
    public Subject getAt(Slot slot) {
        return this;
    }

    @Override
    public Subject getAt(int slotIndex) {
        return this;
    }

    @Override
    public Object direct() {
        return null;
    }

    @Override
    public <B> B asExpected() {
        throw new NoSuchElementException("ZeroSubject contains no values");
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        throw new NoSuchElementException("ZeroSubject contains no values");
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        throw new NoSuchElementException("ZeroSubject contains no values");
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return substitute;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return substitute;
    }

    @Override
    public <B> B orGiven(B reserve) {
        return reserve;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return supplier.get();
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return false;
    }

    @Override
    public boolean notEmpty() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "[]";
    }

    @Override
    public Wave<Subject> iterator(boolean reverse) {
        return Wave.emptyWave();
    }

    @Override
    public Subject set(Object element) {
        return new BubbleSubject(element);
    }

    @Override
    public Subject set(Object key, Object value) {
        return key == value ? set(key) : new CoupleSubject(key, value);
    }

    @Override
    public Subject put(Object element) {
        return set(element);
    }

    @Override
    public Subject put(Object key, Object value) {
        return set(key, value);
    }

    @Override
    public Subject add(Object element) {
        return new CoupleSubject(new Suite.AutoKey(), element);
    }

    @Override
    public Subject unset(Object key) {
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        return slot == Slot.PRIME || slot == Slot.RECENT || slot instanceof Slot.PlacedSlot ?
                set(element) : this;
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        return slot == Slot.PRIME || slot == Slot.RECENT || slot instanceof Slot.PlacedSlot ?
            set(key, value) : this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        return setAt(slot, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        return setAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.AutoKey(), element);
    }
}
