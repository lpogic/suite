package suite.suite;

import suite.suite.util.FluidIterator;
import suite.suite.util.Fluid;
import suite.suite.util.Glass;

import java.util.function.Function;
import java.util.function.Supplier;

public class SolidSubject implements Subject {

    class HomogenizedSubjectIterator implements FluidIterator<Subject>{
        Subject sub;
        boolean reverse;
        FluidIterator<Subject> it;


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
                    it = FluidIterator.empty();
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

    public SolidSubject(Subject subject) {
        this.subject = subject;
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
    public Subject prime() {
        return new SolidSubject(subject.prime().exclude());
    }

    @Override
    public Subject recent() {
        return new SolidSubject(subject.recent().exclude());
    }

    @Override
    public Subject get(Object key) {
        return new SolidSubject(subject.get(key).exclude());
    }

    @Override
    public Subject getAt(Slot slot) {
        return new SolidSubject(subject.getAt(slot).exclude());
    }

    @Override
    public Subject getAt(int slotIndex) {
        return new SolidSubject(subject.getAt(slotIndex).exclude());
    }

    @Override
    public Subject key() {
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
    public boolean assigned(Class<?> type) {
        return subject.assigned(type);
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject saved = subject.get(key);
        if(saved.settled())return new SolidSubject(saved);
        subject = subject.set(key, reserve);
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject done = subject.get(key);
        if(done.settled())return new SolidSubject(done);
        subject = subject.set(key, supplier.get());
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject done = subject.get(key);
        if(done.settled())return new SolidSubject(done);
        subject = subject.set(key, function.apply(argument));
        return get(key);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.settled()) subject = subject.unset(key);
        return taken;
    }

    @Override
    public Subject takeAt(Slot slot) {
        Subject taken = getAt(slot);
        if(taken.settled()) subject = subject.unset(taken.key().direct());
        return taken;
    }

    @Override
    public boolean settled() {
        return subject.settled();
    }

    @Override
    public boolean desolated() {
        return subject.desolated();
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
    public FluidIterator<Subject> iterator(Slot slot, boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse, slot);
    }

    @Override
    public Subject inset(Iterable<Subject> iterable) {
        subject = subject.inset(iterable);
        return this;
    }

    @Override
    public Subject input(Iterable<Subject> iterable) {
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
