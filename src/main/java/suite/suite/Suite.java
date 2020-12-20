package suite.suite;

import suite.suite.util.Fluid;

import java.util.Iterator;
import java.util.Stack;

public class Suite {

    public static class AutoKey {
        @Override
        public String toString() {
            return "$A";
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

    public static String toString(Subject $sub) {
        StringBuilder sb = new StringBuilder();
        Stack<Iterator<Subject>> stack = new Stack<>();
        stack.add($sub.iterator());
        int goTo = 0;
        while(!stack.empty()) {
            for(Subject $s : (Iterable<Subject>) stack::peek) {
                if($s.instanceOf(Subject.class)) {
                    Subject $ = $s.at();
                    sb.append("\t".repeat(stack.size() - 1)).append($s.key()).append(" [\n");
                    stack.add($.iterator());
                    goTo = 1;
                    break;
                } else {
                    sb.append("\t".repeat(stack.size() - 1)).append($s.key()).append(" [ ").append($s.direct()).append(" ]\n");
                }
            }
            if(goTo == 1) {
                goTo = 0;
                continue;
            }
            stack.pop();
            if(stack.size() > 0)sb.append("\t".repeat(stack.size() - 1)).append("]\n");
        }
        return sb.toString();
    }

}
