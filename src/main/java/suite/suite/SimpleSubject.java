package suite.suite;

import suite.suite.util.Glass;

import java.util.function.Supplier;

public interface SimpleSubject extends Subject {

    @Override
    default Object key() {
        return get().key();
    }

    @Override
    default Subject get() {
        return getAt(Slot.PRIME);
    }

    @Override
    default Subject get(Object key) {
        return getAt(Slot.of(key));
    }

    @Override
    default Subject getAt(int slotIndex) {
        return getAt(Slot.in(slotIndex));
    }

    @Override
    default Object direct() {
        return get().direct();
    }

    @Override
    default <B> B asExpected() {
        return get().asExpected();
    }

    @Override
    default <B> B asGiven(Class<B> requestedType) {
        return get().asGiven(requestedType);
    }

    @Override
    default <B> B asGiven(Glass<? super B, B> requestedType) {
        return get().asGiven(requestedType);
    }

    @Override
    default <B> B asGiven(Class<B> requestedType, B substitute) {
        return get().asGiven(requestedType, substitute);
    }

    @Override
    default <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return get().asGiven(requestedType, substitute);
    }

    @Override
    default <B> B orGiven(B substitute) {
        return get().orGiven(substitute);
    }

    @Override
    default <B> B orDo(Supplier<B> supplier) {
        return get().orDo(supplier);
    }

    @Override
    default boolean instanceOf(Class<?> type) {
        return get().instanceOf(type);
    }

    @Override
    default boolean notEmpty() {
        return get().notEmpty();
    }

    @Override
    default boolean isEmpty() {
        return get().isEmpty();
    }
    
    @Override
    default Subject set(Object key, Object value) {
        return setAt(Slot.RECENT, key, value);
    }

    @Override
    default Subject set(Object element) {
        return set(element, element);
    }

    @Override
    default Subject put(Object element) {
        return put(element, element);
    }

    @Override
    default Subject put(Object key, Object value) {
        return get(key).notEmpty() ? this : set(key, value);
    }

    @Override
    default Subject add(Object element) {
        return set(new Suite.AutoKey(), element);
    }

    @Override
    default Subject unset(Object key) {
        return unsetAt(Slot.of(key));
    }
    
    @Override
    Subject unset();

    @Override
    default Subject setAt(Slot slot, Object element) {
        return setAt(slot, element, element);
    }

    @Override
    default Subject putAt(Slot slot, Object element) {
        return putAt(slot, element, element);
    }

    @Override
    default Subject putAt(Slot slot, Object key, Object value) {
        return get(key).notEmpty() ? this : setAt(slot, key, value);
    }

    @Override
    default Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.AutoKey(), element);
    }
}
