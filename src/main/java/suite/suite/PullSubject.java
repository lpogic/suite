package suite.suite;

import suite.suite.util.FluidSubject;
import suite.suite.util.Glass;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class PullSubject implements Subject {

    @Override
    public Subject set(Object key, Object value) {
        return setAt(Slot.RECENT, key, value);
    }

    @Override
    public Subject set(Object element) {
        return set(element, element);
    }

    @Override
    public Subject put(Object element) {
        return put(element, element);
    }

    @Override
    public Subject put(Object key, Object value) {
        return get(key).settled() ? this : set(key, value);
    }

    @Override
    public Subject add(Object element) {
        return set(new Suite.Add(), element);
    }

    @Override
    public Subject unset(Object key, Object value) {
        return Objects.equals(get(key).direct(), value) ? unset(key) : this;
    }

    @Override
    public Subject key() {
        return prime().key();
    }

    @Override
    public Subject prime() {
        return at(Slot.PRIME);
    }

    @Override
    public Subject recent() {
        return at(Slot.RECENT);
    }

    @Override
    public Subject at(int slotIndex) {
        return at(Slot.in(slotIndex));
    }

    @Override
    public Object direct() {
        return prime().direct();
    }

    @Override
    public <B> B asExpected() {
        return prime().asExpected();
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return prime().asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return prime().asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return prime().asGiven(requestedType, substitute);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return prime().asGiven(requestedType, substitute);
    }

    @Override
    public <B> B orGiven(B substitute) {
        return prime().orGiven(substitute);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return prime().orDo(supplier);
    }

    @Override
    public boolean assigned(Class<?> type) {
        return prime().assigned(type);
    }

    @Override
    public boolean settled() {
        return prime().settled();
    }

    @Override
    public boolean desolated() {
        return prime().desolated();
    }

    @Override
    public Subject homogenize() {
        return this;
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject saved = get(key);
        if(saved.settled())return saved;
        set(key, reserve);
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject done = get(key);
        if(done.settled())return done;
        set(key, supplier.get());
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject done = get(key);
        if(done.settled())return done;
        set(key, function.apply(argument));
        return get(key);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        unset(key);
        return taken;
    }

    @Override
    public boolean homogeneous() {
        return true;
    }

    @Override
    public abstract Subject unset();

    @Override
    public FluidSubject front() {
        return frontFrom(Slot.PRIME);
    }

    @Override
    public FluidSubject reverse() {
        return reverseFrom(Slot.RECENT);
    }

    @Override
    public abstract FluidSubject front(Object fromKeyIncluded);

    @Override
    public abstract FluidSubject reverse(Object fromKeyIncluded);

    @Override
    public abstract FluidSubject frontFrom(Slot fromSlotIncluded);

    @Override
    public abstract FluidSubject reverseFrom(Slot fromSlotIncluded);

    @Override
    public Subject setAt(Slot slot, Object element) {
        return setAt(slot, element, element);
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        return putAt(slot, element, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        return get(key).settled() ? this : setAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.Add(), element);
    }
}
