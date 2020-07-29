import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    private Node lastNode;

    private class Node implements Comparable<Node> {
        private int currMove = 0;
        private Board board = null;
        private Node preNode = null;
        private int priority = Integer.MAX_VALUE;

        public Node(Board inital) {
            this.currMove = 0;
            this.board = inital;
            this.preNode = null;
            this.priority = board.manhattan();
        }

        public Node(Board curr, Node pre) {
            this.currMove = pre.currMove + 1;
            this.board = curr;
            this.preNode = pre;
            this.priority = board.manhattan() + currMove;
        }

        @Override
        public int compareTo(Node that) {
            return this.priority - that.priority;
        }

    }

    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
        if (null == initial) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> mySearchNode = new MinPQ<Solver.Node>();
        mySearchNode.insert(new Node(initial));

        MinPQ<Node> yourSearchNode = new MinPQ<Solver.Node>();
        yourSearchNode.insert(new Node(initial.twin()));

        while (true) {
            lastNode = getNextNodes(mySearchNode);
            // if any of twins find the goal, the game is ended.
            if (lastNode == null && getNextNodes(yourSearchNode) == null) {

            }
            else {
                break;
            }

            // System.out.println("hahah");
        }
    }


    private Node getNextNodes(MinPQ<Node> nodes) {
        if (nodes.isEmpty()) {
            return null;
        }
        Node curr = nodes.delMin();
        if (curr.board.isGoal()) {
            return curr;
        }

        for (Board neighbor : curr.board.neighbors()) {
            if (curr.preNode == null || !neighbor.equals(curr.preNode.board)) {
                nodes.insert(new Node(neighbor, curr));
            }
        }

        return null;
    }

    public boolean isSolvable() { // is the initial board solvable?
        return lastNode != null;
    }

    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        if (isSolvable()) {
            return lastNode.currMove;
        }
        else {
            return -1;
        }
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) {
            return null;
        }
        Node tempNode = lastNode;
        Stack<Board> res = new Stack<Board>();
        while (lastNode != null) {
            res.push(lastNode.board);
            lastNode = lastNode.preNode;
        }
        List<Board> boards = new ArrayList<Board>();
        while (!res.isEmpty()) {
            boards.add(res.pop());
        }
        lastNode = tempNode;
        return boards;
    }

    public static void main(String[] args) { // solve a slider puzzle (given below)

        System.out.println(args[0]);
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}

