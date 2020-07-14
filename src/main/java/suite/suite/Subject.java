package suite.suite;

import suite.suite.util.FluidSubject;
import suite.suite.util.Glass;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Subject {

    Subject set(Object element);
    Subject set(Object key, Object value);
    Subject put(Object element);
    Subject put(Object key, Object value);
    Subject add(Object element);

    Subject setAt(Slot slot, Object element);
    Subject setAt(Slot slot, Object key, Object value);
    Subject putAt(Slot slot, Object element);
    Subject putAt(Slot slot, Object key, Object value);
    Subject addAt(Slot slot, Object element);

    default Subject unset() {
        return ZeroSubject.getInstance();
    }
    Subject unset(Object key); // Usuń wartości pod kluczem $key
    Subject unset(Object key, Object value); // Usuń wartość $value pod kluczem $key

    Subject key();
    Subject prime();
    Subject recent();
    Subject get(Object key);
    Subject getAt(Slot slot);
    Subject getAt(int slotIndex);

    default Subject at(Object key) {
        return get(key).orDo(Suite::set);
    }
    Object direct();
    <B> B asExpected();
    <B> B asGiven(Class<B> requestedType);
    <B> B asGiven(Glass<? super B, B> requestedType);
    <B> B asGiven(Class<B> requestedType, B substitute);
    <B> B asGiven(Glass<? super B, B> requestedType, B substitute);
    <B> B orGiven(B substitute);
    <B> B orDo(Supplier<B> supplier);
    boolean assigned(Class<?> type);

    default int asInt() {
        return asExpected();
    }
    default String asString() {
        return asExpected();
    }

    default Subject getSaved(Object key, Object substitute) {
        throw new UnsupportedOperationException();
    }
    default Subject getDone(Object key, Supplier<?> supplier) {
        throw new UnsupportedOperationException();
    }
    default Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        throw new UnsupportedOperationException();
    }
    default Subject take(Object key) {
        throw new UnsupportedOperationException();
    }
    default Subject takeAt(Slot slot) {
        throw new UnsupportedOperationException();
    }

    boolean settled();
    boolean desolated();
    int size();

    default FluidSubject front() {
        throw new UnsupportedOperationException();
    }
    default FluidSubject front(Object fromKeyIncluded) {
        throw new UnsupportedOperationException();
    }
    default FluidSubject frontFrom(Slot fromSlotIncluded) {
        throw new UnsupportedOperationException();
    }
    default FluidSubject reverse() {
        throw new UnsupportedOperationException();
    }
    default FluidSubject reverse(Object fromKeyIncluded) {
        throw new UnsupportedOperationException();
    }
    default FluidSubject reverseFrom(Slot fromSlotIncluded) {
        throw new UnsupportedOperationException();
    }

    Subject homogenize();

    default boolean fused() {
        return false;
    }

    default boolean homogeneous() {
        return false;
    }

    default Subject insetAll(Iterable<Subject> iterable) {
        Subject subject = this;
        for(Subject it : iterable) {
            subject = subject.set(it.key().direct(), it.direct());
        }
        return subject;
    }

    default Subject inputAll(Iterable<Subject> iterable) {
        Subject subject = this;
        for(Subject it : iterable) {
            subject = subject.put(it.key().direct(), it.direct());
        }
        return subject;
    }

    default Subject setAll(Iterable<Object> iterable) {
        Subject subject = this;
        for(Object it : iterable) {
            subject = subject.set(it);
        }
        return subject;
    }

    default Subject putAll(Iterable<Object> iterable) {
        Subject subject = this;
        for(Object it : iterable) {
            subject = subject.put(it);
        }
        return subject;
    }

    default Subject addAll(Iterable<Object> iterable) {
        Subject subject = this;
        for (Object it : iterable) {
            subject = subject.add(it);
        }
        return subject;
    }
}
