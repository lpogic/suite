package suite.suite;

import suite.suite.tester.IsFree;
import suite.suite.util.Wave;
import suite.suite.util.Fluid;
import suite.suite.util.Glass;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SolidSubject implements Subject {

    class HomogenizedSubjectIterator implements Wave<Subject> {
        Subject sub;
        boolean reverse;
        Wave<Subject> it;


        HomogenizedSubjectIterator(Subject sub, boolean reverse) {
            this.sub = sub;
            this.reverse = reverse;
            this.it = sub.iterator(reverse);
        }

        @Override
        public boolean hasNext() {
            if(sub != subject) {
                if(sub == ZeroSubject.getInstance()) {
                    it = subject.iterator(reverse);
                } else if(subject == ZeroSubject.getInstance()) {
                    it = Wave.emptyWave();
                }
                sub = subject;
            }
            return it.hasNext();
        }

        @Override
        public Subject next() {
            return new SolidSubject(it.next().separate());
        }
    }

    private Subject subject;

    public SolidSubject() {
        subject = ZeroSubject.getInstance();
    }

    public SolidSubject(Subject subject) {
        this.subject = subject.separate();
    }

    @Override
    public Subject atFirst() {
        return new SolidSubject(subject.atFirst().separate());
    }

    @Override
    public Subject atLast() {
        return new SolidSubject(subject.atLast().separate());
    }

    @Override
    public Subject at(Object key) {
        return new SolidSubject(subject.at(key).separate());
    }

    @Override
    public Subject get() {
        return subject.get();
    }

    @Override
    public Subject get(Object key) {
        return subject.get(key);
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
    public <B> B as(Class<B> requestedType) {
        return subject.as(requestedType);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return subject.as(requestedType);
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        return subject.as(requestedType, reserve);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return subject.as(requestedType, reserve);
    }

    @Override
    public <B> B orGiven(B reserve) {
        return subject.orGiven(reserve);
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
    public boolean notEmpty() {
        return subject.notEmpty();
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
    public Wave<Subject> iterator(boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse);
    }

    @Override
    public Subject set(Object element) {
        subject = subject.set(element);
        return this;
    }

    @Override
    public Subject set(Object element, Subject $set) {
        subject = subject.set(element, $set);
        return this;
    }

    @Override
    public Subject setBefore(Object sequent, Object element) {
        subject = subject.setBefore(sequent, element);
        return this;
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Subject $set) {
        subject = subject.setBefore(sequent, element, $set);
        return this;
    }

    @Override
    public Subject setIf(Object element, Predicate<Subject> test) {
        subject = subject.setIf(element, test);
        return this;
    }

    @Override
    public Subject unset() {
        subject = subject.unset();
        return this;
    }

    @Override
    public Subject unset(Object element) {
        subject = subject.unset(element);
        return this;
    }

    @Override
    public Subject in() {
        var k = new Suite.AutoKey();
        subject = subject.set(k);
        return subject.get(k);
    }

    @Override
    public Subject in(Object key) {
        subject = subject.setIf(key, new IsFree(key));
        return subject.get(key);
    }

    @Override
    public Subject inset(Object... elements) {
        Subject $ = subject;
        for(Object e : elements) {
            $ = $.in(e);
        }
        return this;
    }

    @Override
    public Fluid front() {
        return this;
    }

    @Override
    public Fluid reverse() {
        return () -> iterator(true);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = at(key);
        if(taken.notEmpty()) subject = subject.unset(key);
        return taken;
    }

    @Override
    public Subject join(Iterable<? extends Subject> iterable) {
        for(var it : iterable) {
            subject = subject.set(it.direct(), it.get());
        }
        return this;
    }

    @Override
    public Subject joinBefore(Object sequent, Iterable<? extends Subject> iterable) {
        for(var it : iterable) {
            subject = subject.setBefore(sequent, it.direct(), it.get());
        }
        return this;
    }

    @Override
    public Subject setAll(Iterable<?> iterable) {
        for(Object it : iterable) {
            subject = subject.set(it);
        }
        return this;
    }

    @Override
    public Subject unsetAll(Iterable<?> iterable) {
        for(var it : iterable) {
            subject = subject.unset(it);
        }
        return this;
    }

    @Override
    public Subject separate() {
        return subject.separate();
    }
}
