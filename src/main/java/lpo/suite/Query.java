package lpo.suite;

import java.util.function.Function;

public class Query {

    private final Subject source;
    private Subject result;

    public Query(Subject source) {
        this.source = source;
        this.result = Suite.set();
    }

    public Query get(Object key) {
        result = source.get(key);
        return this;
    }

    public Query or(Object key) {
        if(result.desolated()) {
            result = source.get(key);
        }
        return this;
    }

    public<B> B orGiven(B substitute) {
        return result.orGiven(substitute);
    }

    public<B> B asExpected() {
        return result.asExpected();
    }

    public Query map(Subject map) {
        Subject r = Suite.set();
        for(var s : result.front()) {
            r.inputAll(map.get(s.direct()).front());
        }
        result = r;
        return this;
    }

    public<T> Query map(Class<T> mappedType, Function<T, Object> function) {
        for(var s : result.front()) {
            if(s.assigned(mappedType)) {
                result.set(s.key().direct(), function.apply(s.asExpected()));
            }
        }
        return this;
    }
}
