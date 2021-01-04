package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Glass;
import suite.suite.util.Wave;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Vendor extends Fluid {

    Vendor atFirst();
    Vendor atLast();
    Vendor at(Object element);
    Vendor get();
    Vendor get(Object element);
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
    Wave<Vendor> iterator(boolean reverse);
    default Wave<Vendor> iterator() {
        return iterator(false);
    }

    default Fluid front() {
        throw new UnsupportedOperationException("Solid method");
    }
    default Fluid reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    default Vendor separate() {
        return this;
    }
}
