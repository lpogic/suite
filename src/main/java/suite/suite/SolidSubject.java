package suite.suite;

import suite.suite.tester.IsFree;
import suite.suite.util.Wave;
import suite.suite.util.Fluid;
import suite.suite.util.Glass;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SolidSubject implements Subject {

    class HomogenizedSubjectIterator implements Wave<Vendor> {
        Vendor sub;
        boolean reverse;
        Wave<Vendor> it;


        HomogenizedSubjectIterator(Vendor sub, boolean reverse) {
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
        public Vendor next() {
            return new SolidSubject(it.next().separate().set());
        }
    }

    private Vendor subject;

    public SolidSubject() {
        subject = ZeroSubject.getInstance();
    }

    public SolidSubject(Vendor subject) {
        this.subject = subject.separate();
    }

    @Override
    public Vendor atFirst() {
        return new SolidSubject(subject.atFirst().separate());
    }

    @Override
    public Vendor atLast() {
        return new SolidSubject(subject.atLast().separate());
    }

    @Override
    public Vendor at(Object key) {
        return new SolidSubject(subject.at(key).separate());
    }

    @Override
    public Vendor get() {
        return subject.get();
    }

    @Override
    public Vendor get(Object key) {
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
    public Wave<Vendor> iterator(boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse);
    }

    @Override
    public Subject set(Object element) {
        subject = subject.set().set(element);
        return this;
    }

    @Override
    public Subject set(Object element, Vendor $set) {
        subject = subject.set().set(element, $set);
        return this;
    }

    @Override
    public Subject setBefore(Object sequent, Object element) {
        subject = subject.set().setBefore(sequent, element);
        return this;
    }

    @Override
    public Subject setBefore(Object sequent, Object element, Vendor $set) {
        subject = subject.set().setBefore(sequent, element, $set);
        return this;
    }

    @Override
    public Subject setIf(Object element, Predicate<Vendor> test) {
        subject = subject.set().setIf(element, test);
        return this;
    }

    @Override
    public Subject unset() {
        subject = subject.set().unset();
        return this;
    }

    @Override
    public Subject unset(Object element) {
        subject = subject.set().unset(element);
        return this;
    }

    @Override
    public Subject in() {
        var $ = Suite.set();
        subject = subject.set().set(new Suite.AutoKey(), $);
        return $;
    }

    @Override
    public Subject in(Object key) {
        var at = subject.at(key);
        if(at.isEmpty()) {
            var $ = Suite.set();
            subject = subject.set().set(key, $);
            return $;
        } else {
            var $ = at.get().set();
            subject = subject.set().set(at.direct(), $);
            return $;
        }
    }

    @Override
    public Subject inset(Object... elements) {
        Subject $ = this;
        int i = 0;
        Object o = null;
        for(Object e : elements) {
            if(i > 0) $ = $.in(o);
            o = e;
            ++i;
        }
        if(i > 0) $.set(o);

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
    public Vendor take(Object key) {
        Vendor taken = at(key);
        if(taken.notEmpty()) subject = subject.set().unset(key);
        return taken;
    }

    @Override
    public Subject join(Iterable<? extends Vendor> iterable) {
        var $ = subject.set();
        for(var it : iterable) {
            $ = $.set(it.direct(), it.get());
        }
        subject = $;
        return this;
    }

    @Override
    public Subject joinBefore(Object sequent, Iterable<? extends Vendor> iterable) {
        var $ = subject.set();
        for(var it : iterable) {
            $ = $.setBefore(sequent, it.direct(), it.get());
        }
        subject = $;
        return this;
    }

    @Override
    public Subject setAll(Iterable<?> iterable) {
        var $ = subject.set();
        for(Object it : iterable) {
            $ = $.set(it);
        }
        subject = $;
        return this;
    }

    @Override
    public Subject unsetAll(Iterable<?> iterable) {
        var $ = subject.set();
        for(Object it : iterable) {
            $ = $.unset(it);
        }
        subject = $;
        return this;
    }

    @Override
    public Vendor separate() {
        return subject.separate();
    }
}
