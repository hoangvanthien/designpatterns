package composite;

import java.util.LinkedList;
import java.util.Queue;

/**
 * You are organizing a champion league.
 * Every match has two players. A Match can either be a Single Match,
 * or a Tournament. In the latter case, the two players are the winners of
 * two other Matches.
 *
 * The Composite Pattern is a recursive structure, where an object is either a composite
 * of general objects (i.e. abstract type) (e.g. a Tournament contains two Matches),
 * or a leaf node on the tree (e.g. a Single Match)
 *
 * The abstract type (Match) declares abstract operations (printDFS, printBFS), that
 * other concrete classes ("composite" Tournament or "leaf" Single Match) must implement
 *
 * When these methods are called on a composite object, it may call the same method on
 * its children to finish the task (similar to Chain of Responsibility pattern)
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

        t.printDFS();
        System.out.println();
        t.printBFS();
        System.out.println();
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
    public void printDFS() {
        if (this instanceof SingleMatch) {
            System.out.print("[" + this.playerA.name + ", " + this.playerB.name + "] ");
            return;
        }
        Tournament t = (Tournament) this;
        System.out.print("[" + this.playerA.name + ", " + this.playerB.name + "] "); // pre-order
        t.leftBracket.printDFS();
        // System.out.println("[" + this.playerA.name + ", " + this.playerB.name + "] "); // in-order
        t.rightBracket.printDFS();
        // System.out.println("[" + this.playerA.name + ", " + this.playerB.name + "] "); // post-order
    }

    public void printBFS() {
        Queue<Match> q = new LinkedList<>();
        q.add(this);
        while (!q.isEmpty()) {
            Match next = q.poll();
            System.out.print("[" + next.playerA.name + ", " + next.playerB.name + "] ");
            if (next instanceof SingleMatch) continue;
            Tournament t = (Tournament) next;
            q.add(t.leftBracket);
            q.add(t.rightBracket);
        }
    }
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
    public Match leftBracket, rightBracket;
}

class SingleMatch extends Match {
    SingleMatch(Player a, Player b, boolean aWins) {
        super(a,b,aWins);
    }
}

