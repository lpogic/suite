package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Fluid;
import suite.suite.util.Glass;

import java.util.function.Function;
import java.util.function.Supplier;

public class SolidSubject implements Subject {

    class HomogenizedSubjectIterator implements Wave<Vendor> {
        Vendor sub;
        boolean reverse;
        Wave<Vendor> it;


        HomogenizedSubjectIterator(Subject sub, boolean reverse, Slot slot) {
            this.sub = sub;
            this.reverse = reverse;
            this.it = sub.iterator(slot, reverse);
        }

        @Override
        public boolean hasNext() {
            if(sub != subject) {
                if(sub == ZeroSubject.getInstance()) {
                    it = reverse ? subject.iterator(Slot.RECENT, true) : subject.iterator(Slot.PRIME, false);
                } else if(subject == ZeroSubject.getInstance()) {
                    it = Wave.empty();
                }
                sub = subject;
            }
            return it.hasNext();
        }

        @Override
        public Subject next() {
            return new SolidSubject(it.next().exclude());
        }
    }

    private Subject subject;

    public SolidSubject() {
        subject = ZeroSubject.getInstance();
    }

    public SolidSubject(Vendor vendor) {
        if(vendor instanceof Subject) this.subject = (Subject)vendor;
        else this.subject = Suite.inset(vendor);
    }

    @Override
    public Subject set(Object element) {
        subject = subject.set(element);
        return this;
    }

    @Override
    public Subject set(Object key, Object value) {
        subject = subject.set(key, value);
        return this;
    }

    @Override
    public Subject put(Object element) {
        subject = subject.put(element);
        return this;
    }

    @Override
    public Subject put(Object key, Object value) {
        subject = subject.put(key, value);
        return this;
    }

    @Override
    public Subject add(Object element) {
        subject = subject.add(element);
        return this;
    }

    @Override
    public Subject unset() {
        subject = subject.unset();
        return this;
    }

    @Override
    public Subject unset(Object key) {
        subject = subject.unset(key);
        return this;
    }

    @Override
    public Subject unset(Object key, Object value) {
        subject = subject.unset(key, value);
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        subject = subject.unsetAt(slot);
        return this;
    }

    @Override
    public Vendor prime() {
        return new SolidSubject(subject.prime().exclude());
    }

    @Override
    public Vendor recent() {
        return new SolidSubject(subject.recent().exclude());
    }

    @Override
    public Vendor get(Object key) {
        return new SolidSubject(subject.get(key).exclude());
    }

    @Override
    public Vendor get(Object ... keys) {
        return new SolidSubject(subject.get(keys).exclude());
    }

    @Override
    public Vendor getAt(Slot slot) {
        return new SolidSubject(subject.getAt(slot).exclude());
    }

    @Override
    public Vendor getAt(int slotIndex) {
        return new SolidSubject(subject.getAt(slotIndex).exclude());
    }

    @Override
    public Vendor key() {
        return new SolidSubject(subject.key().exclude());
    }

    @Override
    public Object direct() {
        return subject.direct();
    }

    @Override
    public <B> B asExpected() {
        return subject.asExpected();
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        return subject.asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        return subject.asGiven(requestedType);
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B substitute) {
        return subject.asGiven(requestedType, substitute);
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return subject.asGiven(requestedType, substitute);
    }

    @Override
    public <B> B orGiven(B substitute) {
        return subject.orGiven(substitute);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return subject.orDo(supplier);
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return subject.instanceOf(type);
    }

    @Override
    public Vendor getSaved(Object key, Object reserve) {
        Vendor saved = subject.get(key);
        if(saved.isNotEmpty())return new SolidSubject(saved);
        subject = subject.set(key, reserve);
        return get(key);
    }

    @Override
    public Vendor getDone(Object key, Supplier<?> supplier) {
        Vendor done = subject.get(key);
        if(done.isNotEmpty())return new SolidSubject(done);
        subject = subject.set(key, supplier.get());
        return get(key);
    }

    @Override
    public Vendor getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Vendor done = subject.get(key);
        if(done.isNotEmpty())return new SolidSubject(done);
        subject = subject.set(key, function.apply(argument));
        return get(key);
    }

    @Override
    public Vendor take(Object key) {
        Vendor taken = get(key);
        if(taken.isNotEmpty()) subject = subject.unset(key);
        return taken;
    }

    @Override
    public Vendor takeAt(Slot slot) {
        Vendor taken = getAt(slot);
        if(taken.isNotEmpty()) subject = subject.unset(taken.key().direct());
        return taken;
    }

    @Override
    public boolean isNotEmpty() {
        return subject.isNotEmpty();
    }

    @Override
    public boolean isEmpty() {
        return subject.isEmpty();
    }

    @Override
    public int size() {
        return subject.size();
    }

    @Override
    public Fluid front() {
        return () -> new HomogenizedSubjectIterator(subject, false, Slot.PRIME);
    }

    @Override
    public Fluid front(Slot slot) {
        return () -> new HomogenizedSubjectIterator(subject, false, slot);
    }

    @Override
    public Fluid reverse() {
        return () -> new HomogenizedSubjectIterator(subject, true, Slot.RECENT);
    }

    @Override
    public Fluid reverse(Slot slot) {
        return () -> new HomogenizedSubjectIterator(subject, true, slot);
    }

    @Override
    public Wave<Vendor> iterator(Slot slot, boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse, slot);
    }

    @Override
    public Subject inset(Iterable<? extends Vendor> iterable) {
        subject = subject.inset(iterable);
        return this;
    }

    @Override
    public Subject input(Iterable<? extends Vendor> iterable) {
        subject = subject.input(iterable);
        return this;
    }

    @Override
    public boolean fused() {
        return subject.fused();
    }

    @Override
    public String toString() {
        return subject.toString();
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        subject = subject.setAt(slot, element);
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        subject = subject.setAt(slot, key, value);
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        subject = subject.putAt(slot, element);
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        subject = subject.putAt(slot, key, value);
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        subject = subject.addAt(slot, element);
        return this;
    }
}
