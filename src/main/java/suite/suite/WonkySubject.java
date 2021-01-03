package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Wave;
import suite.suite.util.Glass;

import java.lang.ref.WeakReference;
import java.util.function.Function;
import java.util.function.Supplier;

public class WonkySubject {} //  extends SolidSubject {



    /*class HomogenizedSubjectIterator implements Wave<Subject> {
        Subject sub;
        boolean reverse;
        Wave<Subject> it;

        boolean hasNext;
        Subject next;

        HomogenizedSubjectIterator(Subject sub, boolean reverse) {
            this.sub = sub;
            this.reverse = reverse;
            this.it = sub.iterator(reverse);
        }


        @Override
        public boolean hasNext() {
            if(hasNext) return true;
            if(sub != subject) {
                if(sub == ZeroSubject.getInstance()) {
                    it = subject.iterator(reverse);
                } else if(subject == ZeroSubject.getInstance()) {
                    it = Wave.emptyWave();
                }
                sub = subject;
            }
            while (hasNext = it.hasNext()) {
                Subject v = it.next();
                v = unweak(v);
                if(v != ZeroSubject.getInstance()) {
                    next = v;
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
            } else return new SolidSubject(next.separate());
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
        return new CoupleSubject(s.key(), o);
    }

    @Override
    public Object key() {
        return subject.key();
    }

    @Override
    public Subject get() {
        Wave<Subject> slime = iterator();
        return slime.hasNext() ? slime.next() : Suite.set();
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
    public Object direct() {
        return get().direct();
    }

    @Override
    public <B> B asExpected() {
        return get().asExpected();
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        return get().as(requestedType);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return get().as(requestedType);
    }

    @Override
    public <B> B as(Class<B> requestedType, B substitute) {
        return get().as(requestedType, substitute);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B substitute) {
        return get().as(requestedType, substitute);
    }

    @Override
    public <B> B orGiven(B substitute) {
        return get().orGiven(substitute);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return get().orDo(supplier);
    }

    @Override
    public boolean instanceOf(Class<?> type) {
        return get().instanceOf(type);
    }

    @Override
    public boolean notEmpty() {
        return iterator( false).hasNext();
    }

    @Override
    public boolean isEmpty() {
        return !notEmpty();
    }

    @Override
    public int size() {
        return subject.size();
    }

    @Override
    public Wave<Subject> iterator(boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse);
    }

    @Override
    public Fluid front() {
        return () -> new HomogenizedSubjectIterator(subject, false);
    }

    @Override
    public Fluid reverse() {
        return () -> new HomogenizedSubjectIterator(subject, true);
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
    public Subject unsetAt(Slot slot) {
        subject = subject.unsetAt(slot);
        return this;
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject saved = unweak(subject.get(key));
        if(saved.notEmpty())return new SolidSubject(saved);
        subject = subject.set(key, weak(reserve));
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject done = unweak(subject.get(key));
        if(done.notEmpty())return new SolidSubject(done);
        subject = subject.set(key, weak(supplier.get()));
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject done = unweak(subject.get(key));
        if(done.notEmpty())return new SolidSubject(done);
        subject = subject.set(key, weak(function.apply(argument)));
        return get(key);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.notEmpty()) subject = subject.unset(key);
        return unweak(taken);
    }

    @Override
    public Subject takeAt(Slot slot) {
        Subject taken = getAt(slot);
        if(taken.notEmpty()) subject = subject.unset(taken.key());
        return unweak(taken);
    }

    @Override
    public Subject join(Iterable<? extends Subject> iterable) {
        for(Subject v : iterable) {
            subject = subject.set(v.key(), v.direct());
        }
        return this;
    }

    @Override
    public Subject input(Iterable<? extends Subject> iterable) {
        for(Subject v : iterable) {
            subject = subject.put(v.key(), v.direct());
        }
        return this;
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
            subject = subject.setAt(slot, element, weak(element));
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        if(unweak(get(key)).isEmpty())
            subject = subject.setAt(slot, key, weak(value));
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        subject = subject.addAt(slot, weak(element));
        return this;
    }

    @Override
    public boolean fused() {
        return subject.fused();
    }
}*/
