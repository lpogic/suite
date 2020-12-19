package suite.sets;

import suite.suite.Subject;
import suite.suite.Suite;
import suite.suite.util.Fluid;

public class Sets {

    public static boolean contains(Subject major, Fluid minor) {
        for(var it : minor.keys()) {
            if(major.get(it).isEmpty()) return false;
        }
        return true;
    }

    public static Subject union(Fluid major, Fluid minor) {
        return Suite.inset(major).input(minor);
    }
    
    public static Subject insec(Subject $strainer, Fluid fluid) {
        Subject $sub = Suite.set();
        for(var o : fluid.keys()) {
            $sub.inset($strainer.get(o));
        }
        return $sub;
    }
}
