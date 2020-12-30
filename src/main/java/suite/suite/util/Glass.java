package suite.suite.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class Glass<C, G extends C> implements Serializable {

    public abstract G cast(Object o);
    public abstract Glass<?,?>[] getGenerics();

    private final Class<C> c;

    public Glass(Class<C> c) {
        this.c = c;
    }

    public boolean isInstance(Object o) {
        return c.isInstance(o);
    }

    public Class<C> getMainClass() {
        return c;
    }

    public int hashCode() {
        return c.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Glass && ((Glass<?, ?>) obj).getMainClass().equals(getMainClass())
                && Arrays.equals(((Glass<?, ?>) obj).getGenerics(), getGenerics());
    }

    @Override
    public String toString() {
        return getMainClass() + "<" + Arrays.toString(getGenerics()) + ">";
    }

    public static<B> Glass<B, B> of(Class<B> brand) {
        return new Glass<>(brand) {

            @Override
            public B cast(Object o) {
                return getMainClass().cast(o);
            }

            @Override
            public Glass<?, ?>[] getGenerics() {
                return new Glass[0];
            }
        };
    }

    public static<A> Glass<List, List<A>> List(Glass<? super A, A> a) {
        return new Glass<>(List.class) {

            @Override
            public List<A> cast(Object o) {
                return (List<A>)o;
            }

            @Override
            public Glass<?, ?>[] getGenerics() {
                return new Glass[]{a};
            }
        };
    }

    public static<A, B> Glass<Map, Map<A, B>> Map(Glass<? super A, A> a, Glass<? super B, B> b) {
        return new Glass<>(Map.class) {

            @Override
            public Map<A, B> cast(Object o) {
                return (Map<A, B>)o;
            }

            @Override
            public Glass<?, ?>[] getGenerics() {
                return new Glass[]{a, b};
            }
        };
    }
}
