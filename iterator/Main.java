package iterator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * You are organizing a champion league.
 * Every match has two players. A Match can either be a Single Match,
 * or a Tournament. In the latter case, the two players are the winners of
 * two other Matches.
 *
 * You want your client to traverse through the tree (DFS/BFS) without diving into
 * the complex structure of the objects. You write different Iterators (DFSIterator,
 * BFSIterator) with two main methods in each:
 * - hasNext(): if there is any node left unvisited
 * - next(): return a node in the traverse order
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

        Iterator dfs = new DFSIterator(t);
        while (dfs.hasNext()) {
            System.out.print(dfs.next().toString() + " ");
        }
        System.out.println();

        Iterator bfs = new BFSIterator(t);
        while (bfs.hasNext()) {
            System.out.print(bfs.next().toString() + " ");
        }
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

    @Override
    public String toString() {
        return "[" + playerA.name + ", " + playerB.name + "]";
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

interface Iterator {
    boolean hasNext();
    Match next();
}

class DFSIterator implements Iterator {
    DFSIterator(Match root) {
        s = new Stack<>();
        s.add(root);
    }

    @Override
    public boolean hasNext() {
        return !s.isEmpty();
    }

    @Override
    public Match next() {
        Match result = s.pop();
        if (result instanceof SingleMatch) return result;
        Tournament t = (Tournament) result;
        s.add(t.rightBracket);
        s.add(t.leftBracket);
        return result;
    }

    private Stack<Match> s;
}

class BFSIterator implements Iterator {
    BFSIterator(Match root) {
        q = new LinkedList<>();
        q.add(root);
    }

    @Override
    public boolean hasNext() {
        return !q.isEmpty();
    }

    @Override
    public Match next() {
        Match result = q.poll();
        if (result instanceof SingleMatch) return result;
        Tournament t = (Tournament) result;
        q.add(t.leftBracket);
        q.add(t.rightBracket);
        return result;
    }

    private Queue<Match> q;
}

