package suite.sets;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.Fluid;

public class Sets {

    public static boolean contains(Subject major, Fluid minor) {
        for(var it : minor.keys()) {
            if(major.get(it).desolated()) return false;
        }
        return true;
    }

    public static Subject union(Fluid major, Fluid minor) {
        return Suite.insetAll(major).inputAll(minor);
    }
    
    public static Subject insec(Subject major, Fluid minor) {
        Subject sub = Suite.set();
        for(var o : minor.keys()) {
            Subject s = major.get(o);
            if(s.settled()) {
                sub.set(s.key().direct(), s.direct());
            }
        }
        return sub;
    }
}
