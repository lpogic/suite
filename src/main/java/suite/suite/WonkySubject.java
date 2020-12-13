package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

public class WonkySubject extends SolidSubject {

    class HomogenizedSubjectIterator implements Wave<Vendor> {
        Vendor sub;
        boolean reverse;
        Wave<Vendor> it;

        boolean hasNext;
        Vendor next;

        HomogenizedSubjectIterator(Vendor sub, boolean reverse, Slot slot) {
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
                Vendor v = it.next();
                v = unweak(v);
                if(v != ZeroSubject.getInstance()) {
                    next = v;
                    return true;
                }
            }
            return false;
        }

        @Override
        public Vendor next() {
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

    private Vendor unweak(Vendor s) {
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
        if(unweak(subject.get(element)).isEmpty())
            subject = subject.set(element, weak(element));
        return this;
    }

    @Override
    public Subject put(Object key, Object value) {
        if(unweak(subject.get(key)).isEmpty())
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
    public Vendor prime() {
        Iterator<Vendor> it = iterator(Slot.PRIME, false);
        return it.hasNext() ? it.next() : new SolidSubject(ZeroSubject.getInstance());
    }

    @Override
    public Vendor recent() {
        Iterator<Vendor> it = iterator(Slot.RECENT, true);
        return it.hasNext() ? it.next() : new SolidSubject(ZeroSubject.getInstance());
    }

    @Override
    public Vendor get(Object key) {
        return unweak(subject.get(key));
    }

    @Override
    public Vendor getAt(Slot slot) {
        return new SolidSubject(unweak(subject.getAt(slot)));
    }

    @Override
    public Vendor getAt(int slotIndex) {
        return new SolidSubject(unweak(subject.getAt(slotIndex)));
    }

    @Override
    public Vendor key() {
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
    public boolean instanceOf(Class<?> type) {
        return prime().instanceOf(type);
    }

    @Override
    public Vendor getSaved(Object key, Object reserve) {
        Vendor saved = unweak(subject.get(key));
        if(saved.isNotEmpty())return new SolidSubject(saved);
        subject = subject.set(key, weak(reserve));
        return get(key);
    }

    @Override
    public Vendor getDone(Object key, Supplier<?> supplier) {
        Vendor done = unweak(subject.get(key));
        if(done.isNotEmpty())return new SolidSubject(done);
        subject = subject.set(key, weak(supplier.get()));
        return get(key);
    }

    @Override
    public Vendor getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Vendor done = unweak(subject.get(key));
        if(done.isNotEmpty())return new SolidSubject(done);
        subject = subject.set(key, weak(function.apply(argument)));
        return get(key);
    }

    @Override
    public Vendor take(Object key) {
        Vendor taken = get(key);
        if(taken.isNotEmpty()) subject = subject.unset(key);
        return unweak(taken);
    }

    @Override
    public Vendor takeAt(Slot slot) {
        Vendor taken = getAt(slot);
        if(taken.isNotEmpty()) subject = subject.unset(taken.key().direct());
        return unweak(taken);
    }

    @Override
    public boolean isNotEmpty() {
        return iterator(Slot.PRIME, false).hasNext();
    }

    @Override
    public boolean isEmpty() {
        return !isNotEmpty();
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
        for(Vendor v : iterable) {
            subject = subject.set(v.key().direct(), v.direct());
        }
        return this;
    }

    @Override
    public Subject input(Iterable<? extends Vendor> iterable) {
        for(Vendor v : iterable) {
            subject = subject.put(v.key().direct(), v.direct());
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
        if(unweak(get(element)).isEmpty())
            subject = subject.putAt(slot, element, weak(element));
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        if(unweak(get(key)).isEmpty())
            subject = subject.putAt(slot, key, weak(value));
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        subject = subject.addAt(slot, weak(element));
        return this;
    }
}
