package suite.suite;

import suite.suite.util.*;

import java.util.NoSuchElementException;
import java.util.function.Predicate;
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
    public Subject unset(Object key, Object value) {
        chain.remove(key, value);
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        Subject s = getAt(slot);
        return s.isNotEmpty() ? unset(s.key().direct()) : this;
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
            } else if(slot instanceof Slot.RecentBeforeSlot) {
                Predicate<Subject> isLater = ((Slot.RecentBeforeSlot) slot).isLater;
                for(Subject s : reverse()) {
                    if(!isLater.test(s)) return s;
                }
                return ZeroSubject.getInstance();
            } else if(slot instanceof Slot.PrimeAfterSlot) {
                Predicate<Subject> isEarlier = ((Slot.PrimeAfterSlot) slot).isEarlier;
                for(Subject s : front()) {
                    if(!isEarlier.test(s)) return s;
                }
                return ZeroSubject.getInstance();
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
    public boolean instanceOf(Class<?> type) {
        return chain.getFirst().subject.instanceOf(type);
    }

    @Override
    public boolean isNotEmpty() {
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
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Link link = chain.get(((Slot.SlotBefore) slot).key);
                if(link == chain.ward) {
                    throw new NoSuchElementException();
                } else chain.put(link, key, value);
            } else if(slot instanceof Slot.SlotAfter) {
                Link link = chain.get(((Slot.SlotAfter) slot).key);
                if (link == chain.ward) {
                    throw new NoSuchElementException();
                } else chain.put(link.back, key, value);
            } else if(slot instanceof Slot.RecentBeforeSlot) {
                Predicate<Subject> isLater = ((Slot.RecentBeforeSlot) slot).isLater;
                boolean settled = false;
                for(Subject s : reverse()) {
                    settled = !isLater.test(s);
                    if(settled) {
                        chain.put(chain.get(s.key().direct()).back, key, value);
                        break;
                    }
                } if(!settled) {
                    chain.put(chain.ward.back, key, value);
                }
            } else if(slot instanceof Slot.PrimeAfterSlot) {
                Predicate<Subject> isEarlier = ((Slot.PrimeAfterSlot) slot).isEarlier;
                boolean settled = false;
                for(Subject s : front()) {
                    settled = !isEarlier.test(s);
                    if(settled) {
                        chain.put(chain.get(s.key().direct()), key, value);
                        break;
                    }
                } if(!settled) {
                    chain.put(chain.ward, key, value);
                }
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
            } else if(slot instanceof Slot.RecentBeforeSlot) {
                if(chain.get(key) == chain.ward) {
                    setAt(slot, key, value);
                }
            } else if(slot instanceof Slot.PrimeAfterSlot) {
                if(chain.get(key) == chain.ward) {
                    setAt(slot, key, value);
                }
            } else if(slot instanceof Slot.PlacedSlot) {
                Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
                chain.putIfAbsent(link, key, value);
            } else throw new UnsupportedOperationException();
        }
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        return setAt(slot, new Suite.AutoKey() ,element);
    }

    @Override
    public Wave<Subject> iterator(Slot slot, boolean reverse) {
        if(reverse) {
            if(slot == Slot.PRIME) {
                Link link = chain.getFirst();
                return link == chain.ward ? Wave.empty() : chain.iterator(true, link.back);
            } else if(slot == Slot.RECENT) {
                return chain.iterator(true);
            } else {
                if(slot instanceof Slot.SlotBefore) {
                    Link link = chain.get(((Slot.SlotBefore) slot).key);
                    return link == chain.ward ? Wave.empty() : chain.iterator(true, link);
                } else if(slot instanceof Slot.SlotAfter) {
                    Link link = chain.get(((Slot.SlotAfter) slot).key);
                    return link == chain.ward || link.back == chain.ward ?
                            Wave.empty() : chain.iterator(true, link.back.back);
                } else if(slot instanceof Slot.PlacedSlot) {
                    Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
                    return link == chain.ward ? Wave.empty() : chain.iterator(false, link.back);
                }
            }
        } else {
            if(slot == Slot.PRIME) {
                return chain.iterator();
            } else if(slot == Slot.RECENT) {
                Link link = chain.getLast();
                return link == chain.ward ? Wave.empty() : chain.iterator(false, link.front);
            } else {
                if(slot instanceof Slot.SlotBefore) {
                    Link link = chain.get(((Slot.SlotBefore) slot).key);
                    return link == chain.ward || link.front == chain.ward ?
                            Wave.empty() : chain.iterator(false, link.front.front);
                } else if(slot instanceof Slot.SlotAfter) {
                    Link link = chain.get(((Slot.SlotAfter) slot).key);
                    return link == chain.ward ? Wave.empty() : chain.iterator(false, link);
                } else if(slot instanceof Slot.PlacedSlot) {
                    Link link = chain.getNth(((Slot.PlacedSlot) slot).place);
                    return link == chain.ward ? Wave.empty() : chain.iterator(false, link.front);
                }
            }
        }
        throw new UnsupportedOperationException();
    }
}
