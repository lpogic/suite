package suite.suite;


public class ParamSub implements Sub {

    final Sub sub;
    final Object frontier;

    public ParamSub(Sub s, Object frontier) {
        sub = s;
        this.frontier = frontier;
    }

    @Override
    public Subject get() {
        return sub.get().jump(frontier);
    }

    public Subject set() {
        sub.set().materialize(frontier);
        return get();
    }
}
