package suite.suite;

class Link {
    Link front;
    Link back;
    Subject subject;

    Link() {
        this.subject = ZeroSubject.getInstance();
        front = this;
        back = this;
    }

    Link(Link front, Link back, Subject subject) {
        this.subject = subject;
        this.front = front;
        this.back = back;
    }

    Link front() {
        return subject != null ? front : back.front();
    }

    Link back() {
        return subject != null ? back : front.back();
    }
}
