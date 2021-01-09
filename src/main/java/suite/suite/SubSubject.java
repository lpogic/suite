package suite.suite;

public class SubSubject implements Sub {

    final Subject subject;

    SubSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject get() {
        return subject;
    }

    public Subject set() {
        return subject;
    }
}
