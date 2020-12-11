package suite.suite;

import suite.suite.util.Glass;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SimpleSubject implements Subject {

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
        return get(key).isNotEmpty() ? this : set(key, value);
    }

    @Override
    public Subject add(Object element) {
        return set(new Suite.AutoKey(), element);
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
        return getAt(Slot.PRIME);
    }

    @Override
    public Subject recent() {
        return getAt(Slot.RECENT);
    }

    @Override
    public Subject getAt(int slotIndex) {
        return getAt(Slot.in(slotIndex));
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
    public boolean instanceOf(Class<?> type) {
        return prime().instanceOf(type);
    }

    @Override
    public boolean isNotEmpty() {
        return prime().isNotEmpty();
    }

    @Override
    public boolean isEmpty() {
        return prime().isEmpty();
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject saved = get(key);
        if(saved.isNotEmpty())return saved;
        set(key, reserve);
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject done = get(key);
        if(done.isNotEmpty())return done;
        set(key, supplier.get());
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject done = get(key);
        if(done.isNotEmpty())return done;
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
    public Subject takeAt(Slot slot) {
        Subject taken = getAt(slot);
        unsetAt(slot);
        return taken;
    }

    @Override
    public abstract Subject unset();

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
        return get(key).isNotEmpty() ? this : setAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.AutoKey(), element);
    }
}
