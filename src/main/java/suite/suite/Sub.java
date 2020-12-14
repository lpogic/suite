package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Slime;
import suite.suite.util.Glass;
import suite.suite.util.Wave;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class Sub<T> implements Fluid {

    class HomogenizedSubjectIterator implements Wave<Vendor> {
        Vendor sub;
        boolean reverse;
        Wave<Vendor> it;


        HomogenizedSubjectIterator(Vendor sub, boolean reverse, Slot slot) {
            this.sub = sub;
            this.reverse = reverse;
            this.it = sub.iterator(slot, reverse);
        }

        @Override
        public boolean hasNext() {
            if(sub != subject) {
                if(sub == ZeroSubject.getInstance()) {
                    it = reverse ? subject.iterator(Slot.RECENT, true) : subject.iterator(Slot.PRIME, false);
                } else if(subject == ZeroSubject.getInstance()) {
                    it = Wave.empty();
                }
                sub = subject;
            }
            return it.hasNext();
        }

        @Override
        public Vendor next() {
            return new SolidSubject(it.next().exclude());
        }
    }

    private Subject subject;

    public Sub() {
        subject = ZeroSubject.getInstance();
    }

    Sub(Vendor vendor) {
        this.subject = vendor instanceof Subject ? (Subject)vendor : vendor.set();
    }

    public Sub<T> set(T element) {
        subject = subject.set(element);
        return this;
    }

    public Sub<T> set(Object key, T value) {
        subject = subject.set(key, value);
        return this;
    }

    public Sub<T> put(T element) {
        subject = subject.put(element);
        return this;
    }

    public Sub<T> put(Object key, T value) {
        subject = subject.put(key, value);
        return this;
    }

    public Sub<T> add(T element) {
        subject = subject.add(element);
        return this;
    }

    public Sub<T> unset() {
        subject = subject.unset();
        return this;
    }

    public Sub<T> unset(Object key) {
        subject = subject.unset(key);
        return this;
    }

    public Sub<T> unset(Object key, Object value) {
        subject = subject.unset(key, value);
        return this;
    }

    public Sub<T> unsetAt(Slot slot) {
        subject = subject.unsetAt(slot);
        return this;
    }

    public Sub<T> prime() {
        return new Sub<>(subject.prime().exclude());
    }

    public Sub<T> recent() {
        return new Sub<>(subject.recent().exclude());
    }

    public Sub<T> get(Object key) {
        return new Sub<>(subject.get(key).exclude());
    }

    public Sub<T> get(Object ... keys) {
        return new Sub<>(subject.get(keys).exclude());
    }

    public Sub<T> getAt(Slot slot) {
        return new Sub<>(subject.getAt(slot).exclude());
    }

    public Sub<T> getAt(int slotIndex) {
        return new Sub<>(subject.getAt(slotIndex).exclude());
    }

    public Subject key() {
        return new SolidSubject(subject.key().exclude());
    }

    public T direct() {
        return subject.orGiven(null);
    }

    public <B extends T> B asExpected() {
        return subject.asExpected();
    }

    public <B extends T> B asGiven(Class<B> requestedType) {
        return subject.asGiven(requestedType);
    }

    public <B extends T> B asGiven(Glass<? super B, B> requestedType) {
        return subject.asGiven(requestedType);
    }

    public <B extends T> B asGiven(Class<B> requestedType, B substitute) {
        return subject.asGiven(requestedType, substitute);
    }

    public <B extends T> B asGiven(Glass<? super B, B> requestedType, B substitute) {
        return subject.asGiven(requestedType, substitute);
    }

    public <B extends T> B orGiven(B substitute) {
        return subject.orGiven(substitute);
    }

    public <B extends T> B orDo(Supplier<B> supplier) {
        return subject.orDo(supplier);
    }

    public <B extends T> boolean assigned(Class<B> type) {
        return subject.instanceOf(type);
    }

    public Sub<T> getSaved(Object key, T reserve) {
        Vendor saved = subject.get(key);
        if(saved.notEmpty())return new Sub<>(saved);
        subject = subject.set(key, reserve);
        return get(key);
    }

    public Sub<T> getDone(Object key, Supplier<T> supplier) {
        Vendor done = subject.get(key);
        if(done.notEmpty())return new Sub<>(done);
        subject = subject.set(key, supplier.get());
        return get(key);
    }

    public Sub<T> getDone(Object key, Function<Subject, T> function, Subject argument) {
        Vendor done = subject.get(key);
        if(done.notEmpty())return new Sub<>(done);
        subject = subject.set(key, function.apply(argument));
        return get(key);
    }

    public Sub<T> take(Object key) {
        Sub<T> taken = get(key);
        if(taken.settled()) subject = subject.unset(key);
        return taken;
    }

    public Sub<T> takeAt(Slot slot) {
        Sub<T> taken = getAt(slot);
        if(taken.settled()) subject = subject.unset(taken.key().direct());
        return taken;
    }

    public boolean settled() {
        return subject.notEmpty();
    }

    public boolean desolated() {
        return subject.isEmpty();
    }

    public int size() {
        return subject.size();
    }

    public Fluid front() {
        return () -> new HomogenizedSubjectIterator(subject, false, Slot.PRIME);
    }

    public Fluid front(Slot slot) {
        return () -> new HomogenizedSubjectIterator(subject, false, slot);
    }

    public Fluid reverse() {
        return () -> new HomogenizedSubjectIterator(subject, true, Slot.RECENT);
    }

    public Fluid reverse(Slot slot) {
        return () -> new HomogenizedSubjectIterator(subject, true, slot);
    }

    public Wave<Vendor> iterator(Slot slot, boolean reverse) {
        return new HomogenizedSubjectIterator(subject, reverse, slot);
    }

    public Wave<Vendor> iterator() {
        return iterator(Slot.PRIME, false);
    }

    public boolean fused() {
        return subject.fused();
    }

    @Override
    public String toString() {
        return subject.toString();
    }

    public Sub<T> setAt(Slot slot, T element) {
        subject = subject.setAt(slot, element);
        return this;
    }

    public Sub<T> setAt(Slot slot, Object key, T value) {
        subject = subject.setAt(slot, key, value);
        return this;
    }

    public Sub<T> putAt(Slot slot, T element) {
        subject = subject.putAt(slot, element);
        return this;
    }

    public Sub<T> putAt(Slot slot, Object key, T value) {
        subject = subject.putAt(slot, key, value);
        return this;
    }

    public Sub<T> addAt(Slot slot, T element) {
        subject = subject.addAt(slot, element);
        return this;
    }

    public String asString() {
        return Objects.toString(direct(), "nuLL");
    }

    public Sub<T> setAll(Iterable<T> iterable) {
        subject = subject.setAll(iterable);
        return this;
    }

    public Sub<T> putAll(Iterable<T> iterable) {
        subject = subject.putAll(iterable);
        return this;
    }

    public Sub<T> addAll(Iterable<T> iterable) {
        subject = subject.addAll(iterable);
        return this;
    }

    public Slime<T> keys() {
        return () -> new Wave<>() {
            final Iterator<Vendor> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public T next() {
                return subIt.next().key().asExpected();
            }
        };
    }

    public Slime<T> values() {
        return () -> new Wave<>() {
            final Iterator<Vendor> subIt = iterator();

            @Override
            public boolean hasNext() {
                return subIt.hasNext();
            }

            @Override
            public T next() {
                return subIt.next().asExpected();
            }
        };
    }
}
