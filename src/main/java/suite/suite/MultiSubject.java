package suite.suite;

import suite.suite.util.*;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

class MultiSubject implements Subject {

    private final Chain chain;

    MultiSubject() {
        this.chain = new Chain();
    }

    @Override
    public Subject set(Object element) {
        return set(element, element);
    }

    @Override
    public Subject set(Object key, Object value) {
        chain.putLast(key, value);
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
        chain.putLast(new Suite.Add(), element);
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
    public Subject unset(Object key, Object value) {
        chain.remove(key, value);
        return this;
    }

    @Override
    public Subject key() {
        return chain.getFirst().subject.key();
    }

    @Override
    public Subject prime() {
        return chain.getFirst().subject;
    }

    @Override
    public Subject recent() {
        return chain.getLast().subject;
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
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Link link = chain.get(((Slot.SlotBefore) slot).key);
                return link == chain.ward ? ZeroSubject.getInstance() : link.front.subject;
            } else if(slot instanceof Slot.SlotAfter) {
                Link link = chain.get(((Slot.SlotAfter) slot).key);
                return link == chain.ward ? ZeroSubject.getInstance() : link.back.subject;
            } else if(slot instanceof Slot.PlacedSlot) {
                Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
                return link.subject;
            } else return ZeroSubject.getInstance();
        }
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
    public boolean assigned(Class<?> type) {
        return chain.getFirst().subject.assigned(type);
    }

    @Override
    public boolean settled() {
        return !chain.isEmpty();
    }

    @Override
    public boolean desolated() {
        return chain.isEmpty();
    }

    @Override
    public int size() {
        return chain.size();
    }

    @Override
    public FluidSubject front() {
        return chain;
    }

    @Override
    public FluidSubject front(Object fromKeyIncluded) {
        Link link = chain.get(fromKeyIncluded);
        return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(false, link.front);
    }

    @Override
    public FluidSubject frontFrom(Slot fromSlotIncluded) {
        if(fromSlotIncluded == Slot.PRIME) {
            return chain;
        } else if(fromSlotIncluded == Slot.RECENT) {
            Link link = chain.getLast();
            return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(false, link.front);
        } else {
            if(fromSlotIncluded instanceof Slot.SlotBefore) {
                Link link = chain.get(((Slot.SlotBefore) fromSlotIncluded).key);
                return link == chain.ward || link.front == chain.ward ?
                        FluidSubject.empty() : () -> chain.iterator(false, link.front.front);
            } else if(fromSlotIncluded instanceof Slot.SlotAfter) {
                Link link = chain.get(((Slot.SlotAfter) fromSlotIncluded).key);
                return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(false, link);
            } else if(fromSlotIncluded instanceof Slot.PlacedSlot) {
                Link link = chain.getNth(((Slot.PlacedSlot) fromSlotIncluded).place);
                return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(false, link.front);
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public FluidSubject reverse() {
        return () -> chain.iterator(true);
    }

    @Override
    public FluidSubject reverse(Object fromKeyIncluded) {
        Link link = chain.get(fromKeyIncluded);
        return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(true, link.back);
    }

    @Override
    public FluidSubject reverseFrom(Slot fromSlotIncluded) {
        if(fromSlotIncluded == Slot.PRIME) {
            Link link = chain.getFirst();
            return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(true, link.back);
        } else if(fromSlotIncluded == Slot.RECENT) {
            return () -> chain.iterator(true);
        } else {
            if(fromSlotIncluded instanceof Slot.SlotBefore) {
                Link link = chain.get(((Slot.SlotBefore) fromSlotIncluded).key);
                return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(true, link);
            } else if(fromSlotIncluded instanceof Slot.SlotAfter) {
                Link link = chain.get(((Slot.SlotAfter) fromSlotIncluded).key);
                return link == chain.ward || link.back == chain.ward ?
                        FluidSubject.empty() : () -> chain.iterator(true, link.back.back);
            } else if(fromSlotIncluded instanceof Slot.PlacedSlot) {
                Link link = chain.getNth(((Slot.PlacedSlot) fromSlotIncluded).place);
                return link == chain.ward ? FluidSubject.empty() : () -> chain.iterator(false, link.back);
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Subject homogenize() {
        return this;
    }

    @Override
    public String toString() {
        return chain.toString();
    }

    @Override
    public int hashCode() {
        return Suite.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Subject && Suite.equals(this, (Subject)obj);
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
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Link link = chain.get(((Slot.SlotBefore) slot).key);
                if(link == chain.ward) {
                    throw new NoSuchElementException();
                } else chain.put(link, key, value);
            } else if(slot instanceof Slot.SlotAfter) {
                Link link = chain.get(((Slot.SlotAfter) slot).key);
                if(link == chain.ward) {
                    throw new NoSuchElementException();
                } else chain.put(link.back, key, value);
            } else if(slot instanceof Slot.PlacedSlot) {
                Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
                chain.put(link, key, value);
            } else throw new UnsupportedOperationException();
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
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Link link = chain.get(((Slot.SlotBefore) slot).key);
                if(link == chain.ward) {
                    throw new NoSuchElementException();
                } else chain.putIfAbsent(link, key, value);
            } else if(slot instanceof Slot.SlotAfter) {
                Link link = chain.get(((Slot.SlotAfter) slot).key);
                if(link == chain.ward) {
                    throw new NoSuchElementException();
                } else chain.putIfAbsent(link.back, key, value);
            } else if(slot instanceof Slot.PlacedSlot) {
                Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
                chain.putIfAbsent(link, key, value);
            } else throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.Add() ,element);
    }
}
