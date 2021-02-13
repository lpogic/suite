package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Glass;
import suite.suite.util.Series;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class WonkySubject{}/* extends SolidSubject {

    class Vestige {
        WeakReference<?> ref;
        int hashCode;

        public Vestige(Object o) {
            ref = new WeakReference<>(o);
            hashCode = Objects.hashCode(o);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vestige vestige = (Vestige) o;
            return Objects.equals(ref.get(), vestige.ref.get());
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }

    class HomogenizedSubjectIterator implements Browser {
        Subject sub;
        boolean reverse;
        Browser it;

        boolean hasNext = false;
        Subject next;


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

            if(hasNext) return true;
            if(sub.origin != subject.origin) {
                it = subject.iterator(reverse);
                sub = subject;
            }
            while (hasNext = it.hasNext()) {
                next = strengthen(it.next());
                if(next.present()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Subject next() {
            return new SolidSubject(next);
        }
    }

    WonkySubject() {
        super();
    }

    private Subject strengthen(Subject s) {
        if(s.present()) {
            if(s.is(Vestige.class)) {
                Vestige v = s.asExpected();
                Object o = v.ref.get();
                if (o != null) {
                    return new MonoSubject(o, s.up().get());
                }
            } else return s;
        }
        return ZeroSubject.getInstance();
    }

    @Override
    protected Subject materialize(Object element) {
        subject = subject.materialize(new Vestige(element));
        return this;
    }

    @Override
    protected Subject materialize() {
        subject = subject.materialize();
        return this;
    }

    @Override
    protected Subject jump() {
        return strengthen(subject.jump());
    }

    @Override
    protected Subject jump(Object element) {
        return strengthen(subject.jump(new Vestige(element)));
    }

    @Override
    protected boolean real(Object element) {
        return subject.real(new Vestige(element));
    }

    @Override
    public Subject first() {
        return front().first();
    }

    @Override
    public Subject last() {
        return reverse().first();
    }

    @Override
    public Subject get(Object element) {
        return strengthen(subject.get(new Vestige(element)));
    }

    @Override
    public Object direct() {
        return strengthen(subject).direct();
    }

    @Override
    public <B> B asExpected() {
        return strengthen(subject).asExpected();
    }

    @Override
    public <B> B as(Class<B> requestedType) {
        return strengthen(subject).as(requestedType);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType) {
        return strengthen(subject).as(requestedType);
    }

    @Override
    public <B> B as(Class<B> requestedType, B reserve) {
        return strengthen(subject).as(requestedType, reserve);
    }

    @Override
    public <B> B as(Glass<? super B, B> requestedType, B reserve) {
        return strengthen(subject).as(requestedType, reserve);
    }

    @Override
    public <B> B orGiven(B reserve) {
        return strengthen(subject).orGiven(reserve);
    }

    @Override
    public <B> B orDo(Supplier<B> supplier) {
        return strengthen(subject).orDo(supplier);
    }

    @Override
    public boolean is(Class<?> type) {
        return strengthen(subject).is(type);
    }

    @Override
    public boolean present() {
        return strengthen(subject).present();
    }

    @Override
    public boolean present(Object element) {
        return strengthen(subject).present(new Vestige(element));
    }

    @Override
    public boolean absent() {
        return strengthen(subject).absent();
    }

    @Override
    public boolean absent(Object element) {
        return strengthen(subject).absent(new Vestige(element));
    }

    @Override
    public int size() {
        return subject.size();
    }

    @Override
    public Browser iterator(boolean reverse) {
        return new SolidSubject.HomogenizedSubjectIterator(subject, reverse);
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
    public*//* Series takeAll(Iterable<?> iterable) {
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
*/