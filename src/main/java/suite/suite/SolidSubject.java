package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Series;
import suite.suite.util.Glass;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SolidSubject extends Subject {

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
            if(sub.origin != subject.origin) {
                it = subject.iterator(reverse);
                sub = subject;
            }
            return it.hasNext();
        }

        @Override
        public Subject next() {
            return new SolidSubject(it.next());
        }
    }

    Subject subject;

    public SolidSubject() {
        subject = ZeroSubject.getInstance();
    }

    public SolidSubject(Subject subject) {
        this.subject = subject.separate();
    }

    @Override
    protected Subject materialize(Object element) {
        subject = subject.materialize(element);
        return this;
    }

    @Override
    protected Subject materialize() {
        subject = subject.materialize();
        return this;
    }

    @Override
    protected Subject jump() {
        return subject.jump();
    }

    @Override
    protected Subject jump(Object element) {
        return subject.jump(element);
    }

    @Override
    protected boolean real(Object element) {
        return subject.real(element);
    }

    @Override
    public Subject first() {
        return new SolidSubject(subject.first());
    }

    @Override
    public Subject last() {
        return new SolidSubject(subject.last());
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
    public boolean present(Object element) {
        return subject.present(element);
    }

    @Override
    public boolean absent() {
        return subject.absent();
    }

    @Override
    public boolean absent(Object element) {
        return subject.absent(element);
    }

    @Override
    public int size() {
        return subject.size();
    }

    @Override
    public Browser iterator(boolean reverse) {
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
    public Subject strictSet(Object sequent, Object element) {
        subject = subject.strictSet(sequent, element);
        return this;
    }

    @Override
    public Subject strictSet(Object sequent, Object element, Subject $set) {
        subject = subject.strictSet(sequent, element, $set);
        return this;
    }

    @Override
    public Subject shift(Object out, Object in) {
        subject = subject.shift(out, in);
        return this;
    }

    @Override
    public Subject setIf(Object element, Predicate<Object> test) {
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
    public Subject setUp(Object... elements) {
        Sub $ = this;
        int i = 0;
        Object o = null;
        for(Object e : elements) {
            if(i > 0) $ = $.up(o);
            o = e;
            ++i;
        }
        if(i > 0) $.set(o);

        return this;
    }

    @Override
    public Subject addUp(Object... elements) {
        Sub $ = up(new Suite.Auto());
        int i = 0;
        Object o = null;
        for(Object e : elements) {
            if(i > 0) $ = $.up(o);
            o = e;
            ++i;
        }
        if(i > 0) $.set(o);
        else $.set();

        return this;
    }

    @Override
    public Series front() {
        return this;
    }

    @Override
    public Series reverse() {
        return () -> iterator(true);
    }

    @Override
    public Subject getFilled(Object element) {
        subject = subject.setIf(element, subject::absent);
        return subject.get(element);
    }

    @Override
    public Subject getFilled(Object element, Subject $set) {
        subject = subject.setIf(element, $set, subject::absent);
        return subject.get(element);
    }

    @Override
    public Subject take(Object key) {
        Subject taken = get(key);
        if(taken.present()) subject = subject.unset(key);
        return taken;
    }

    @Override
    public Subject alter(Iterable<? extends Subject> iterable) {
        var $ = subject;
        for(var it : iterable) {
            if(it.present()) {
                Object o = it.direct();
                if (it.real(o)) {
                    $ = $.set(o, it.jump(o));
                } else {
                    $ = $.set(o);
                }
            }
        }
        subject = $;
        return this;
    }

    @Override
    public Subject strictAlter(Object sequent, Iterable<? extends Subject> iterable) {
        var $ = subject;
        for(var it : iterable) {
            if(it.present()) {
                Object o = it.direct();
                if (it.real(o)) {
                    $ = $.strictSet(sequent, o, it.jump(o));
                } else {
                    $ = $.strictSet(sequent, o);
                }
            }
        }
        subject = $;
        return this;
    }

    @Override
    public Series getAll(Iterable<?> iterable) {
        return () -> new Browser() {
            final Iterator<?> it = iterable.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Subject next() {
                return get(it.next());
            }
        };
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
    public Series takeAll(Iterable<?> iterable) {
        return () -> new Browser() {
            final Iterator<?> it = iterable.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Subject next() {
                return take(it.next());
            }
        };
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
