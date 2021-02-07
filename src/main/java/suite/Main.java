package suite;

import suite.suite.Subject;
import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var $ = Suite.set(1);
        int i = 0;
//        for(var $$ : $) {
//            $$.print();
//            if($$.as(Integer.class) == 1) {
//                $.unset().set(2).set(3);
//            }
//        }
//        $.print();
        for(var $1 : Suite.preDfs(Suite.add($)).eachIn().select(Subject::present)) {
            $1.print();
            if($1.as(Integer.class) == 1) {
                $1.unset().set(2).set(3, Suite.set(++i));
            }
        }
        System.out.println("@@@");
        $.print();

    }
}
