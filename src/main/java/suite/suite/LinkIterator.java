package suite.suite;

import suite.suite.util.Wave;

public class LinkIterator implements Wave<Subject> {

    private final boolean reverse;
    private Link current;
    private final Link ward;

    public LinkIterator(Link ward) {
        this(false, ward, ward);
    }

    public LinkIterator(boolean reverse, Link link, Link ward) {
        this.reverse = reverse;
        this.current = link;
        this.ward = ward;
    }

    public boolean hasNext() {
        return (reverse ? current.front() : current.back()) != ward;
    }

    public Subject next() {
        current = reverse ? current.front() : current.back();
        return current.subject;
    }
}
