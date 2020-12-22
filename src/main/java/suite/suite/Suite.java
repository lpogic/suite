package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Query;

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
        return new SolidSubject(new MonoSubject(element));
    }
    public static Subject set(Object element, Subject $) {
        return new SolidSubject(new MonoSubject(element, $));
    }
    public static Subject inset(Iterable<Subject> source) {
        return new SolidSubject().inset(source);
    }
    public static Subject setAll(Iterable<?> source) {
        return new SolidSubject().setAll(source);
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

//    public static Subject wonky() {
//        return new WonkySubject();
//    }

    public static Query from(Subject $) {
        return new Query($);
    }

    public static Subject zip(Iterable<Object> keys, Iterable<Object> values) {
        return inset(Fluid.engage(keys, values));
    }

    public static String describe(Fluid $sub) {
        StringBuilder sb = new StringBuilder();
        Stack<Iterator<Subject>> stack = new Stack<>();
        Subject printed = Suite.set();
        stack.add($sub.iterator());
        int goTo = 0;
        while(!stack.empty()) {
            Iterator<Subject> it = stack.peek();
            for(Subject $s : (Iterable<Subject>) () -> it) {
                sb.append("\t".repeat(stack.size() - 1)).append($s.direct());
                Subject $ = $s.get();
                if($.isEmpty()) {
                    if(it.hasNext())sb.append(" []\n");
                } else {
                    sb.append(" [\n");
                    stack.add($.iterator());
                    printed.set($);
                    goTo = 1;
                    break;
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

    public static String describe(Fluid $sub, boolean compressed) {
        if(!compressed)return describe($sub);
        StringBuilder sb = new StringBuilder();
        Stack<Iterator<Subject>> stack = new Stack<>();
        stack.add($sub.iterator());
        int goTo = 0;
        while(!stack.empty()) {
            for(Subject $s : (Iterable<Subject>) stack::peek) {
                if($s.instanceOf(Fluid.class)) {
                    Subject $ = $s.get();
                    sb.append($s.direct()).append("[");
                    stack.add($.iterator());
                    goTo = 1;
                    break;
                } else {
                    sb.append($s.direct()).append("[").append($s.direct()).append("]");
                }
            }
            if(goTo == 1) {
                goTo = 0;
                continue;
            }
            stack.pop();
            if(stack.size() > 0)sb.append("]");
        }
        return sb.toString();
    }

}
