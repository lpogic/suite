package suite.suite;

public class WonkySubject {} //  extends SolidSubject {



    /*class HomogenizedSubjectIterator implements Wave<Subject> {
        Subject jump;
        boolean reverse;
        Wave<Subject> it;

        boolean hasNext;
        Subject next;

        HomogenizedSubjectIterator(Subject jump, boolean reverse) {
            this.jump = jump;
            this.reverse = reverse;
            this.it = jump.iterator(reverse);
        }


        @Override
        public boolean hasNext() {
            if(hasNext) return true;
            if(jump != sub) {
                if(jump == ZeroSubject.getInstance()) {
                    it = sub.iterator(reverse);
                } else if(sub == ZeroSubject.getInstance()) {
                    it = Wave.empty();
                }
                jump = sub;
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
            } else return new SolidSubject(next.fuse());
        }
    }

    private Subject sub;

    WonkySubject() {
        sub = ZeroSubject.getInstance();
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
        return sub.key();
    }

    @Override
    public Subject get() {
        Wave<Subject> slime = iterator();
        return slime.hasNext() ? slime.next() : Suite.set();
    }

    @Override
    public Subject get(Object key) {
        return unweak(sub.get(key));
    }

    @Override
    public Subject getAt(Slot slot) {
        return new SolidSubject(unweak(sub.getAt(slot)));
    }

    @Override
    public Subject getAt(int slotIndex) {
        return new SolidSubject(unweak(sub.getAt(slotIndex)));
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
    public boolean is(Class<?> type) {
        return get().is(type);
    }

    @Override
    public boolean present() {
        return iterator( false).hasNext();
    }

    @Override
    public boolean absent() {
        return !present();
    }

    @Override
    public int size() {
        return sub.size();
    }

    @Override
    public Wave<Subject> iterator(boolean reverse) {
        return new HomogenizedSubjectIterator(sub, reverse);
    }

    @Override
    public Fluid front() {
        return () -> new HomogenizedSubjectIterator(sub, false);
    }

    @Override
    public Fluid reverse() {
        return () -> new HomogenizedSubjectIterator(sub, true);
    }

    @Override
    public Subject set(Object element) {
        System.err.println("Keys up WeakSubject are not weak wrapped");
        sub = sub.set(element, weak(element));
        return this;
    }

    @Override
    public Subject set(Object key, Object value) {
        sub = sub.set(key, weak(value));
        return this;
    }

    @Override
    public Subject put(Object element) {
        System.err.println("Keys up WeakSubject are not weak wrapped");
        if(unweak(sub.get(element)).absent())
            sub = sub.set(element, weak(element));
        return this;
    }

    @Override
    public Subject put(Object key, Object value) {
        if(unweak(sub.get(key)).absent())
            sub = sub.set(key, weak(value));
        return this;
    }

    @Override
    public Subject add(Object element) {
        sub = sub.add(weak(element));
        return this;
    }

    @Override
    public Subject unset() {
        sub = sub.unset();
        return this;
    }

    @Override
    public Subject unset(Object key) {
        sub = sub.unset(key);
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        sub = sub.unsetAt(slot);
        return this;
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject saved = unweak(sub.get(key));
        if(saved.present())return new SolidSubject(saved);
        sub = sub.set(key, weak(reserve));
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject done = unweak(sub.get(key));
        if(done.present())return new SolidSubject(done);
        sub = sub.set(key, weak(supplier.get()));
        return get(key);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject done = unweak(sub.get(key));
        if(done.present())return new SolidSubject(done);
        sub = sub.set(key, weak(function.apply(argument)));
        return get(key);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.present()) sub = sub.unset(key);
        return unweak(taken);
    }

    @Override
    public Subject takeAt(Slot slot) {
        Subject taken = getAt(slot);
        if(taken.present()) sub = sub.unset(taken.key());
        return unweak(taken);
    }

    @Override
    public Subject alter(Iterable<? extends Subject> iterable) {
        for(Subject v : iterable) {
            sub = sub.set(v.key(), v.direct());
        }
        return this;
    }

    @Override
    public Subject input(Iterable<? extends Subject> iterable) {
        for(Subject v : iterable) {
            sub = sub.put(v.key(), v.direct());
        }
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        System.err.println("Keys up WeakSubject are not weak wrapped");
        sub = sub.setAt(slot, element, weak(element));
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        sub = sub.setAt(slot, key, weak(value));
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        System.err.println("Keys up WeakSubject are not weak wrapped");
        if(unweak(get(element)).absent())
            sub = sub.setAt(slot, element, weak(element));
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        if(unweak(get(key)).absent())
            sub = sub.setAt(slot, key, weak(value));
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        sub = sub.addAt(slot, weak(element));
        return this;
    }

    @Override
    public boolean fused() {
        return sub.fused();
    }
}*/
