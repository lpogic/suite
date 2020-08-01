package suite.sets;

import suite.suite.Subject;

public class Sets {

    public static boolean contains(Subject s1, Subject s2) {
        for(var it : s2.front().keys()) {
            if(s1.get(it).desolated()) return false;
        }
        return true;
    }
}
