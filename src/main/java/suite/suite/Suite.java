package suite.suite;

import suite.suite.util.Fluid;

public class Suite {

    public static class AutoKey {
        @Override
        public String toString() {
            return "€";
        }
    }

    public static Subject set() {
        return new SolidSubject();
    }
    public static Subject set(Object element) {
        return new SolidSubject(new BubbleSubject(element));
    }
    public static Subject set(Object key, Object value) {
        return new SolidSubject(new CoupleSubject(key, value));
    }
    public static Subject add(Object element) {
        return new SolidSubject(new CoupleSubject(new AutoKey(), element));
    }
    public static Subject inset(Iterable<Subject> source) {
        return new SolidSubject().inset(source);
    }
    public static Subject input(Iterable<Subject> source) {
        return new SolidSubject().input(source);
    }
    public static Subject setAll(Iterable<Object> source) {
        return new SolidSubject().setAll(source);
    }
    public static Subject addAll(Iterable<Object> source) {
        return new SolidSubject().addAll(source);
    }

//    public static Subject fuse(Subject subject) {
//        if(subject == null) {
//            return Suite.set();
//        } else if(subject.fused()) {
//            return subject;
//        } else {
//            return new SolidSubject(new FuseSubject(subject));
//        }
//    }

//    public static Subject thready() {
//        return new ThreadySubject();
//    }

    public static Subject wonky() {
        return new WonkySubject();
    }

    public static Query from(Subject sub) {
        return new Query(sub);
    }

    public static Subject zip(Iterable<Object> keys, Iterable<Object> values) {
        return inset(Fluid.engage(keys, values));
    }

}
