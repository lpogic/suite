package suite.suite;

import suite.suite.util.Glass;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public abstract class SimpleVendor implements Vendor {

    @Override
    public Vendor prime() {
        return getAt(Slot.PRIME);
    }

    @Override
    public Vendor recent() {
        return getAt(Slot.RECENT);
    }

    @Override
    public Vendor get(Object key) {
        return getAt(Slot.of(key));
    }

    @Override
    public Vendor getAt(int slotIndex) {
        return getAt(Slot.in(slotIndex));
    }

    @Override
    public <B> B asExpected() {
        return (B)direct();
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return asGiven(Glass.of(requestedType));
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return requestedType.cast(direct());
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return asGiven(Glass.of(requestedType), substitute);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        Object o = direct();
        return requestedType.isInstance(o) ? requestedType.cast(o) : substitute;
    }

    @Override
    public <B> B orGiven(B substitute) {
        Object o = direct();
        return o == null ? substitute : (B)o;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        Object o = direct();
        return o == null ? supplier.get() : (B)o;
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return type.isInstance(direct());
    }

    @Override
    public boolean notEmpty() {
        return size() > 0;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
