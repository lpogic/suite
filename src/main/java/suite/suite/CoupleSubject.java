package suite.suite;

import suite.suite.util.FluidIterator;
import suite.suite.util.FluidSubject;
import suite.suite.util.Glass;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
class CoupleSubject implements Subject {

    final Object primeKey;
    final Object primeValue;

    CoupleSubject(Object primeKey, Object primeValue) {
        this.primeKey = primeKey;
        this.primeValue = primeValue;
    }

    @Override
    public Subject set(Object element) {
        return Objects.equals(primeKey, element) ? new BubbleSubject(element) :
                new MultiSubject().set(primeKey, primeValue).set(element, element);
    }

    @Override
    public Subject set(Object key, Object value) {
        return Objects.equals(primeKey, key) ? new CoupleSubject(key, value) :
                new MultiSubject().set(primeKey, primeValue).set(key, value);
    }

    @Override
    public Subject put(Object element) {
        return Objects.equals(primeKey, element) ? this : new MultiSubject().set(primeKey, primeValue).put(element, element);
    }

    @Override
    public Subject put(Object key, Object value) {
        return Objects.equals(primeKey, key) ? this : new MultiSubject().set(primeKey, primeValue).put(key, value);
    }

    @Override
    public Subject add(Object element) {
        return new MultiSubject().set(primeKey, primeValue).add(element);
    }

    @Override
    public Subject unset(Object key) {
        return Objects.equals(primeKey, key) ? ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject unset(Object key, Object value) {
        return Objects.equals(primeKey, key) && Objects.equals(primeValue, value) ?
                ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        return getAt(slot).settled() ? ZeroSubject.getInstance() : this;
    }

    @Override
    public Subject key() {
        return new BubbleSubject(primeKey);
    }

    @Override
    public Subject prime() {
        return this;
    }

    @Override
    public Subject recent() {
        return this;
    }

    @Override
    public Subject get(Object key) {
        return Objects.equals(primeKey, key) ? new BubbleSubject(primeValue) : ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(Slot slot) {
        if(slot == Slot.PRIME || slot == Slot.RECENT || (slot instanceof Slot.PlacedSlot && ((Slot.PlacedSlot) slot).place == 0)) {
            return this;
        } else if(slot instanceof Slot.RecentBeforeSlot) {
            Predicate<Subject> isLater = ((Slot.RecentBeforeSlot) slot).isLater;
            return isLater.test(this) ? ZeroSubject.getInstance() : this;
        } else if(slot instanceof Slot.PrimeAfterSlot) {
            Predicate<Subject> isEarlier = ((Slot.PrimeAfterSlot) slot).isEarlier;
            return isEarlier.test(this) ? ZeroSubject.getInstance() : this;
        }
        return ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(int slotIndex) {
        return slotIndex == 0 ? this : ZeroSubject.getInstance();
    }

    @Override
    public Object direct() {
        return primeValue;
    }

    @Override
    public <B> B asExpected() {
        return (B)primeValue;
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return (B)primeValue;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return (B)primeValue;
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return requestedType.isInstance(primeValue) ? requestedType.cast(primeValue) : substitute;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return requestedType.isInstance(primeValue) ? requestedType.cast(primeValue) : substitute;
    }

    @Override
    public <B> B orGiven(B substitute) {
        return primeValue == null ? substitute : (B)primeValue;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return primeValue == null ? supplier.get() : (B)primeValue;
    }

    @Override
    public boolean assigned(Class<?> type) {
        return type.isInstance(primeValue);
    }

    @Override
    public boolean settled() {
        return true;
    }

    @Override
    public boolean desolated() {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidSubject front() {
        return () -> new FluidIterator<>() {
            boolean available = true;

            @Override
            public boolean hasNext() {
                return available;
            }

            @Override
            public Subject next() {
                available = false;
                return CoupleSubject.this;
            }
        };
    }

    @Override
    public FluidSubject reverse() {
        return front();
    }

    @Override
    public Subject homogenize() {
        return new MultiSubject().set(primeKey, primeValue);
    }

    @Override
    public String toString() {
        return "[" + primeKey + "]" + primeValue;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        return Objects.equals(primeKey, element) ? new BubbleSubject(element) :
                new MultiSubject().set(primeKey, primeValue).setAt(slot, element, element);
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        return Objects.equals(primeKey, key) ? new CoupleSubject(key, value) :
                new MultiSubject().set(primeKey, primeValue).setAt(slot, key, value);
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        return Objects.equals(primeKey, element) ? this :
                new MultiSubject().set(primeKey, primeValue).setAt(slot, element, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        return Objects.equals(primeKey, key) ? this :
                new MultiSubject().set(primeKey, primeValue).setAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return new MultiSubject().set(primeKey, primeValue).addAt(slot, element);
    }
}
