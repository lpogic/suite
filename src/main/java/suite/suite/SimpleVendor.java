package suite.suite;

public interface SimpleVendor extends SimpleSubject {

    @Override
    default Subject set() {
        return Suite.inset(this);
    }

    @Override
    default Subject put() {
        return Suite.input(this);
    }

    @Override
    default Subject setAt(Slot slot, Object key, Object value) {
        return set().setAt(slot, key, value);
    }
    
    @Override
    default Subject unsetAt(Slot slot) {
        return set().unsetAt(slot);
    }
    
}
