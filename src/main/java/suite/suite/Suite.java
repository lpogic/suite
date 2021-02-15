package suite.suite;

import suite.suite.action.Action;
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
    public static Subject put(Subject $) {
        return new SolidSubject(new MonoSubject(new Auto(), $));
    }
    public static Subject inset(Object ... elements) {
        return new SolidSubject().inset(elements);
    }
    public static Subject input(Object ... elements) {
        return new SolidSubject().input(elements);
    }
    public static Subject alter(Iterable<Subject> source) {
        return new SolidSubject().alter(source);
    }
    public static Subject setAll(Iterable<?> source) {
        return new SolidSubject().setAll(source);
    }

//    public static Subject fuse(Subject sub) {
//        if(sub == null) {
//            return Suite.set();
//        } else if(sub.fused()) {
//            return sub;
//        } else {
//            return new SolidSubject(new FuseSubject(sub));
//        }
//    }

    public static Subject thready() {
        return new ThreadySubject();
    }

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
            sb.append(describe($, true, o -> o instanceof Auto ? "" : Objects.toString(o)));
        }
        return sb.toString();
    }

    public static String describe(Subject $sub) {
        return describe($sub, true, o -> o instanceof Auto ? "" : Objects.toString(o));
    }

    public static String describe(Subject $sub, boolean pack, Function<Object, String> encoder) {
        if($sub == null) $sub = set();
        if(pack) {
            $sub = put($sub.absent() ? set(new Auto()) : $sub);
        }
        StringBuilder sb = new StringBuilder();
        java.util.Stack<Subject> stack = new java.util.Stack<>();
        Subject printed = set();
        stack.add(set($sub).set($sub.cascade()));
        int goTo = 0;
        boolean tabsBefore = false;
        while(!stack.empty()) {
            var $1 = stack.peek();
            Subject $current = $1.asExpected();
            Cascade<Subject> it = $1.last().asExpected();
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
                        sb.append("[ ").append($).append(" ]\n");
                        tabsBefore = true;
                    } else {
                        if($.size() > 1 || $.in().present()) {
                            sb.append("[\n");
                            tabsBefore = true;
                        } else {
                            sb.append("[ ");
                        }
                        stack.add(set($).set($.cascade()));
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
                if(tabsBefore) sb.append("\t".repeat(stack.size() - 1)).append("]\n");
                else sb.append(" ]\n");
                tabsBefore = true;
            }
        }
        return sb.toString();
    }

    public static String describe(Subject $sub, boolean pack, Function<Object, String> encoder, boolean compress) {
        if(!compress)return describe($sub, pack, encoder);
        if($sub == null) $sub = set();
        if(pack) $sub = put($sub.set());
        StringBuilder sb = new StringBuilder();
        java.util.Stack<Iterator<Subject>> stack = new java.util.Stack<>();
        Subject printed = set();
        stack.add($sub.iterator());
        int goTo = 0;
        while(!stack.empty()) {
            Iterator<Subject> it = stack.peek();
            for(var $s : (Iterable<Subject>) () -> it) {
                if(!$s.is(Auto.class)) {
                    sb.append(encoder.apply($s.direct()));
                }
                var $ = $s.in().get();
                if($.absent()) {
                    if(it.hasNext()) {
                        sb.append("[]");
                    }
                } else {
                    if(printed.in($).present()) {
                        sb.append("[").append($).append("]");
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

    public static Series postDfs(Subject $sub) {
        return postDfs($sub, Subject::front);
    }

    public static Series postDfs(Subject $sub, Function<Subject, Series> serializer) {
        return () -> new Browser() {
            final Subject $stack = inset($sub, serializer.apply($sub).iterator());
            final Subject $subjectStack = set();
            final Subject $hasNext = set();

            @Override
            public boolean hasNext() {
                if($hasNext.present()) return $hasNext.asExpected();
                dig();
                $hasNext.set($subjectStack.present());
                return $hasNext.asExpected();
            }

            @Override
            public Subject next() {
                if($hasNext.absent()) dig();
                Subject $next = $subjectStack.take($subjectStack.last().direct()).in().get();
                $hasNext.unset();
                return $next;
            }

            private void dig() {
                if ($stack.absent()) return;
                Subject $i = $stack.last().asExpected();
                Iterator<Subject> it = $stack.last().in().asExpected();
                while (it.hasNext()) {
                    var $ = it.next();
                    $subjectStack.put($);
                    $i = $.in().get();
                    if ($i.present() && $stack.absent($i)) {
                        it = serializer.apply($i).iterator();
                        $stack.in($i).set(it);
                    } else return;
                }
                $stack.unset($i);
            }
        };
    }

    public static Series preDfs(Subject $sub) {
        return preDfs($sub, Subject::front);
    }

    public static Series preDfs(Subject $sub, Function<Subject, Series> serializer) {
        return () -> new Browser() {
            final Subject $stack = set();
            boolean digDone = false;
            Subject $nextUp = $sub;

            @Override
            public boolean hasNext() {
                if(!digDone) dig($nextUp);
                return $stack.present();
            }

            @Override
            public Subject next() {
                Iterator<Subject> it = $stack.last().in().asExpected();
                var $next = it.next();
                $nextUp = $next.in().get();
                digDone = false;
                return $next;
            }

            private void dig(Subject $) {
                if($.present() && $stack.absent($)) $stack.inset($, serializer.apply($).iterator());
                while($stack.present()) {
                    Iterator<Subject> it = $stack.last().in().asExpected();
                    if(it.hasNext()) return;
                    $stack.unset($stack.last().direct());
                }
                digDone = true;
            }
        };
    }

    public static Series bfs(Subject $sub) {
        return bfs($sub, Subject::front);
    }

    public static Series bfs(Subject $sub, Function<Subject, Series> serializer) {
        return () -> new Browser() {
            final Subject $all = set($sub);
            Browser current = serializer.apply($sub).iterator();
            Series $nextFront = Series.empty();

            @Override
            public boolean hasNext() {
                if(current.hasNext()) return true;
                current = $nextFront.iterator();
                $nextFront = Series.empty();
                return current.hasNext();
            }

            @Override
            public Subject next() {
                var $ = current.next();
                var $i = $.in().get();
                if($i.present() && $all.absent($i)) {
                    $all.set($i);
                    $nextFront = $nextFront.join(serializer.apply($i));
                }
                return $;
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

    public static Subject refactor(Subject $sub, Action refactor) {
        for(var $ : preDfs(put($sub)).eachIn()) {
            var $t = set();
            for(var $1 : $) {
                $t.alter(refactor.apply($1));
            }
            $.unset().alter($t);
        }
        return $sub;
    }

}
