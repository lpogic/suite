package suite.suite;

public class FirstSub implements Sub {

    final Sub sub;

    public FirstSub(Sub sub) {
        this.sub = sub;
    }

    public Subject get() {
        return sub.get().jump();
    }

    public Subject set() {
        sub.set().materialize();
        return get();
    }
}
