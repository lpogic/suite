package suite.suite;

public interface Slot {
    Slot PRIME = new Slot(){};
    Slot RECENT = new Slot(){};

    static Slot before(Object key) {
        return new SlotBefore(key);
    }

    static Slot after(Object key) {
        return new SlotAfter(key);
    }

    static Slot in(int place) {
        return new PlacedSlot(place);
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
}
