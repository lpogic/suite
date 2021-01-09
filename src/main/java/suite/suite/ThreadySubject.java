package suite.suite;

class ThreadySubject {}/* implements Subject {

    class HomogenizedSubjectIterator implements Wave<Subject> {
        Subject jump;
        boolean reverse;
        Wave<Subject> it;

        boolean hasNext;
        Subject next;

        HomogenizedSubjectIterator(Subject jump, boolean reverse, Slot slot) {
            this.jump = jump;
            this.reverse = reverse;
            this.it = jump.iterator(slot, reverse);
        }


        @Override
        public boolean hasNext() {
            if(hasNext) return true;
            try(var ignored = readLock.lock()) {
                if(jump != sub) {
                    if(jump == ZeroSubject.getInstance()) {
                        it = reverse ? sub.iterator(Slot.RECENT, true) : sub.iterator(Slot.PRIME, false);
                    } else if(sub == ZeroSubject.getInstance()) {
                        it = Wave.empty();
                    }
                    jump = sub;
                }
                hasNext = it.hasNext();
                if(hasNext) next = it.next();
            }
            return hasNext;
        }

        @Override
        public Subject next() {
            hasNext = false;
            return new SolidSubject(next.fuse());
        }
    }

    private static class ThreadyLock implements AutoCloseable {
        private final Lock lock;

        public ThreadyLock(Lock lock) {
            this.lock = lock;
        }

        public final ThreadyLock lock() {
            lock.lock();
            return this;
        }

        @Override
        public void close() {
            lock.unlock();
        }
    }

    private Subject sub;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ThreadyLock writeLock = new ThreadyLock(lock.writeLock());
    private final ThreadyLock readLock = new ThreadyLock(lock.readLock());


    ThreadySubject() {
        sub = ZeroSubject.getInstance();
    }

    ThreadySubject(Subject sub) {
        this.sub = sub;
    }

    @Override
    public Subject set(Object element) {
        try(var ignored = writeLock.lock()) {
            sub = sub.set(element);
        }
        return this;
    }

    @Override
    public Subject set(Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            sub = sub.set(key, value);
        }
        return this;
    }

    @Override
    public Subject put(Object value) {
        try(var ignored = writeLock.lock()) {
            sub = sub.put(value);
        }
        return this;
    }

    @Override
    public Subject put(Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            sub = sub.put(key, value);
        }
        return this;
    }

    @Override
    public Subject add(Object element) {
        try(var ignored = writeLock.lock()) {
            sub = sub.add(element);
        }
        return this;
    }

    @Override
    public Subject unset() {
        try(var ignored = writeLock.lock()) {
            sub = sub.unset();
        }
        return this;
    }

    @Override
    public Subject unset(Object key) {
        try(var ignored = writeLock.lock()) {
            sub = sub.unset(key);
        }
        return this;
    }

    @Override
    public Subject unset(Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            sub = sub.unset(key, value);
        }
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        try(var ignored = writeLock.lock()) {
            sub = sub.unsetAt(slot);
        }
        return this;
    }

    @Override
    public Subject prime() {
        Subject prime;
        try(var ignored = readLock.lock()) {
            prime = new SolidSubject(sub.prime().fuse());
        }
        return prime;
    }

    @Override
    public Subject recent() {
        Subject recent;
        try(var ignored = readLock.lock()) {
            recent = new SolidSubject(sub.recent().fuse());
        }
        return recent;
    }

    @Override
    public Subject get(Object key) {
        Subject get;
        try(var ignored = readLock.lock()) {
            get = new SolidSubject(sub.get(key).fuse());
        }
        return get;
    }

    @Override
    public Subject get(Object ... keys) {
        Subject get;
        try(var ignored = readLock.lock()) {
            get = new SolidSubject(sub.get(keys).fuse());
        }
        return get;
    }

    @Override
    public Subject getAt(Slot slot) {
        Subject in;
        try(var ignored = readLock.lock()) {
            in = new SolidSubject(sub.getAt(slot).fuse());
        }
        return in;
    }

    @Override
    public Subject getAt(int slotIndex) {
        Subject in;
        try(var ignored = readLock.lock()) {
            in = new SolidSubject(sub.getAt(slotIndex).fuse());
        }
        return in;
    }

    @Override
    public Subject key() {
        Subject key;
        try(var ignored = readLock.lock()) {
            key = new SolidSubject(sub.key().fuse());
        }
        return key;
    }

    @Override
    public Object direct() {
        Object direct;
        try(var ignored = readLock.lock()) {
            direct = sub.direct();
        }
        return direct;
    }

    @Override
    public <B> B asExpected() {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.asExpected();
        }
        return b;
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.as(requestedType);
        }
        return b;
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.as(requestedType);
        }
        return b;
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.as(requestedType, reserve);
        }
        return b;
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.as(requestedType, reserve);
        }
        return b;
    }

    @Override
    public <B> B orGiven(B reserve) {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.orGiven(reserve);
        }
        return b;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        B b;
        try(var ignored = readLock.lock()) {
            b = sub.orDo(supplier);
        }
        return b;
    }

    @Override
    public boolean is(Class<?> type) {
        boolean is;
        try(var ignored = readLock.lock()) {
            is = sub.is(type);
        }
        return is;
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject spared;
        try(var ignored = writeLock.lock()) {
            spared = sub.get(key);
            if(!spared.present()) {
                sub = sub.set(key, reserve);
                spared = sub.get(key);
            }
        }
        return new SolidSubject(spared);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject spared;
        try(var ignored = writeLock.lock()) {
            spared = sub.get(key);
            if(!spared.present()) {
                sub = sub.set(key, supplier.get());
                spared = sub.get(key);
            }
        }
        return new SolidSubject(spared);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject spared;
        try(var ignored = writeLock.lock()) {
            spared = sub.get(key);
            if(!spared.present()) {
                sub = sub.set(key, function.apply(argument));
                spared = sub.get(key);
            }
        }
        return new SolidSubject(spared);
    }

    @Override
    public Subject take(Object key) {
        Subject taken;
        try(var ignored = writeLock.lock()) {
            taken = sub.get(key);
            sub = sub.unset(key);
        }
        return new SolidSubject(taken);
    }

    @Override
    public Subject takeAt(Slot slot) {
        Subject taken;
        try(var ignored = writeLock.lock()) {
            taken = sub.getAt(slot);
            if(taken.present()) sub = sub.unset(taken.key().direct());
        }
        return new SolidSubject(taken);
    }

    @Override
    public boolean present() {
        boolean settled;
        try(var ignored = readLock.lock()) {
            settled = sub.present();
        }
        return settled;
    }

    @Override
    public boolean absent() {
        boolean desolated;
        try(var ignored = readLock.lock()) {
            desolated = sub.absent();
        }
        return desolated;
    }

    @Override
    public int size() {
        int size;
        try(var ignored = readLock.lock()) {
            size = sub.size();
        }
        return size;
    }

    @Override
    public Wave<Subject> iterator(Slot slot, boolean reverse) {
        Wave<Subject> it;
        try(var ignored = writeLock.lock()) {
            it = new HomogenizedSubjectIterator(sub, reverse, slot);
        }
        return it;
    }

    @Override
    public Subject alter(Iterable<? extends Subject> iterable) {
        try(var ignored = writeLock.lock()) {
            sub = sub.alter(iterable);
        }
        return this;
    }

    @Override
    public Subject input(Iterable<? extends Subject> iterable) {
        try(var ignored = writeLock.lock()) {
            sub = sub.input(iterable);
        }
        return this;
    }

    @Override
    public boolean fused() {
        boolean fused;
        try(var ignored = readLock.lock()) {
            fused = sub.fused();
        }
        return fused;
    }

    @Override
    public String toString() {
        String string;
        try(var ignored = readLock.lock()) {
            string = sub.toString();
        }
        return string;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        try(var ignored = writeLock.lock()) {
            sub = sub.setAt(slot, element);
        }
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            sub = sub.setAt(slot, key, value);
        }
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        try(var ignored = writeLock.lock()) {
            sub = sub.putAt(slot, element);
        }
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            sub = sub.putAt(slot, key, value);
        }
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        try(var ignored = writeLock.lock()) {
            sub = sub.addAt(slot, element);
        }
        return this;
    }
}*/
