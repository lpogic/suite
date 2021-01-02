package suite.suite;

import suite.suite.util.Fluid;
import suite.suite.util.Glass;
import suite.suite.util.Query;
import suite.suite.util.Wave;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        return new SolidSubject(new BasicSubject(element));
    }
    public static Subject set(Object element, Subject $) {
        return new SolidSubject(new MonoSubject(element, $));
    }
    public static Subject add(Subject $) {
        return new SolidSubject(new MonoSubject(new AutoKey(), $));
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
        Stack<Subject> stack = new Stack<>();
        Subject printed = Suite.set();
        stack.add(Suite.set($sub).set($sub.iterator()));
        int goTo = 0;
        boolean tabsBefore = false;
        while(!stack.empty()) {
            var $1 = stack.peek();
            Subject $current = $1.asExpected();
            Iterator<Subject> it = $1.atLast().asExpected();
            for(Subject $s : (Iterable<Subject>) () -> it) {
                if(tabsBefore)sb.append("\t".repeat(stack.size() - 1));
                tabsBefore = false;
                sb.append($s.direct()).append(" ");
                Subject $ = $s.get();
                if($.isEmpty()) {
                    if($current.size() > 1) {
                        sb.append("[]\n");
                        tabsBefore = true;
                    }
                } else {
                    if(printed.at($).notEmpty()) {
                        sb.append("[ ... ]\n");
                        tabsBefore = true;
                    } else {
                        if($.size() > 1) {
                            sb.append("[\n");
                            tabsBefore = true;
                        } else {
                            sb.append("[ ");
                        }
                        stack.add(Suite.set($).set($.iterator()));
                        printed.set($);
                        goTo = 1;
                        break;
                    }
                }
            }
            if(goTo == 1) {
                goTo = 0;
                continue;
            }
            stack.pop();
            if(stack.size() > 0) {
                if(tabsBefore) sb.append("\t".repeat(stack.size() - 1));
                sb.append("]\n");
                tabsBefore = true;
            }
        }
        return sb.toString();
    }

    public static String describe(Fluid $sub, boolean compressed) {
        if(!compressed)return describe($sub);
        StringBuilder sb = new StringBuilder();
        Stack<Iterator<Subject>> stack = new Stack<>();
        Subject printed = Suite.set();
        stack.add($sub.iterator());
        int goTo = 0;
        while(!stack.empty()) {
            Iterator<Subject> it = stack.peek();
            for(Subject $s : (Iterable<Subject>) () -> it) {
                sb.append($s.direct());
                Subject $ = $s.get();
                if($.isEmpty()) {
                    if(it.hasNext()) {
                        sb.append("[]");
                    }
                } else {
                    if(printed.at($).notEmpty()) {
                        sb.append("[...]");
                    } else {
                        sb.append("[");
                        stack.add($.iterator());
                        printed.set($);
                        goTo = 1;
                        break;
                    }
                }
            }
            if(goTo == 1) {
                goTo = 0;
                continue;
            }
            stack.pop();
            if(stack.size() > 0) {
                sb.append("]");
            }
        }
        return sb.toString();
    }

    public static Fluid dfs(Subject $sub) {
        return () -> new Wave<>() {
            final Stack<Iterator<Subject>> stack = new Stack<>();
            final Stack<Subject> subjectStack = new Stack<>();
            {
                stack.add(Suite.set(null, $sub).eachGet().iterator());
                dig();
            }

            @Override
            public boolean hasNext() {
                return subjectStack.size() > 0;
            }

            @Override
            public Subject next() {
                Subject $ = subjectStack.pop();
                stack.pop();
                dig();
                return $;
            }

            private void dig() {
                while (stack.size() > 0 && stack.peek().hasNext()) {
                    while (stack.peek().hasNext()) {
                        subjectStack.add(stack.peek().next());
                        stack.add(subjectStack.peek().eachGet().iterator());
                    }
                    subjectStack.pop();
                    stack.pop();
                }
            }
        };
    }

    public static <B> List<B> asListOf(Subject $, Class<B> elementType) {
        return asListOf($, Glass.of(elementType));
    }
    public static <B> List<B> asListOf(Subject $, Glass<B, B> elementType) {
        return $.as(Glass.List(elementType));
    }
    public static <B> List<B> asListOf(Subject $, Class<B> elementType, List<B> reserve) {
        return asListOf($, Glass.of(elementType), reserve);
    }
    public static <B> List<B> asListOf(Subject $, Glass<B, B> elementType, List<B> reserve) {
        return $.as(Glass.List(elementType), reserve);
    }

    public static <A, B> Map<A, B> asMapOf(Subject $, Class<A> keyType, Class<B> valueType) {
        return asMapOf($, Glass.of(keyType), Glass.of(valueType));
    }
    public static <A, B> Map<A, B> asMapOf(Subject $, Glass<A, A> keyType, Glass<B, B> valueType) {
        return $.as(Glass.Map(keyType, valueType));
    }
    public static <A, B> Map<A, B> asMapOf(Subject $, Class<A> keyType, Class<B> valueType, Map<A, B> reserve) {
        return asMapOf($, Glass.of(keyType), Glass.of(valueType), reserve);
    }
    public static <A, B> Map<A, B> asMapOf(Subject $, Glass<A, A> keyType, Glass<B, B> valueType, Map<A, B> reserve) {
        return $.as(Glass.Map(keyType, valueType), reserve);
    }

}
