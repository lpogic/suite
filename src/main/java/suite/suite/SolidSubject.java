package suite.suite;

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
            return new SolidSubject(it.next());
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
    public Subject at(Object element) {
        return new SolidSubject(new ImaginarySubject(this, element));
    }

    @Override
    public Subject burn(Object element) {
        subject = subject.burn(element);
        return this;
    }

    @Override
    public Subject jump(Object element) {
        return subject.jump(element);
    }

    @Override
    public boolean burned(Object element) {
        return subject.burned(element);
    }

    @Override
    public Subject getFirst() {
        return new SolidSubject(subject.getFirst());
    }

    @Override
    public Subject getLast() {
        return new SolidSubject(subject.getLast());
    }

    @Override
    public Subject get(Object element) {
        return new SolidSubject(subject.get(element));
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
    public boolean is(Class<?> type) {
        return subject.is(type);
    }

    @Override
    public boolean present() {
        return subject.present();
    }

    @Override
    public boolean absent() {
        return subject.absent();
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
    public Subject inset(Object... elements) {
        Subject $ = this;
        int i = 0;
        Object o = null;
        for(Object e : elements) {
            if(i > 0) $ = $.at(o);
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
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.present()) subject = subject.unset(key);
        return taken;
    }

    @Override
    public Subject join(Iterable<? extends Subject> iterable) {
        var $ = subject;
        for(var it : iterable) {
            Object o = it.direct();
            if(it.burned(o)) {
                $ = $.set(o, it.jump(o));
            } else {
                $ = $.set(o);
            }
        }
        subject = $;
        return this;
    }

    @Override
    public Subject joinBefore(Object sequent, Iterable<? extends Subject> iterable) {
        var $ = subject;
        for(var it : iterable) {
            Object o = it.direct();
            if(it.burned(o)) {
                $ = $.setBefore(sequent, o, it.jump(o));
            } else {
                $ = $.setBefore(sequent, o);
            }
        }
        subject = $;
        return this;
    }

    @Override
    public Subject getAll(Iterable<?> iterable) {
        var $ = Suite.set();
        for(Object it : iterable) {
            $.join(get(it));
        }
        return $;
    }

    @Override
    public Subject setAll(Iterable<?> iterable) {
        var $ = subject;
        for(Object it : iterable) {
            $ = $.set(it);
        }
        subject = $;
        return this;
    }

    @Override
    public Subject unsetAll(Iterable<?> iterable) {
        var $ = subject;
        for(Object it : iterable) {
            $ = $.unset(it);
        }
        subject = $;
        return this;
    }

    @Override
    public Subject takeAll(Iterable<?> iterable) {
        var $ = Suite.set();
        for(Object it : iterable) {
            $.join(take(it));
        }
        return $;
    }

    @Override
    public Subject separate() {
        return subject.separate();
    }

    @Override
    public Subject set() {
        subject = subject.set();
        return this;
    }
}
