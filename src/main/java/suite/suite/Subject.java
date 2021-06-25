package suite.suite;

import suite.suite.action.Action;
import suite.suite.util.Series;
import suite.suite.util.Glass;
import suite.suite.util.Browser;

import java.util.List;
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

    public abstract Subject first();
    public abstract Subject last();
    public abstract Subject get(Object element);
    public abstract Object raw();
    public abstract <B> B asExpected();
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
    public Series front() {
        throw new UnsupportedOperationException("Solid method");
    }
    public Series reverse() {
        throw new UnsupportedOperationException("Solid method");
    }

    public abstract Subject set(Object element);
    public abstract Subject aimedSet(Object aim, Object element);
    public Subject put(Object e1, Object ... en) {
        return inset(e1, Suite.put(en));
    }
    public Subject aimedPut(Object aim, Object key, Object... en) {
        return aimedInset(aim, key, Suite.set(en));
    }
    public Subject add(Object element) {
        return put(new Suite.Auto(), element);
    }
    public Subject aimedAdd(Object aim, Object element) {
        return aimedPut(aim, new Suite.Auto(), element);
    }
    public abstract Subject inset(Object in, Subject $set);
    public abstract Subject aimedInset(Object aim, Object in, Subject $set);
    public Subject inset(Subject $set) {
        return inset(new Suite.Auto(), $set);
    }
    public Subject aimedInset(Object aim, Subject $set) {
        return aimedInset(aim, new Suite.Auto(), $set);
    }
    public Subject shift(Object in) {
        return shift(raw(), in);
    }
    public abstract Subject shift(Object out, Object in);
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
        return getEntire(List.of(elements)).set();
    }
    public Series getEntire(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
    }

    public Subject set(Object ... elements) {
        return setEntire(List.of(elements));
    }
    public Subject setEntire(Iterable<?> iterable) {
        for(Object it : iterable) {
            set(it);
        }
        return this;
    }

    public Subject add(Object ... elements) {
        return addEntire(List.of(elements));
    }
    public Subject addEntire(Iterable<?> iterable) {
        for(Object it : iterable) {
            add(it);
        }
        return this;
    }

    public Subject unset(Object ... elements) {
        return unsetEntire(List.of(elements));
    }
    public Subject unsetEntire(Iterable<?> iterable) {
        for(Object it : iterable) {
            unset(it);
        }
        return this;
    }

    public Subject take(Object... elements) {
        return takeEntire(List.of(elements)).set();
    }
    public Series takeEntire(Iterable<?> iterable) {
        throw new UnsupportedOperationException("Solid method");
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
