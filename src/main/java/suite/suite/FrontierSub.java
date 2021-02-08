package suite.suite;


public class FrontierSub implements Sub {

    final Sub sub;
    final Object frontier;

    public FrontierSub(Sub s, Object frontier) {
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
