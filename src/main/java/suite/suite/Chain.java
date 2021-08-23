package suite.suite;

import suite.suite.util.Browser;
import suite.suite.util.Series;

import java.util.HashMap;
import java.util.Map;

class Chain implements Series {

    private final Map<Object, Link> data = new HashMap<>(3);
    final Link ward;

    public Chain() {
        this.ward = new Link(null, null, ZeroSubject.getInstance());
        this.ward.front = this.ward.back = ward;
    }

    public Chain(Link ward) {
        this.ward = ward;
        Link link = ward.front;
        while (link != ward) {
            data.put(link.subject.raw(), link);
            link = link.front;
        }
    }

    public Link get(Object key) {
        return data.getOrDefault(key, ward);
    }

    public Link getFirst() {
        return ward.back;
    }

    public Link getLast() {
        return ward.front;
    }

    public int size() {
        return data.size();
    }

    public void put(Object o) {
        Link link = data.get(o);
        if(link != null) {
            link.subject = new BasicSubject(o);
        } else {
            link = new Link(ward.front, ward, new BasicSubject(o));
            ward.front = ward.front.back = link;
            data.put(o, link);
        }
    }

    public void put(Object o, Subject $) {
        Link link = data.get(o);
        if(link != null) {
            link.subject = new MonoSubject(o, $);
        } else {
            link = new Link(ward.front, ward, new MonoSubject(o, $));
            ward.front = ward.front.back = link;
            data.put(o, link);
        }
    }

    public void put(Link sequent, Object o) {
        Link link = data.get(o);
        if(link != null) {
            link.subject = new BasicSubject(o);
            if(link != sequent) moveBefore(link, sequent);
        } else {
            link = new Link(sequent.front, sequent, new BasicSubject(o));
            sequent.front = sequent.front.back = link;
            data.put(o, link);
        }
    }

    public void put(Link sequent, Object o, Subject $) {
        Link link = data.get(o);
        if(link != null) {
            link.subject = new MonoSubject(o, $);
            if(link != sequent) moveBefore(link, sequent);
        } else {
            link = new Link(sequent.front, sequent, new MonoSubject(o, $));
            sequent.front = sequent.front.back = link;
            data.put(o, link);
        }
    }

    public void swap(Object o1, Object o2) {
        Link link1 = data.remove(o1);
        Link link2 = data.remove(o2);
        if(link1 != null && link2 != null) {
            var s = link1.subject.swap(o1, o2);
            link1.subject = link2.subject.swap(o2, o1);
            link2.subject = s;
            data.put(o1, link2);
            data.put(o2, link1);
        } else if(link2 != null) {
            link2.subject = link2.subject.swap(o2, o1);
            data.put(o1, link2);
        } else if(link1 != null) {
            link1.subject = link1.subject.swap(o1, o2);
            data.put(o2, link1);
        }
    }

    public void moveBefore(Link link, Link sequent) {
        link.front.back = link.back;
        link.back.front = link.front;

        link.back = sequent;
        link.front = sequent.front;

        sequent.front = sequent.front.back = link;
    }

    public void remove(Object key) {

        Link linkSenior = data.remove(key);

        if(linkSenior != null) {

            linkSenior.front.back = linkSenior.back;
            linkSenior.back.front = linkSenior.front;
            linkSenior.subject = null;
        }

    }

    public void clear() {
        for(Link link : data.values()) {
            link.subject = null;
        }
        data.clear();
        ward.front = ward.back = ward;
    }

    @Override
    public Browser iterator() {
        return new LinkIterator(ward);
    }

    public Browser iterator(boolean reverse) {
        return new LinkIterator(reverse, ward, ward);
    }

    public Browser iterator(boolean reverse, Link link) {
        return new LinkIterator(reverse, link, ward);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        forEach(s -> stringBuilder.append(s).append(" "));
        return stringBuilder.toString();
    }
}
