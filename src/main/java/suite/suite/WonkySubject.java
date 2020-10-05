package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

public class WonkySubject extends SolidSubject {

    class HomogenizedSubjectIterator implements Wave<Subject> {
        Subject sub;
        boolean reverse;
        Wave<Subject> it;

        boolean hasNext;
        Subject next;

        HomogenizedSubjectIterator(Subject sub, boolean reverse, Slot slot) {
            this.sub = sub;
            this.reverse = reverse;
            this.it = sub.iterator(slot, reverse);
        }


        @Override
        public boolean hasNext() {
            if(hasNext) return true;
            if(sub != subject) {
                if(sub == ZeroSubject.getInstance()) {
                    it = reverse ? subject.iterator(Slot.RECENT, true) : subject.iterator(Slot.PRIME, false);
                } else if(subject == ZeroSubject.getInstance()) {
                    it = Wave.empty();
                }
                sub = subject;
            }
            while (hasNext = it.hasNext()) {
                Subject s = it.next();
                s = unweak(s);
                if(s != ZeroSubject.getInstance()) {
                    next = s;
                    return true;
                }
            }
            return false;
        }

        @Override
        public Subject next() {
            if(hasNext) {
                hasNext = false;
                return new SolidSubject(next);
            } else return new SolidSubject(next.exclude());
        }
    }

    private Subject subject;

    WonkySubject() {
        subject = ZeroSubject.getInstance();
    }

    private WeakReference<Object> weak(Object o) {
        return o == null ? null : new WeakReference<>(o);
    }

    private Subject unweak(Subject s) {
        WeakReference<Object> ref = s.orGiven(null);
        if(ref == null) return s;
        Object o = ref.get();
        if(o == null) return ZeroSubject.getInstance();
        return new CoupleSubject(s.key().direct(), o);
    }

    @Override
    public Subject set(Object element) {
        System.err.println("Keys in WeakSubject are not weak wrapped");
        subject = subject.set(element, weak(element));
        return this;
    }

    @Override
    public Subject set(Object key, Object value) {
        subject = subject.set(key, weak(value));
        return this;
    }

    @Override
    public Subject put(Object element) {
        System.err.println("Keys in WeakSubject are not weak wrapped");
        if(unweak(subject.get(element)).desolated())
            subject = subject.set(element, weak(element));
        return this;
    }

    @Override
    public Subject put(Object key, Object value) {
        if(unweak(subject.get(key)).desolated())
            subject = subject.set(key, weak(value));
        return this;
    }

    @Override
    public Subject add(Object element) {
        subject = subject.add(weak(element));
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
        Iterator<Subject> it = iterator(Slot.PRIME, false);
        return it.hasNext() ? it.next() : new SolidSubject(ZeroSubject.getInstance());
    }

    @Override
    public Subject recent() {
        Iterator<Subject> it = iterator(Slot.RECENT, true);
        return it.hasNext() ? it.next() : new SolidSubject(ZeroSubject.getInstance());
    }

    @Override
    public Subject get(Object key) {
        return unweak(subject.get(key));
    }

    @Override
    public Subject getAt(Slot slot) {
        return new SolidSubject(unweak(subject.getAt(slot)));
    }

    @Override
    public Subject getAt(int slotIndex) {
        return new SolidSubject(unweak(subject.getAt(slotIndex)));
    }

    @Override
    public Subject key() {
        return new SolidSubject(subject.key());
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
    public Subject getSaved(Object key, Object reserve) {
        Subject saved = unweak(subject.get(key));
        if(saved.settled())return new SolidSubject(saved);
        subject = subject.set(key, weak(reserve));
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject done = unweak(subject.get(key));
        if(done.settled())return new SolidSubject(done);
        subject = subject.set(key, weak(supplier.get()));
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject done = unweak(subject.get(key));
        if(done.settled())return new SolidSubject(done);
        subject = subject.set(key, weak(function.apply(argument)));
        return get(key);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.settled()) subject = subject.unset(key);
        return unweak(taken);
    }

    @Override
    public Subject takeAt(Slot slot) {
        Subject taken = getAt(slot);
        if(taken.settled()) subject = subject.unset(taken.key().direct());
        return unweak(taken);
    }

    @Override
    public boolean settled() {
        return iterator(Slot.PRIME, false).hasNext();
    }

    @Override
    public boolean desolated() {
        return !settled();
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
    public Wave<Subject> iterator(Slot slot, boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse, slot);
    }

    @Override
    public Subject inset(Iterable<Subject> iterable) {
        for(Subject s : iterable) {
            subject = subject.set(s.key().direct(), s.direct());
        }
        return this;
    }

    @Override
    public Subject input(Iterable<Subject> iterable) {
        for(Subject s : iterable) {
            subject = subject.put(s.key().direct(), s.direct());
        }
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
        System.err.println("Keys in WeakSubject are not weak wrapped");
        subject = subject.setAt(slot, element, weak(element));
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        subject = subject.setAt(slot, key, weak(value));
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        System.err.println("Keys in WeakSubject are not weak wrapped");
        if(unweak(get(element)).desolated())
            subject = subject.putAt(slot, element, weak(element));
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        if(unweak(get(key)).desolated())
            subject = subject.putAt(slot, key, weak(value));
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        subject = subject.addAt(slot, weak(element));
        return this;
    }
}
