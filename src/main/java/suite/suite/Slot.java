package suite.suite;

import java.util.function.Predicate;

public interface Slot {
    Slot PRIME = new Slot(){};
    Slot RECENT = new Slot(){};

    static Slot of(Object key) {
        return new SlotOf(key);
    }

    static Slot before(Object key) {
        return new SlotBefore(key);
    }

    static Slot after(Object key) {
        return new SlotAfter(key);
    }

    static Slot in(int place) {
        return new PlacedSlot(place);
    }

    static Slot recentBefore(Predicate<Vendor> isLater) {
        return new RecentBeforeSlot(isLater);
    }

    static Slot primeAfter(Predicate<Vendor> isEarlier) {
        return new PrimeAfterSlot(isEarlier);
    }

    class SlotOf implements Slot {
        Object key;

        SlotOf(Object key) {
            this.key = key;
        }
    }

    class SlotBefore implements Slot {
        Object key;

        SlotBefore(Object key) {
            this.key = key;
        }
    }

    class SlotAfter implements Slot {
        Object key;

        SlotAfter(Object key) {
            this.key = key;
        }
    }

    class PlacedSlot implements Slot{
        int place;

        PlacedSlot(int place) {
            this.place = place;
        }
    }

    class RecentBeforeSlot implements Slot {
        Predicate<Vendor> isLater;

        RecentBeforeSlot(Predicate<Vendor> isLater) {
            this.isLater = isLater;
        }
    }

    class PrimeAfterSlot implements Slot {
        Predicate<Vendor> isEarlier;

        PrimeAfterSlot(Predicate<Vendor> isEarlier) {
            this.isEarlier = isEarlier;
        }
    }
}
