package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;
import suite.suite.util.Series;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

class ThreadySubject extends SolidSubject {

    class HomogenizedSubjectIterator implements Browser {
        Subject sub;
        boolean reverse;
        Browser it;

        HomogenizedSubjectIterator(Subject sub, boolean reverse) {
            this.sub = sub;
            this.reverse = reverse;
            this.it = sub.iterator(reverse);
        }

        @Override
        public boolean hasNext() {
            try(var ignored = readLock.lock()) {
                if (sub.origin != subject.origin) {
                    it = subject.iterator(reverse);
                    sub = subject;
                }
                return it.hasNext();
            }
        }

        @Override
        public Subject next() {
            try(var ignored = readLock.lock()) {
                return new SolidSubject(it.next());
            }
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

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ThreadyLock writeLock = new ThreadyLock(lock.writeLock());
    private final ThreadyLock readLock = new ThreadyLock(lock.readLock());


    ThreadySubject() {
        super();
    }

    ThreadySubject(Subject sub) {
        super(sub);
    }

    @Override
    protected Subject materialize(Object element) {
        try(var ignored = writeLock.lock()) {
            super.materialize();
            return this;
        }
    }

    @Override
    protected Subject materialize() {
        try(var ignored = writeLock.lock()) {
            super.materialize();
            return this;
        }
    }

    @Override
    protected Subject jump() {
        try(var ignored = readLock.lock()) {
            return super.jump();
        }
    }

    @Override
    protected Subject jump(Object element) {
        try(var ignored = readLock.lock()) {
            return super.jump(element);
        }
    }

    @Override
    protected boolean real(Object element) {
        try(var ignored = readLock.lock()) {
            return super.real(element);
        }
    }


    @Override
    public Subject first() {
        try(var ignored = readLock.lock()) {
            return super.first();
        }
    }

    @Override
    public Subject last() {
        try(var ignored = readLock.lock()) {
            return super.last();
        }
    }

    @Override
    public Subject get(Object element) {
        try(var ignored = readLock.lock()) {
            return super.get(element);
        }
    }

    @Override
    public Object raw() {
        try(var ignored = readLock.lock()) {
            return super.raw();
        }
    }

    @Override
    public <B> B asExpected() {
        try(var ignored = readLock.lock()) {
            return super.asExpected();
        }
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        try(var ignored = readLock.lock()) {
            return super.as(requestedType);
        }
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        try(var ignored = readLock.lock()) {
            return super.as(requestedType);
        }
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        try(var ignored = readLock.lock()) {
            return super.as(requestedType, reserve);
        }
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        try(var ignored = readLock.lock()) {
            return super.as(requestedType, reserve);
        }
    }

    @Override
    public <B> B orGiven(B reserve) {
        try(var ignored = readLock.lock()) {
            return super.orGiven(reserve);
        }
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        try(var ignored = readLock.lock()) {
            return super.orDo(supplier);
        }
    }

    @Override
    public boolean is(Class<?> type) {
        try(var ignored = readLock.lock()) {
            return super.is(type);
        }
    }

    @Override
    public boolean present() {
        try(var ignored = readLock.lock()) {
            return super.present();
        }
    }

    @Override
    public boolean present(Object element) {
        try(var ignored = readLock.lock()) {
            return super.present(element);
        }
    }

    @Override
    public boolean absent() {
        try(var ignored = readLock.lock()) {
            return super.absent();
        }
    }

    @Override
    public boolean absent(Object element) {
        try(var ignored = readLock.lock()) {
            return super.absent(element);
        }
    }

    @Override
    public int size() {
        try(var ignored = readLock.lock()) {
            return super.size();
        }
    }

    @Override
    public Browser iterator(boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse);
    }

    @Override
    public Subject set(Object element) {
        try(var ignored = writeLock.lock()) {
            super.set(element);
            return this;
        }
    }

    @Override
    public Subject aimedSet(Object aim, Object element) {
        try(var ignored = writeLock.lock()) {
            super.aimedSet(aim, element);
            return this;
        }
    }

    @Override
    public Subject inset(Object in, Subject $set) {
        try(var ignored = writeLock.lock()) {
            super.inset(in, $set);
            return this;
        }
    }

    @Override
    public Subject aimedInset(Object aim, Object in, Subject $set) {
        try(var ignored = writeLock.lock()) {
            super.aimedInset(aim, in, $set);
            return this;
        }
    }

    @Override
    public Subject swap(Object o1, Object o2) {
        try(var ignored = writeLock.lock()) {
            super.swap(o1, o2);
            return this;
        }
    }

    @Override
    public Subject unset() {
        try(var ignored = writeLock.lock()) {
            super.unset();
            return this;
        }
    }

    @Override
    public Subject unset(Object element) {
        try(var ignored = writeLock.lock()) {
            super.unset(element);
            return this;
        }
    }

    @Override
    public Series front() {
        return () -> iterator(false);
    }

    @Override
    public Series reverse() {
        return () -> iterator(true);
    }

    @Override
    public Series front(Object start) {
        return () -> browser(start, false);
    }

    @Override
    public Series reverse(Object start) {
        return () -> browser(start, true);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.present()) unset(key);
        return taken;
    }

    @Override
    public Subject alter(Iterable<? extends Subject> iterable) {
        for(var it : iterable) {
            if(it.present()) {
                Object o = it.raw();
                if (it.real(o)) {
                    this.inset(o, it.jump(o));
                } else {
                    set(o);
                }
            }
        }
        return this;
    }

    @Override
    public Subject aimedAlter(Object sequent, Iterable<? extends Subject> iterable) {
        for(var it : iterable) {
            if(it.present()) {
                Object o = it.raw();
                if (it.real(o)) {
                    this.aimedInset(sequent, o, it.jump(o));
                } else {
                    this.aimedSet(sequent, o);
                }
            }
        }
        return this;
    }

    @Override
    public Subject separate() {
        try(var ignored = readLock.lock()) {
            return subject.separate();
        }
    }

    @Override
    public Subject set() {
        try(var ignored = writeLock.lock()) {
            super.set();
            return this;
        }
    }
}
