package suite.suite;

import suite.suite.util.Glass;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

class ArraySubject implements Subject {
    private final ArrayList<CoupleSubject> array = new ArrayList<>();

    ArraySubject() {}

    @Override
    public Subject set(Object element) {
        return homogenize().set(element);
    }

    @Override
    public Subject set(Object key, Object value) {
        return homogenize().set(key, value);
    }

    @Override
    public Subject put(Object element) {
        return homogenize().put(element);
    }

    @Override
    public Subject put(Object key, Object value) {
        return homogenize().put(key, value);
    }

    @Override
    public Subject add(Object element) {
        array.add(new CoupleSubject(new Suite.AutoKey(), element));
        return this;
    }

    @Override
    public Subject unset(Object key) {
        if(key instanceof Suite.AutoKey) {
            int s = array.size();
            for (int i = 0; i < s; i++) {
                if(array.get(i).primeKey == key) {
                    s = i;
                    break;
                }
            }
            if(s < array.size()) array.remove(s);
        }
        return this;
    }

    @Override
    public Subject unset(Object key, Object value) {
        if(key instanceof Suite.AutoKey) {
            int s = array.size();
            for (int i = 0; i < s; i++) {
                if(array.get(i).primeKey == key) {
                    if(Objects.equals(array.get(i).primeValue, value)) s = i;
                    break;
                }
            }
            if(s < array.size()) array.remove(s);
        }
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        if(slot == Slot.PRIME) {
            if(array.size() > 0) array.remove(0);
        } else if(slot == Slot.RECENT) {
            if(array.size() > 0) array.remove(array.size() - 1);
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Object k = ((Slot.SlotBefore) slot).key;
                if(k instanceof Suite.AutoKey) {
                    int s = array.size();
                    for (int i = 0; i < s; i++) {
                        if (array.get(i).primeKey == k) {
                            s = i;
                            break;
                        }
                    }
                    if(s > 0 && s < array.size()) array.remove(s);
                }
            } else if(slot instanceof Slot.SlotAfter) {
                Object k = ((Slot.SlotAfter) slot).key;
                if(k instanceof Suite.AutoKey) {
                    int s = array.size();
                    for (int i = 0; i < s; i++) {
                        if (array.get(i).primeKey == k) {
                            s = i;
                            break;
                        }
                    }
                    if(s < array.size() - 1) array.remove(s);
                }
            } else if(slot instanceof Slot.RecentBeforeSlot) {
                Predicate<Subject> isLater = ((Slot.RecentBeforeSlot) slot).isLater;
                for (int i = array.size() - 1; i >= 0; --i) {
                    CoupleSubject cs = array.get(i);
                    if(!isLater.test(cs)) array.remove(i);
                }
            } else if(slot instanceof Slot.PrimeAfterSlot) {
                Predicate<Subject> isEarlier = ((Slot.PrimeAfterSlot) slot).isEarlier;
                int s = array.size();
                for (int i = 0; i < s; i++) {
                    CoupleSubject cs = array.get(i);
                    if(!isEarlier.test(cs)) return array.remove(i);
                }
            } else if(slot instanceof Slot.PlacedSlot) {
                int i = ((Slot.PlacedSlot) slot).place;
                if(i < array.size() && i >= 0) array.remove(i);
            }
        }
        return this;
    }

    @Override
    public Subject key() {
        return array.isEmpty() ? ZeroSubject.getInstance() : new BubbleSubject(array.get(0).primeKey);
    }

    @Override
    public Subject prime() {
        return array.isEmpty() ? ZeroSubject.getInstance() : array.get(0);
    }

    @Override
    public Subject recent() {
        int s = array.size();
        return s > 0 ? array.get(s - 1) : ZeroSubject.getInstance();
    }

    @Override
    public Subject get(Object key) {
        for (CoupleSubject cs : array) {
            if (cs.primeKey == key) return cs;
        }
        return ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(Slot slot) {
        if(slot == Slot.PRIME) {
            return prime();
        } else if(slot == Slot.RECENT) {
            return recent();
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Object k = ((Slot.SlotBefore) slot).key;
                if(k instanceof Suite.AutoKey) {
                    int s = array.size();
                    for (int i = 0; i < s; i++) {
                        if (array.get(i).primeKey == k) {
                            s = i;
                            break;
                        }
                    }
                    if (s > 0 && s < array.size()) return array.get(s - 1);
                }
            } else if(slot instanceof Slot.SlotAfter) {
                Object k = ((Slot.SlotAfter) slot).key;
                if(k instanceof Suite.AutoKey) {
                    int s = array.size();
                    for (int i = 0; i < s; i++) {
                        if (array.get(i).primeKey == k) {
                            s = i;
                            break;
                        }
                    }
                    if (s < array.size() - 1) return array.get(s + 1);
                }
            } else if(slot instanceof Slot.RecentBeforeSlot) {
                Predicate<Subject> isLater = ((Slot.RecentBeforeSlot) slot).isLater;
                for (int i = array.size() - 1; i >= 0; --i) {
                    CoupleSubject cs = array.get(i);
                    if(!isLater.test(cs)) return cs;
                }
            } else if(slot instanceof Slot.PrimeAfterSlot) {
                Predicate<Subject> isEarlier = ((Slot.PrimeAfterSlot) slot).isEarlier;
                for (CoupleSubject cs : array) {
                    if (!isEarlier.test(cs)) return cs;
                }
            } else if(slot instanceof Slot.PlacedSlot) {
                int i = ((Slot.PlacedSlot) slot).place;
                if(i >= 0 && i < array.size()) return array.get(i);
            }
        }
        return ZeroSubject.getInstance();
    }

    @Override
    public Subject getAt(int slotIndex) {
        return slotIndex >= 0 && slotIndex < array.size() ? array.get(slotIndex) : ZeroSubject.getInstance();
    }

    @Override
    public Object direct() {
        return prime().direct();
    }

    @Override
    public <B> B asExpected() {
        return prime().asExpected();
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return prime().asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return prime().asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return prime().asGiven(requestedType, substitute);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return prime().asGiven(requestedType, substitute);
    }

    @Override
    public <B> B orGiven(B substitute) {
        return prime().orGiven(substitute);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return prime().orDo(supplier);
    }

    @Override
    public boolean assigned(Class<?> type) {
        return prime().assigned(type);
    }

    @Override
    public boolean settled() {
        return array.size() > 0;
    }

    @Override
    public boolean desolated() {
        return array.size() < 1;
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public Subject homogenize() {
        return new MultiSubject().insetAll(front());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        array.forEach(s -> stringBuilder.append(s).append("\n"));
        return stringBuilder.toString();
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        return homogenize().setAt(slot, element);
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        return homogenize().setAt(slot, key, value);
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        return homogenize().putAt(slot, element);
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        return homogenize().putAt(slot, key, value);
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        if(slot == Slot.PRIME) {
            array.add(0, new CoupleSubject(new Suite.AutoKey(), element));
        } else if(slot == Slot.RECENT) {
            array.add(new CoupleSubject(new Suite.AutoKey(), element));
        } else {
            if(slot instanceof Slot.SlotBefore) {
                Object k = ((Slot.SlotBefore) slot).key;
                if(k instanceof Suite.AutoKey) {
                    int s = array.size();
                    for (int i = 0; i < s; i++) {
                        if (array.get(i).primeKey == k) {
                            s = i;
                            break;
                        }
                    }
                    if (s > 0 && s < array.size()) {
                        array.add(s - 1, new CoupleSubject(new Suite.AutoKey(), element));
                        return this;
                    }
                }
            } else if(slot instanceof Slot.SlotAfter) {
                Object k = ((Slot.SlotAfter) slot).key;
                if(k instanceof Suite.AutoKey) {
                    int s = array.size();
                    for (int i = 0; i < s; i++) {
                        if (array.get(i).primeKey == k) {
                            s = i;
                            break;
                        }
                    }
                    if (s < array.size()) {
                        array.add(s + 1, new CoupleSubject(new Suite.AutoKey(), element));
                        return this;
                    }
                }
            } else if(slot instanceof Slot.RecentBeforeSlot) {
                Predicate<Subject> isLater = ((Slot.RecentBeforeSlot) slot).isLater;
                for (int i = array.size() - 1; i >= 0; --i) {
                    CoupleSubject cs = array.get(i);
                    if(!isLater.test(cs)) {
                        array.add(i + 1, new CoupleSubject(new Suite.AutoKey(), element));
                        return this;
                    }
                }
            } else if(slot instanceof Slot.PrimeAfterSlot) {
                Predicate<Subject> isEarlier = ((Slot.PrimeAfterSlot) slot).isEarlier;
                int s = array.size();
                for (int i = 0; i < s; i++) {
                    CoupleSubject cs = array.get(i);
                    if(!isEarlier.test(cs)) {
                        array.add(i, new CoupleSubject(new Suite.AutoKey(), element));
                        return this;
                    }
                }
            } else if(slot instanceof Slot.PlacedSlot) {
                int i = ((Slot.PlacedSlot) slot).place;
                if(i >= 0 && i <= array.size()) {
                    array.add(i, new CoupleSubject(new Suite.AutoKey(), element));
                    return this;
                }
            }
        }
        throw new NoSuchElementException();
    }
}
