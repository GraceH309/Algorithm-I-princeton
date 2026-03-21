import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Solver {
    private Node goalNode;
    private boolean solvable;

    // Find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }

        // Priority queues for initial and twin boards
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> twinPq = new MinPQ<>();

        // Initial nodes
        Node initialNode = new Node(initial, 0, null);
        Node twinInitialNode = new Node(initial.twin(), 0, null);

        // Insert into queues
        pq.insert(initialNode);
        twinPq.insert(twinInitialNode);

        // Process queues in lockstep
        while (true) {
            // Process initial board's queue
            Node current = pq.delMin();
            if (current.board.isGoal()) {
                goalNode = current;
                solvable = true;
                break;
            }

            // Process twin board's queue
            Node twinCurrent = twinPq.delMin();
            if (twinCurrent.board.isGoal()) {
                solvable = false;
                break;
            }

            // Enqueue neighbors of current (skip previous board)
            for (Board neighbor : current.board.neighbors()) {
                if (current.prev == null || !neighbor.equals(current.prev.board)) {
                    pq.insert(new Node(neighbor, current.moves + 1, current));
                }
            }

            // Enqueue neighbors of twinCurrent (skip previous board)
            for (Board neighbor : twinCurrent.board.neighbors()) {
                if (twinCurrent.prev == null || !neighbor.equals(twinCurrent.prev.board)) {
                    twinPq.insert(new Node(neighbor, twinCurrent.moves + 1, twinCurrent));
                }
            }
        }
    }

    // Is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // Min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solvable ? goalNode.moves : -1;
    }

    // Sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) {
            return null;
        }

        // Reconstruct path from goal to initial
        Stack<Board> stack = new Stack<>();
        Node node = goalNode;
        while (node != null) {
            stack.push(node.board);
            node = node.prev;
        }

        // Convert to list (initial to goal) and make immutable
        List<Board> solution = new ArrayList<>();
        while (!stack.isEmpty()) {
            solution.add(stack.pop());
        }
        return Collections.unmodifiableList(solution);
    }

    // Private search node class
    private static class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev;
        private final int manhattan;
        private final int priority;

        public Node(Board board, int moves, Node prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = board.manhattan();
            this.priority = this.manhattan + this.moves;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // Test client (see below)
    public static void main(String[] args) {
        // Read input file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // Solve the puzzle
        Solver solver = new Solver(initial);

        // Print solution
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}