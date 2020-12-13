package suite.suite;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Subject extends Vendor {

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

    default Vendor getSaved(Object key, Object substitute) {
        throw new UnsupportedOperationException();
    }
    default Vendor getDone(Object key, Supplier<?> supplier) {
        throw new UnsupportedOperationException();
    }
    default Vendor getDone(Object key, Function<Subject, ?> function, Subject argument) {
        throw new UnsupportedOperationException();
    }
    default Vendor take(Object key) {
        throw new UnsupportedOperationException();
    }
    default Vendor takeAt(Slot slot) {
        throw new UnsupportedOperationException();
    }
    default Subject into(Object key) {
        return getDone(key, Suite::set).asExpected();
    }

    default boolean fused() {
        return false;
    }
    default Subject exclude() {
        return this;
    }

    default Subject inset(Iterable<? extends Vendor> iterable) {
        Subject subject = this;
        for(var it : iterable) {
            subject = subject.set(it.key().direct(), it.direct());
        }
        return subject;
    }

    default Subject input(Iterable<? extends Vendor> iterable) {
        Subject subject = this;
        for(var it : iterable) {
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

    default Subject set() {
        return this;
    }
    default Subject put() {
        return this;
    }
}
