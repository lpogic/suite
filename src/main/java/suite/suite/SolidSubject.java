package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Series;
import suite.suite.util.Glass;

import java.util.Iterator;
import java.util.function.Supplier;

public class SolidSubject extends Subject {

    class HomogenizedSubjectIterator implements Browser {
        Subject sub;
        boolean reverse;
        Browser browser;


        HomogenizedSubjectIterator(Browser browser, Subject sub, boolean reverse) {
            this.sub = sub;
            this.reverse = reverse;
            this.browser = browser;
        }

        @Override
        public boolean hasNext() {
            if(sub.origin != subject.origin) {
                browser = subject.iterator(reverse);
                sub = subject;
            }
            return browser.hasNext();
        }

        @Override
        public Subject next() {
            return new SolidSubject(browser.next());
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
    public Object raw() {
        return subject.raw();
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
        return new HomogenizedSubjectIterator(subject.iterator(reverse), subject, reverse);
    }

    @Override
    public Browser browser(Object start, boolean reverse) {
        return new HomogenizedSubjectIterator(subject.browser(start, reverse), subject, reverse);
    }

    @Override
    public Subject set(Object element) {
        subject = subject.set(element);
        return this;
    }

    @Override
    public Subject aimedSet(Object aim, Object element) {
        subject = subject.aimedSet(aim, element);
        return this;
    }

    @Override
    public Subject inset(Object in, Subject $set) {
        subject = subject.inset(in, $set);
        return this;
    }

    @Override
    public Subject aimedInset(Object aim, Object in, Subject $set) {
        subject = subject.aimedInset(aim, in, $set);
        return this;
    }

    @Override
    public Subject swap(Object o1, Object o2) {
        subject = subject.swap(o1, o2);
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
    public Subject sate(Object element) {
        var $ = get(element);
        if($.present()) return $;
        set(element);
        return get(element);
    }

    @Override
    public Subject sate(Object element, Subject $set) {
        var $ = get(element);
        inset($.present() ? $.raw() : element, $set);
        return get(element);
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
                Object o = it.raw();
                if (it.real(o)) {
                    $ = $.inset(o, it.jump(o));
                } else {
                    $ = $.set(o);
                }
            }
        }
        subject = $;
        return this;
    }

    @Override
    public Subject aimedAlter(Object sequent, Iterable<? extends Subject> iterable) {
        var $ = subject;
        for(var it : iterable) {
            if(it.present()) {
                Object o = it.raw();
                if (it.real(o)) {
                    $ = $.aimedInset(sequent, o, it.jump(o));
                } else {
                    $ = $.aimedSet(sequent, o);
                }
            }
        }
        subject = $;
        return this;
    }

    @Override
    public Series getEntire(Iterable<?> iterable) {
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
    public Series takeEntire(Iterable<?> iterable) {
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
