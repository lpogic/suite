package suite.suite;

import suite.suite.util.*;

import java.util.function.Supplier;

class MultiSubject implements Subject {

    private final Chain chain;

    MultiSubject() {
        this.chain = new Chain();
    }

    MultiSubject(Link ward) {
        this.chain = new Chain(ward);
    }

    @Override
    public Subject set(Object element) {
        return set(element, element);
    }

    @Override
    public Subject set(Object key, Object value) {
        chain.put(chain.get(key), key, value);
        return this;
    }

    @Override
    public Subject put(Object element) {
        return put(element, element);
    }

    @Override
    public Subject put(Object key, Object value) {
        chain.putLastIfAbsent(key, value);
        return this;
    }

    @Override
    public Subject add(Object element) {
        chain.putLast(new Suite.AutoKey(), element);
        return this;
    }

    @Override
    public Subject unset() {
        chain.clear();
        return this;
    }

    @Override
    public Subject unset(Object key) {
        chain.remove(key);
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        Subject s = getAt(slot);
        if(s.notEmpty()) unset(s.key());
        return this;
    }

    @Override
    public Object key() {
        return chain.getFirst().subject.key();
    }

    @Override
    public Subject get() {
        return chain.getFirst().subject;
    }

    @Override
    public Subject get(Object key) {
        return chain.get(key).subject;
    }

    @Override
    public Subject getAt(Slot slot) {
        if(slot == Slot.PRIME) {
            return chain.getFirst().subject;

        } else if(slot == Slot.RECENT) {
            return chain.getLast().subject;

        } else if(slot instanceof Slot.SlotOf) {
            Link link = chain.get(((Slot.SlotOf) slot).key);
            return link.subject;

        } else if(slot instanceof Slot.SlotBefore) {
            Link link = chain.get(((Slot.SlotBefore) slot).key);
            return link == chain.ward ? link.subject : link.front.subject;

        } else if(slot instanceof Slot.SlotAfter) {
            Link link = chain.get(((Slot.SlotAfter) slot).key);
            return link == chain.ward ? link.subject : link.back.subject;

        } else if(slot instanceof Slot.PlacedSlot) {
            Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
            return link.subject;

        } else return ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(int slotIndex) {
        return chain.getNth(slotIndex).subject;
    }

    @Override
    public Object direct() {
        return chain.getFirst().subject.direct();
    }
    
    @Override
    public <B> B asExpected() {
        return chain.getFirst().subject.asExpected();
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return chain.getFirst().subject.asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return chain.getFirst().subject.asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B reserve) {
        return chain.getFirst().subject.asGiven(requestedType, reserve);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B reserve) {
        return chain.getFirst().subject.asGiven(requestedType, reserve);
    }

    @Override
    public <B> B orGiven(B reserve) {
        return chain.getFirst().subject.orGiven(reserve);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return chain.getFirst().subject.orDo(supplier);
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return chain.getFirst().subject.instanceOf(type);
    }

    @Override
    public boolean notEmpty() {
        return !chain.isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return chain.isEmpty();
    }

    @Override
    public int size() {
        return chain.size();
    }

    @Override
    public Fluid front() {
        return chain;
    }

    @Override
    public Fluid reverse() {
        return () -> chain.iterator(true);
    }

    @Override
    public String toString() {
        return chain.toString();
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        return setAt(slot, element, element);
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        if(slot == Slot.PRIME) {
            chain.putFirst(key, value);

        } else if(slot == Slot.RECENT) {
            chain.putLast(key, value);

        } else if(slot instanceof Slot.SlotBefore) {
            Link link = chain.get(((Slot.SlotBefore) slot).key);
            if(link != chain.ward) chain.put(link, key, value);

        } else if(slot instanceof Slot.SlotAfter) {
            Link link = chain.get(((Slot.SlotAfter) slot).key);
            if (link != chain.ward) chain.put(link.back, key, value);

        } else if(slot instanceof Slot.PlacedSlot) {
            Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
            chain.put(link, key, value);

        }
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        return putAt(slot, element, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        if(slot == Slot.PRIME) {
            chain.putFirstIfAbsent(key, value);

        } else if(slot == Slot.RECENT) {
            chain.putLastIfAbsent(key, value);

        } else if(slot instanceof Slot.SlotBefore) {
            Link link = chain.get(((Slot.SlotBefore) slot).key);
            if(link != chain.ward) chain.putIfAbsent(link, key, value);

        } else if(slot instanceof Slot.SlotAfter) {
            Link link = chain.get(((Slot.SlotAfter) slot).key);
            if(link != chain.ward) chain.putIfAbsent(link.back, key, value);

        } else if(slot instanceof Slot.PlacedSlot) {
            Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
            chain.putIfAbsent(link, key, value);

        }
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.AutoKey() ,element);
    }

    @Override
    public Wave<Subject> iterator(boolean reverse) {
        return chain.iterator(reverse);
    }
}
