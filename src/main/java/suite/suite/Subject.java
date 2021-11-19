package suite.suite;

import suite.suite.action.Action;
import suite.suite.util.Series;
import suite.suite.util.Glass;
import suite.suite.util.Browser;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings("UnusedReturnValue")
public abstract class Subject implements Sub {

    protected final int origin;

    public Subject(int origin) {
        this.origin = origin;
    }

    public Subject() {
        this.origin = hashCode();
    }

    protected abstract Subject materialize();
    protected abstract Subject materialize(Object element);
    protected abstract Subject jump();
    protected abstract Subject jump(Object element);
    protected abstract boolean real(Object element);

    public Subject out() {
        return Suite.inset(this);
    }
    public Subject out(Object o) {
        return Suite.inset(o, this);
    }

    public abstract Subject first();
    public abstract Subject last();
    public abstract Subject get(Object element);
    public abstract Object raw();
    public abstract <B> B asExpected();
    public <B> B one() {
        return asExpected();
    }
    public abstract <B> B as(Class<B> requestedType);
    public abstract <B> B as(Glass<? super B, B> requestedType);
    public abstract <B> B as(Class<B> requestedType, B reserve);
    public abstract <B> B as(Glass<? super B, B> requestedType, B reserve);
    public abstract <B> B orGiven(B reserve);
    public abstract <B> B orDo(Supplier<B> supplier);
    public abstract boolean is(Class<?> type);
    public abstract boolean present();
    public abstract boolean present(Object element);
    public abstract boolean absent();
    public abstract boolean absent(Object element);
    public abstract int size();
    public abstract Browser iterator(boolean reverse);
    public Browser iterator() {
        return iterator(false);
    }
    public abstract Browser browser(Object start, boolean reverse);
    public Browser browser(Object start) {
        return browser(start, false);
    }
    public Series front() {
        throw new UnsupportedOperationException("Solid method");
    }
    public Series reverse() {
        throw new UnsupportedOperationException("Solid method");
    }
    public Series front(Object start) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Series reverse(Object start) {
        throw new UnsupportedOperationException("Solid method");
    }

    public abstract Subject set(Object element);
    public Subject put(Object e1, Object ... en) {
        return inset(e1, Suite.put(en));
    }
    public Subject add(Object element) {
        return put(new Suite.Auto(), element);
    }
    public abstract Subject inset(Object in, Subject $set);
    public Subject inset(Subject $set) {
        return inset(new Suite.Auto(), $set);
    }
    public abstract Subject aimedSet(Object aim, Object element);
    public Subject aimedPut(Object aim, Object key, Object... en) {
        return aimedInset(aim, key, Suite.set(en));
    }
    public Subject aimedAdd(Object aim, Object element) {
        return aimedPut(aim, new Suite.Auto(), element);
    }
    public abstract Subject aimedInset(Object aim, Object in, Subject $set);
    public Subject aimedInset(Object aim, Subject $set) {
        return aimedInset(aim, new Suite.Auto(), $set);
    }
    public abstract Subject swap(Object o1, Object o2);
    public Subject reset(Object element) {
        return unset().set(element);
    }
    public abstract Subject unset();
    public abstract Subject unset(Object element);
    public Subject sate(Object element) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject sate(Object element, Subject $set) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject take(Object element) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject alter(Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }
    public Subject aimedAlter(Object sequent, Iterable<? extends Subject> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    public Subject merge(Subject $tree) {
        for(var $ : $tree) {
            var $at = $.at();
            if($at.present()) {
                in($.raw()).merge($at);
            } else {
                sate($.raw());
            }
        }
        return this;
    }

    public Subject introspect(Action converter) {
        for(var $ : Suite.preDfs(Suite.inset(this)).eachIn()) {
            var $t = Suite.set();
            for(var $1 : $) {
                $t.alter(converter.apply($1));
            }
            $.unset().alter($t);
        }
        return this;
    }

    public Subject get(Object... elements) {
        return getEntire(Arrays.asList(elements));
    }
    public Subject getEntire(Iterable<?> iterable) {
        var got = Suite.set();
        for(Object it : iterable) {
            got.alter(get(it));
        }
        return got;
    }

    public Subject set(Object ... elements) {
        return setEntire(Arrays.asList(elements));
    }
    public Subject setEntire(Iterable<?> iterable) {
        for(Object it : iterable) {
            set(it);
        }
        return this;
    }

    public Subject add(Object ... elements) {
        return addEntire(Arrays.asList(elements));
    }
    public Subject addEntire(Iterable<?> iterable) {
        for(Object it : iterable) {
            add(it);
        }
        return this;
    }

    public Subject unset(Object ... elements) {
        return unsetEntire(Arrays.asList(elements));
    }
    public Subject unsetEntire(Iterable<?> iterable) {
        for(Object it : iterable) {
            unset(it);
        }
        return this;
    }

    public Subject take(Object... elements) {
        return takeEntire(Arrays.asList(elements));
    }
    public Subject takeEntire(Iterable<?> iterable) {
        var taken = Suite.set();
        for(Object it : iterable) {
            taken.alter(take(it));
        }
        return taken;
    }

    public Subject merge(Subject ... subjects) {
        return mergeEntire(Arrays.asList(subjects));
    }
    public Subject mergeEntire(Iterable<? extends Subject> iterable) {
        for(var it : iterable) {
            merge(it);
        }
        return this;
    }
    @Override
    public Subject set() {
        return this;
    }
    @Override
    public Subject get() {
        return this;
    }

    abstract Subject separate();
    public Subject print() {
        System.out.println(Suite.toString(this));
        return this;
    }

    @Override
    public String toString() {
        return "$" + Integer.toHexString(hashCode());
    }
}
