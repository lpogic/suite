package suite.sets;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.FluidIterator;

import java.util.Iterator;

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
    
    public static Subject insec(Subject major, Subject minor) {
        Subject sub = Suite.set();
        for(var o : minor.front().keys()) {
            Subject s1 = major.get(o);
            if(s1.settled()) {
                sub.set(s1.key().direct(), s1.direct());
            }
        }
        return sub;
    }
}
