package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Glass;
import suite.suite.util.Wave;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Vendor extends Fluid {

    Vendor key();
    Vendor prime();
    Vendor recent();
    Vendor get(Object key);
    default Vendor get(Object ... keys) {
        Subject s = Suite.set();
        for(Object k : keys) {
            s.inset(get(k));
        }
        return s;
    }
    Vendor getAt(Slot slot);
    Vendor getAt(int slotIndex);

    default Vendor at(Object key) {
        Vendor v = get(key);
        if(v.instanceOf(Vendor.class))return v.asExpected();
        if(v.isNotEmpty())return v;
        return Suite.set();
    }
    default Vendor at(Object ... path) {
        Vendor at = this;
        for(Object o : path) at = at.at(o);
        return at;
    }
    Object direct();
    <B> B asExpected();
    <B> B asGiven(Class<B> requestedType);
    <B> B asGiven(Glass<? super B, B> requestedType);
    <B> B asGiven(Class<B> requestedType, B substitute);
    <B> B asGiven(Glass<? super B, B> requestedType, B substitute);
    <B> B orGiven(B substitute);
    <B> B orDo(Supplier<B> supplier);
    boolean instanceOf(Class<?> type);

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

    boolean isNotEmpty();
    boolean isEmpty();
    int size();

    Wave<Vendor> iterator(Slot slot, boolean reverse);

    @Override
    default Wave<Vendor> iterator() {
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
    default Vendor exclude() {
        return this;
    }

}
