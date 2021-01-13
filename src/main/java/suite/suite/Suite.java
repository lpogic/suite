package suite.suite;

import suite.suite.util.*;

import java.util.*;
import java.util.function.Function;

public class Suite {

    public static class Auto {
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
        return new SolidSubject(new MonoSubject(new Auto(), $));
    }
    public static Subject insert(Object ... elements) {
        return new SolidSubject().insert(elements);
    }
    public static Subject alter(Iterable<Subject> source) {
        return new SolidSubject().alter(source);
    }
    public static Subject setAll(Iterable<?> source) {
        return new SolidSubject().setAll(source);
    }

//    public static boolean absent1(Subject $, Object element)

//    public static Subject fuse(Subject sub) {
//        if(sub == null) {
//            return Suite.set();
//        } else if(sub.fused()) {
//            return sub;
//        } else {
//            return new SolidSubject(new FuseSubject(sub));
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
        return alter(Series.engage(keys, values));
    }

    public static String describe(Series $ser) {
        StringBuilder sb = new StringBuilder();
        for(var $ : $ser) {
            sb.append(describe($, true, o -> o instanceof Auto ? "" : o + " "));
        }
        return sb.toString();
    }

    public static String describe(Subject $sub) {
        return describe($sub, true, o -> o instanceof Auto ? "" : o + " ");
    }

    public static String describe(Subject $sub, boolean pack, Function<Object, String> encoder) {
        if($sub == null) $sub = Suite.set();
        if(pack) {
            $sub = Suite.add($sub.absent() ? Suite.set(new Auto()) : $sub);
        }
        StringBuilder sb = new StringBuilder();
        Stack<Subject> stack = new Stack<>();
        Subject printed = Suite.set();
        stack.add(Suite.set($sub).set($sub.cascade()));
        int goTo = 0;
        boolean tabsBefore = false;
        while(!stack.empty()) {
            var $1 = stack.peek();
            Subject $current = $1.asExpected();
            Cascade<Subject> it = $1.getLast().asExpected();
            for(Subject $s : it.toEnd()) {
                if(tabsBefore)sb.append("\t".repeat(stack.size() - 1));
                tabsBefore = false;
                sb.append(encoder.apply($s.direct()));
                Subject $ = $s.in().get();
                if($.absent()) {
                    if($current.size() > 1) {
                        sb.append("[]\n");
                        tabsBefore = true;
                    }
                } else {
                    if(printed.get($).present()) {
                        sb.append("[ ... ]\n");
                        tabsBefore = true;
                    } else {
                        if($.size() > 1 || $.in().present()) {
                            sb.append("[\n");
                            tabsBefore = true;
                        } else {
                            sb.append("[ ");
                        }
                        stack.add(Suite.set($).set($.cascade()));
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

    public static String describe(Subject $sub, boolean pack, Function<Object, String> encoder, boolean compress) {
        if(!compress)return describe($sub, pack, encoder);
        if($sub == null) $sub = Suite.set();
        if(pack) $sub = Suite.add($sub.set());
        StringBuilder sb = new StringBuilder();
        Stack<Iterator<Subject>> stack = new Stack<>();
        Subject printed = Suite.set();
        stack.add($sub.iterator());
        int goTo = 0;
        while(!stack.empty()) {
            Iterator<Subject> it = stack.peek();
            for(var $s : (Iterable<Subject>) () -> it) {
                if(!$s.is(Auto.class)) {
                    sb.append($s.direct());
                }
                var $ = $s.in().get();
                if($.absent()) {
                    if(it.hasNext()) {
                        sb.append("[]");
                    }
                } else {
                    if(printed.in($).present()) {
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

    public static Series dfs(Subject $sub) {
        return () -> new Browser<>() {
            final Stack<Iterator<Subject>> stack = new Stack<>();
            final Stack<Subject> subjectStack = new Stack<>();
            {
                stack.add(Suite.set(null, $sub).eachIn().iterator());
                dig();
            }

            @Override
            public boolean hasNext() {
                return subjectStack.size() > 0;
            }

            @Override
            public Subject next() {
                var $ = subjectStack.pop();
                stack.pop();
                dig();
                return $;
            }

            private void dig() {
                while (stack.size() > 0 && stack.peek().hasNext()) {
                    while (stack.peek().hasNext()) {
                        subjectStack.add(stack.peek().next());
                        stack.add(subjectStack.peek().eachIn().iterator());
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
