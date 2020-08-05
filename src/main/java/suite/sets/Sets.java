package suite.sets;

import suite.suite.Subject;
import suite.suite.Suite;

public class Sets {

    public static boolean contains(Subject major, Subject minor) {
        for(var it : minor.front().keys()) {
            if(major.get(it).desolated()) return false;
        }
        return true;
    }

    public static Subject union(Subject major, Subject minor) {
        return Suite.insetAll(major.front()).inputAll(minor.front());
    }
}
