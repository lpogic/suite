package suite.suite;

import suite.suite.util.*;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Subject extends Fluid {

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
    Subject unset(Object key);
    Subject unset(Object key, Object value);
    Subject unsetAt(Slot slot);

    Subject key();
    Subject prime();
    Subject recent();
    Subject get(Object key);
    default Subject get(Object ... keys) {
        Subject s = ZeroSubject.getInstance();
        for(Object k : keys) {
            s.inset(get(k));
        }
        return s;
    }
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

    default String asString() {
        return Objects.toString(direct(), "nuLL");
    }
    default int asInt() {
        return asGiven(Number.class).intValue();
    }
    default short asShort() {
        return asGiven(Number.class).shortValue();
    }
    default long asLong() {
        return asGiven(Number.class).longValue();
    }
    default double asDouble() {
        return asGiven(Number.class).doubleValue();
    }
    default float asFloat() {
        return asGiven(Number.class).floatValue();
    }
    default short asByte() {
        return asGiven(Number.class).byteValue();
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
    default Subject into(Object key) {
        return getDone(key, Suite::set).asExpected();
    }

    boolean settled();
    boolean desolated();
    int size();

    Wave<Subject> iterator(Slot slot, boolean reverse);

    @Override
    default Wave<Subject> iterator() {
        return iterator(Slot.PRIME, false);
    }

    default Fluid front() {
        return () -> iterator(Slot.PRIME, false);
    }
    default Fluid front(Slot slot) {
        return () -> iterator(slot, false);
    }
    default Fluid reverse() {
        return () -> iterator(Slot.RECENT, true);
    }
    default Fluid reverse(Slot slot) {
        return () -> iterator(slot, true);
    }

    default boolean fused() {
        return false;
    }
    default Subject exclude() {
        return this;
    }

    default Subject inset(Iterable<Subject> iterable) {
        Subject subject = this;
        for(Subject it : iterable) {
            subject = subject.set(it.key().direct(), it.direct());
        }
        return subject;
    }

    default Subject input(Iterable<Subject> iterable) {
        Subject subject = this;
        for(Subject it : iterable) {
            subject = subject.put(it.key().direct(), it.direct());
        }
        return subject;
    }

    default Subject setAll(Iterable<?> iterable) {
        Subject subject = this;
        for(Object it : iterable) {
            subject = subject.set(it);
        }
        return subject;
    }

    default Subject putAll(Iterable<?> iterable) {
        Subject subject = this;
        for(Object it : iterable) {
            subject = subject.put(it);
        }
        return subject;
    }

    default Subject addAll(Iterable<?> iterable) {
        Subject subject = this;
        for (Object it : iterable) {
            subject = subject.add(it);
        }
        return subject;
    }
}
