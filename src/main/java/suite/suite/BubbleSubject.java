package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class BubbleSubject implements Subject {

    private final Object bubbled;
    private Link ward;

    BubbleSubject(Object bubbled) {
        this.bubbled = bubbled;
    }

    @Override
    public Object key() {
        return bubbled;
    }

    @Override
    public Subject get() {
        return this;
    }

    @Override
    public Subject get(Object key) {
        return Objects.equals(bubbled, key) ? this : ZeroSubject.getInstance();
    }

    @Override
    public Subject get(Object ... keys) {
        for(Object k : keys) {
            if(Objects.equals(bubbled, k)) return this;
        }
        return ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(Slot slot) {
        return slot == Slot.PRIME || slot == Slot.RECENT || (slot instanceof Slot.PlacedSlot && ((Slot.PlacedSlot) slot).place == 0) ?
            this : ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(int slotIndex) {
        return slotIndex == 0 ? this : ZeroSubject.getInstance();
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
        return (B)bubbled;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return (B)bubbled;
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

    @Override
    public String toString() {
        return bubbled + " []";
    }

    @Override
    public Subject set(Object element) {
        return Objects.equals(bubbled, element) ? new BubbleSubject(element) :
                new MultiSubject(link()).set(element, element);
    }

    @Override
    public Subject set(Object key, Object value) {
        return Objects.equals(bubbled, key) ? new CoupleSubject(key, value) :
                new MultiSubject(link()).set(key, value);
    }

    @Override
    public Subject put(Object element) {
        return Objects.equals(bubbled, element) ? this :
                new MultiSubject(link()).set(element, element);
    }

    @Override
    public Subject put(Object key, Object value) {
        return Objects.equals(bubbled, key) ? this :
                new MultiSubject(link()).set(key, value);
    }

    @Override
    public Subject add(Object element) {
        return new MultiSubject(link()).add(element);
    }

    @Override
    public Subject unset(Object key) {
        return Objects.equals(bubbled, key) ? ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        return getAt(slot).notEmpty() ? ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        return Objects.equals(bubbled, element) ? new BubbleSubject(element) :
                new MultiSubject(link()).setAt(slot, element);
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        return Objects.equals(bubbled, key) ? new CoupleSubject(key, value) :
                new MultiSubject(link()).setAt(slot, key, value);
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        return Objects.equals(bubbled, element) ? this :
                new MultiSubject(link()).setAt(slot, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        return Objects.equals(bubbled, key) ? this :
                new MultiSubject(link()).setAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return new MultiSubject(link()).addAt(slot, element);
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
    public Subject exclude() {
        return new BubbleSubject(bubbled);
    }
}
