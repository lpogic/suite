package suite.suite;

import suite.suite.util.Wave;
import suite.suite.util.Fluid;

import java.util.HashMap;
import java.util.Map;

class Chain implements Fluid {

    private final Map<Object, Link> data = new HashMap<>();
    final Link ward;

    public Chain() {
        this.ward = new Link(null, null, ZeroSubject.getInstance());
        this.ward.front = this.ward.back = ward;
    }

    public Chain(Link ward) {
        this.ward = ward;
        Link link = ward.front;
        while (link != ward) {
            data.put(link.subject.direct(), link);
            link = link.front;
        }
    }

    Link get(Object key) {
        return data.getOrDefault(key, ward);
    }

    Link getFirst() {
        return ward.back;
    }

    Link getLast() {
        return ward.front;
    }

    public int size() {
        return data.size();
    }

    Link put(Object o) {
        Link link = data.get(o);
        if(link != null) {
            link.subject = new BubbleSubject(o);
        } else {
            link = new Link(ward.front, ward, new BubbleSubject(o));
            ward.front = ward.front.back = link;
        }
        return link;
    }

    Link put(Object o, Subject $) {
        Link link = data.get(o);
        if(link != null) {
            link.subject = new BubbleSubject(o, $);
        } else {
            link = new Link(ward.front, ward, new BubbleSubject(o, $));
            ward.front = ward.front.back = link;
        }
        return link;
    }

    Link putIfAbsent(Object o) {
        Link link = data.get(o);
        if(link != null) {
            return link;
        } else {
            link = new Link(ward.front, ward, new BubbleSubject(o));
            ward.front = ward.front.back = link;
        }
        return link;
    }

    Object put(Link before, Object key, Object value) {

        Link linkJunior = new Link(null, null, key, value);
        Link linkSenior = data.putIfAbsent(key, linkJunior);
        Object seniorValue = null;

        if(linkSenior == null) {

            before.front.back = linkJunior;
            linkJunior.front = before.front;
            linkJunior.back = before;
            before.front = linkJunior;

        } else {

            seniorValue = linkSenior.subject.direct();
            linkSenior.setValue(value);
            if(linkSenior != before && linkSenior != before.front) {

                linkSenior.front.back = linkSenior.back;
                linkSenior.back.front = linkSenior.front;
                before.front.back = linkSenior;
                linkSenior.front = before.front;
                linkSenior.back = before;
                before.front = linkSenior;
            }
        }

        return seniorValue;
    }

    void putIfAbsent(Link before, Object key, Object value) {

        Link linkJunior = new Link(null, null, key, value);
        Link linkSenior = data.putIfAbsent(key, linkJunior);

        if(linkSenior == null) {

            before.front.back = linkJunior;
            linkJunior.front = before.front;
            linkJunior.back = before;
            before.front = linkJunior;

        }

    }

    public Object putLast(Object key, Object value) {
        return put(ward, key, value);
    }

    public void putLastIfAbsent(Object key, Object value) {
        putIfAbsent(ward, key, value);
    }

    public Object putFirst(Object key, Object value) {
        return put(ward.back, key, value);
    }

    public void putFirstIfAbsent(Object key, Object value) {
        putIfAbsent(ward.back, key, value);
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
    public Wave<Subject> iterator() {
        return new LinkIterator(ward);
    }

    public Wave<Subject> iterator(boolean reverse) {
        return new LinkIterator(reverse, ward, ward);
    }

    public Wave<Subject> iterator(boolean reverse, Link link) {
        return new LinkIterator(reverse, link, ward);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        forEach(s -> stringBuilder.append(s).append(" "));
        return stringBuilder.toString();
    }
}
