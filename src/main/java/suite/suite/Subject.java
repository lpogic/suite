package suite.suite;

import suite.suite.util.*;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Subject extends Fluid {

    Subject atFirst();
    Subject atLast();
    Subject at(Object element);
    Subject get();
    Subject get(Object element);
    Object direct();
    <B> B asExpected();
    <B> B as(Class<B> requestedType);
    <B> B as(Glass<? super B, B> requestedType);
    <B> B as(Class<B> requestedType, B reserve);
    <B> B as(Glass<? super B, B> requestedType, B reserve);
    <B> B orGiven(B reserve);
    <B> B orDo(Supplier<B> supplier);
    boolean instanceOf(Class<?> type);
    boolean notEmpty();
    boolean isEmpty();
    int size();
    Wave<Subject> iterator(boolean reverse);
    Subject set(Object element);
    Subject set(Object element, Subject $set);
    Subject setBefore(Object sequent, Object element);
    Subject setBefore(Object sequent, Object element, Subject $set);
    Subject unset();
    Subject unset(Object element);
    default Subject in() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Subject in(Object element) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Wave<Subject> iterator() {
        return iterator(false);
    }

    default Subject setIf(Object element, Predicate<Subject> test) {
        return test.test(this) ? set(element) : this;
    }

    default Subject add(Subject $sub) {
        return set(new Suite.AutoKey(), $sub);
    }

    default Fluid front() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Fluid reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject take(Object key) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject inset(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject unsetAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    default Subject separate() {
        return this;
    }

    /* Vendor methods */

    /*Object key();
    Subject get(Object key);
    default Subject get(Object ... keys) {
        Subject s = Suite.set();
        for(Object k : keys) {
            s.inset(get(k));
        }
        return s;
    }
    Subject getAt(Slot slot);
    Subject getAt(int slotIndex);

    default Subject at() {
        return at(key());
    }
    default Subject at(Object key) {
        Subject $ = get(key);
        if($.instanceOf(Subject.class))return $.asExpected();
        if($.notEmpty())return Suite.set($.direct());
        return Suite.set();
    }
    default Subject at(Object ... path) {
        Subject at = this;
        for(Object o : path) at = at.at(o);
        return at;
    }
    Object direct();
    <B> B asExpected();
    <B> B as(Class<B> requestedType);
    <B> B as(Glass<? super B, B> requestedType);
    <B> B as(Class<B> requestedType, B substitute);
    <B> B as(Glass<? super B, B> requestedType, B substitute);
    <B> B orGiven(B substitute);
    <B> B orDo(Supplier<B> supplier);
    boolean instanceOf(Class<?> type);

    default String asString() {
        return Objects.toString(direct(), "nuLL");
    }
    default int asInt() {
        return as(Number.class).intValue();
    }
    default short asShort() {
        return as(Number.class).shortValue();
    }
    default long asLong() {
        return as(Number.class).longValue();
    }
    default double asDouble() {
        return as(Number.class).doubleValue();
    }
    default float asFloat() {
        return as(Number.class).floatValue();
    }
    default short asByte() {
        return as(Number.class).byteValue();
    }

    boolean notEmpty();
    boolean isEmpty();
    int size();

    Wave<Subject> iterator(boolean reverse);

    @Override
    default Wave<Subject> iterator() {
        return iterator(false);
    }

    default Fluid front() {
        return () -> iterator(false);
    }
    default Fluid reverse() {
        return () -> iterator(true);
    }


    *//* Collector methods *//*

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
    Subject unsetAt(Slot slot);

    default Subject getSaved(Object key, Object substitute) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }
    default Subject getDone(Object key, Supplier<?> supplier) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }
    default Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }
    default Subject take(Object key) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }
    default Subject takeAt(Slot slot) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }
    default Subject into(Object key) {
        return getDone(key, Suite::set).asExpected();
    }

    default Subject inset(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }

    default Subject input(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }

    default Subject setAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }

    default Subject putAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }

    default Subject addAll(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Only in Solid version allowed");
    }

    *//* Other methods *//*

    default Subject set() {
        return this;
    }
    default Subject put() {
        return this;
    }
    default boolean fused() {
        return false;
    }
    default Subject separate() {
        return this;
    }*/
}
