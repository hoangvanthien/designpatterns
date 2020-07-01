package visitor;

/**
 * You are organizing a champion league.
 * Every match has two players. A Match can either be a Single Match,
 * or a Tournament. In the latter case, the two players are the winners of
 * two other Matches.
 *
 * You want to print the Matches along with their depths from the root Tournament
 * Instead of writing a function for that, you write a class! (PrintDepth)
 *
 * Each Match has a method `accept(Visitor v)`
 * Each Visitor has two methods `visit(SingleMatch)` and `visit(Tournament)`
 *
 * Whenever you want to do something with the Match, you call its `accept` function,
 * along with the operation you want to perform. The operation is an instance of
 * the class Visitor.
 *
 * The `accept` then simply calls `v.visit(this)` on the Visitor v that was given.
 *
 * Only after two dispatches, the intended operation will take place:
 * - 1: match.accept(v)
 * - 2: v.visit(this) // this == match
 *
 * Thus this pattern is also called Double Dispatch.
 *
 * You may not skip step 1 and simply call v.visit(match) because `match` may be of
 * an abstract type Match, and we do not have any `visit` method that takes a Match
 * parameter. ==> Not compilable
 *
 * Thus, you'd always let the match itself accept the visitor, then the concrete type
 * of the match will be resolved at run-time. Because we have `visit` FOR each concrete class,
 * and we define `accept` IN each concrete class, the compiler will not complain
 *
 */

public class Main {
    public static void main(String[] args) {
        Player[] players = new Player[8];
        for (int i = 0; i < 8; ++i) players[i] = new Player(String.valueOf(i));

        SingleMatch m1 = new SingleMatch(players[0], players[1], true);
        SingleMatch m2 = new SingleMatch(players[2], players[3], true);
        SingleMatch m3 = new SingleMatch(players[4], players[5], true);
        SingleMatch m4 = new SingleMatch(players[6], players[7], true);

        Tournament t1 = new Tournament(m1, m2, true);
        Tournament t2 = new Tournament(m3, m4, true);
        Tournament t = new Tournament(t1, t2, true);

        /**
         *              0
         *             / \
         *           /    \
         *         /       \
         *       0          4
         *      / \        / \
         *    0    2     4    6
         *   /\   /\    /\   /\
         *  0 1  2 3   4 5  6 7
         */
        t.accept(new PrintDepth());
    }
}

class Player {
    Player(String name) {
        this.name = name;
    }
    public String name;
}

abstract class Match {
    Match() {}
    Match(Player a, Player b, boolean aWins) {
        playerA = a;
        playerB = b;
        if (aWins) winner = a; else winner = b;
    }

    public String info(int depth) {
        return "[" + playerA.name + ", " + playerB.name + ", depth=" + depth + "]";
    }

    abstract void accept(Visitor v);

    public Player playerA, playerB, winner;
}

class Tournament extends Match {
    Tournament(Match l, Match r, boolean lWins) {
        leftBracket = l;
        rightBracket = r;
        playerA = l.winner;
        playerB = r.winner;
        if (lWins) winner = playerA; else winner = playerB;
    }

    @Override
    void accept(Visitor v) {
        v.visit(this);
    }

    public Match leftBracket, rightBracket;
}

class SingleMatch extends Match {
    SingleMatch(Player a, Player b, boolean aWins) {
        super(a,b,aWins);
    }
    @Override
    void accept(Visitor v) {
        v.visit(this);
    }
}

interface Visitor {
    void visit(SingleMatch m);
    void visit(Tournament t);
}

class PrintDepth implements Visitor {
    public void visit(SingleMatch m) {
        System.out.println(m.info(depth));
    }
    public void visit(Tournament t) {
        System.out.println(t.info(depth));
        depth++;
        t.leftBracket.accept(this);
        t.rightBracket.accept(this);
        depth--;
    }
    int depth = 0;
}


