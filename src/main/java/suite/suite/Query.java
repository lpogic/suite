package suite.suite;

import suite.suite.action.Action;

import java.util.function.Function;
import java.util.function.Supplier;

public class Query {

    private final Subject $source;
    private Subject $result;

    public Query(Subject $source) {
        this.$source = $source;
        this.$result = Suite.set();
    }

    public Query get(Object key) {
        $result = $source.get(key);
        return this;
    }

    public Query get(Object key, Subject $map) {
        $result = $map.get(key);
        return this;
    }

    public Query get(Object key, Action action) {
        $result = action.play($source.get(key));
        return this;
    }

    public<T> Query get(Object key, Class<T> type) {
        var $s = $source.get(key);
        if($s.instanceOf(type)) {
            $result = $s;
        }
        return this;
    }

    public<T> Query get(Object key, Class<T> type, Function<T, Object> function) {
        var $s = $source.get(key);
        if($s.instanceOf(type)) {
            $result = Suite.set(function.apply($s.asExpected()));
        }
        return this;
    }

    public Query or(Object key) {
        return $result.isEmpty() ? get(key) : this;
    }

    public Query or(Object key, Subject $map) {
        return $result.isEmpty() ? get(key, $map) : this;
    }

    public Query or(Object key, Action action) {
        return $result.isEmpty() ? get(key, action) : this;
    }

    public<T> Query or(Object key, Class<T> type) {
        return $result.isEmpty() ? get(key, type) : this;
    }

    public<T> Query or(Object key, Class<T> type, Function<T, Object> function) {
        return $result.isEmpty() ? get(key, type, function) : this;
    }

    public<B> B orGiven(B substitute) {
        return $result.orGiven(substitute);
    }

    public<B> B orDo(Supplier<B> supplier) {
        return $result.orDo(supplier);
    }

    public<B> B orDo(Function<Subject, B> function) {
        return $result.isEmpty() ? function.apply($source) : $result.asExpected();
    }

    public<B> B asExpected() {
        return $result.asExpected();
    }

    public Object direct() {
        return $result.direct();
    }

    public Query map(Subject $map) {
        Subject $r = Suite.set();
        for(var $s : $result.front()) {
            $r.input($map.get($s.direct()).front());
        }
        $result = $r;
        return this;
    }

    public Query map(Action action) {
        Subject $r = Suite.set();
        for(var $s : $result.front()) {
            $r.inset(action.play($s).front());
        }
        $result = $r;
        return this;
    }

    public<T> Query map(Class<T> mappedType, Function<T, Object> function) {
        Subject $r = Suite.set();
        for(var $s : $result.front()) {
            if($s.instanceOf(mappedType)) {
                $r.set($s.key(), function.apply($s.asExpected()));
            }
        }
        $result = $r;
        return this;
    }
}
