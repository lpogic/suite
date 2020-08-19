package suite.suite;

import suite.suite.util.FluidIterator;
import suite.suite.util.Fluid;
import suite.suite.util.Glass;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.function.Supplier;

class ThreadySubject implements Subject {

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

    private Subject subject;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ThreadyLock writeLock = new ThreadyLock(lock.writeLock());
    private final ThreadyLock readLock = new ThreadyLock(lock.readLock());


    ThreadySubject() {
        subject = ZeroSubject.getInstance();
    }

    ThreadySubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Subject set(Object element) {
        try(var ignored = writeLock.lock()) {
            subject = subject.set(element);
        }
        return this;
    }

    @Override
    public Subject set(Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            subject = subject.set(key, value);
        }
        return this;
    }

    @Override
    public Subject put(Object value) {
        try(var ignored = writeLock.lock()) {
            subject = subject.put(value);
        }
        return this;
    }

    @Override
    public Subject put(Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            subject = subject.put(key, value);
        }
        return this;
    }

    @Override
    public Subject add(Object element) {
        try(var ignored = writeLock.lock()) {
            subject = subject.add(element);
        }
        return this;
    }

    @Override
    public Subject unset() {
        try(var ignored = writeLock.lock()) {
            subject = subject.unset();
        }
        return this;
    }

    @Override
    public Subject unset(Object key) {
        try(var ignored = writeLock.lock()) {
            subject = subject.unset(key);
        }
        return this;
    }

    @Override
    public Subject unset(Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            subject = subject.unset(key, value);
        }
        return this;
    }

    @Override
    public Subject unsetAt(Slot slot) {
        try(var ignored = writeLock.lock()) {
            subject = subject.unsetAt(slot);
        }
        return this;
    }

    @Override
    public Subject prime() {
        Subject prime;
        try(var ignored = readLock.lock()) {
            prime = new SolidSubject(subject.prime());
        }
        return prime;
    }

    @Override
    public Subject recent() {
        Subject recent;
        try(var ignored = readLock.lock()) {
            recent = new SolidSubject(subject.recent());
        }
        return recent;
    }

    @Override
    public Subject get(Object key) {
        Subject get;
        try(var ignored = readLock.lock()) {
            get = new SolidSubject(subject.get(key));
        }
        return get;
    }

    @Override
    public Subject getAt(Slot slot) {
        Subject at;
        try(var ignored = readLock.lock()) {
            at = new SolidSubject(subject.getAt(slot));
        }
        return at;
    }

    @Override
    public Subject getAt(int slotIndex) {
        Subject at;
        try(var ignored = readLock.lock()) {
            at = new SolidSubject(subject.getAt(slotIndex));
        }
        return at;
    }

    @Override
    public Subject key() {
        Subject key;
        try(var ignored = readLock.lock()) {
            key = new SolidSubject(subject.key());
        }
        return key;
    }

    @Override
    public Object direct() {
        Object direct;
        try(var ignored = readLock.lock()) {
            direct = subject.direct();
        }
        return direct;
    }

    @Override
    public <B> B asExpected() {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.asExpected();
        }
        return b;
    }

    @Override
    public <B> B asGiven(Class<B> requestedType) {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.asGiven(requestedType);
        }
        return b;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType) {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.asGiven(requestedType);
        }
        return b;
    }

    @Override
    public <B> B asGiven(Class<B> requestedType, B reserve) {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.asGiven(requestedType, reserve);
        }
        return b;
    }

    @Override
    public <B> B asGiven(Glass<? super B, B> requestedType, B reserve) {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.asGiven(requestedType, reserve);
        }
        return b;
    }

    @Override
    public <B> B orGiven(B reserve) {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.orGiven(reserve);
        }
        return b;
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        B b;
        try(var ignored = readLock.lock()) {
            b = subject.orDo(supplier);
        }
        return b;
    }

    @Override
    public boolean assigned(Class<?> type) {
        boolean is;
        try(var ignored = readLock.lock()) {
            is = subject.assigned(type);
        }
        return is;
    }

    @Override
    public Subject getSaved(Object key, Object reserve) {
        Subject spared;
        try(var ignored = writeLock.lock()) {
            spared = subject.get(key);
            if(!spared.settled()) {
                subject = subject.set(key, reserve);
                spared = subject.get(key);
            }
        }
        return new SolidSubject(spared);
    }

    @Override
    public Subject getDone(Object key, Supplier<?> supplier) {
        Subject spared;
        try(var ignored = writeLock.lock()) {
            spared = subject.get(key);
            if(!spared.settled()) {
                subject = subject.set(key, supplier.get());
                spared = subject.get(key);
            }
        }
        return new SolidSubject(spared);
    }

    @Override
    public Subject getDone(Object key, Function<Subject, ?> function, Subject argument) {
        Subject spared;
        try(var ignored = writeLock.lock()) {
            spared = subject.get(key);
            if(!spared.settled()) {
                subject = subject.set(key, function.apply(argument));
                spared = subject.get(key);
            }
        }
        return new SolidSubject(spared);
    }

    @Override
    public Subject take(Object key) {
        Subject taken;
        try(var ignored = writeLock.lock()) {
            taken = subject.get(key);
            subject = subject.unset(key);
        }
        return new SolidSubject(taken);
    }

    @Override
    public Subject takeAt(Slot slot) {
        Subject taken;
        try(var ignored = writeLock.lock()) {
            taken = subject.getAt(slot);
            if(taken.settled()) subject = subject.unset(taken.key().direct());
        }
        return new SolidSubject(taken);
    }

    @Override
    public boolean settled() {
        boolean settled;
        try(var ignored = readLock.lock()) {
            settled = subject.settled();
        }
        return settled;
    }

    @Override
    public boolean desolated() {
        boolean desolated;
        try(var ignored = readLock.lock()) {
            desolated = subject.desolated();
        }
        return desolated;
    }

    @Override
    public int size() {
        int size;
        try(var ignored = readLock.lock()) {
            size = subject.size();
        }
        return size;
    }

    @Override
    public Fluid reverse() {
        Fluid fluid;
        try(var ignored = writeLock.lock()) {
            subject = subject.homogenize();
            fluid = () -> new FluidIterator<>() {
                final FluidIterator<Subject> subIt = subject.reverse().iterator();
                boolean hasNext;
                Subject next;

                @Override
                public boolean hasNext() {
                    if(hasNext) return true;
                    try(var ignored = readLock.lock()) {
                        if(hasNext = subIt.hasNext()) next = subIt.next();
                    }
                    return hasNext;
                }

                @Override
                public Subject next() {
                    hasNext = false;
                    return new SolidSubject(next);
                }
            };
        }
        return fluid;
    }

    @Override
    public FluidIterator<Subject> iterator() {
        FluidIterator<Subject> it;
        try(var ignored = writeLock.lock()) {
            subject = subject.homogenize();
            it = new FluidIterator<>() {
                final FluidIterator<Subject> subIt = subject.front().iterator();
                boolean hasNext;
                Subject next;

                @Override
                public boolean hasNext() {
                    if(hasNext) return true;
                    try(var ignored = readLock.lock()) {
                        hasNext = subIt.hasNext();
                        if(hasNext) next = subIt.next();
                    }
                    return hasNext;
                }

                @Override
                public Subject next() {
                    hasNext = false;
                    return new SolidSubject(next);
                }
            };
        }
        return it;
    }

    @Override
    public Subject homogenize() {
        return this;
    }

    @Override
    public Subject insetAll(Iterable<Subject> iterable) {
        try(var ignored = writeLock.lock()) {
            subject = subject.insetAll(iterable);
        }
        return this;
    }

    @Override
    public Subject inputAll(Iterable<Subject> iterable) {
        try(var ignored = writeLock.lock()) {
            subject = subject.inputAll(iterable);
        }
        return this;
    }

    @Override
    public boolean fused() {
        boolean fused;
        try(var ignored = readLock.lock()) {
            fused = subject.fused();
        }
        return fused;
    }

    @Override
    public boolean homogeneous() {
        return true;
    }

    @Override
    public String toString() {
        String string;
        try(var ignored = readLock.lock()) {
            string = subject.toString();
        }
        return string;
    }

    @Override
    public Subject setAt(Slot slot, Object element) {
        try(var ignored = writeLock.lock()) {
            subject = subject.setAt(slot, element);
        }
        return this;
    }

    @Override
    public Subject setAt(Slot slot, Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            subject = subject.setAt(slot, key, value);
        }
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object element) {
        try(var ignored = writeLock.lock()) {
            subject = subject.putAt(slot, element);
        }
        return this;
    }

    @Override
    public Subject putAt(Slot slot, Object key, Object value) {
        try(var ignored = writeLock.lock()) {
            subject = subject.putAt(slot, key, value);
        }
        return this;
    }

    @Override
    public Subject addAt(Slot slot, Object element) {
        try(var ignored = writeLock.lock()) {
            subject = subject.addAt(slot, element);
        }
        return this;
    }
}
