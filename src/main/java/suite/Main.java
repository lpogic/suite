package suite;

import suite.suite.Suite;

public class Main {

    public static void main(String[] args) {
        var $1 = Suite.insert(4, 5).insert(6, 7);
        var $ = Suite.set(0, Suite.set(1, Suite.set(2, $1)).set(3, $1));
        $.print();
        System.out.println("PRE DFS");
        Suite.preDfs($).print();
        System.out.println("POST DFS");
        Suite.postDfs($).print();
        System.out.println("BFS");
        Suite.bfs($).print();
    }
}
