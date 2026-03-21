import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int n;
    private final int[][] tiles;

    // Create a board from an n-by-n array of tiles
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Tiles array cannot be null");
        }
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, n);
        }
    }

    // String representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%3d", tiles[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Board dimension n
    public int dimension() {
        return n;
    }

    // Number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];
                if (tile == 0) continue;
                int goal = i * n + j + 1;
                if (tile != goal) {
                    count++;
                }
            }
        }
        return count;
    }

    // Sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];
                if (tile == 0) continue;
                int goalRow = (tile - 1) / n;
                int goalCol = (tile - 1) % n;
                distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
            }
        }
        return distance;
    }

    // Is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // Does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board = (Board) y;
        if (n != board.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != board.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // All neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();
        int blankRow = -1;
        int blankCol = -1;

        // Find the blank square
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
            if (blankRow != -1) break;
        }

        // Generate neighbors
        // Up
        if (blankRow > 0) {
            int[][] newTiles = copyTiles();
            swap(newTiles, blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(new Board(newTiles));
        }
        // Down
        if (blankRow < n - 1) {
            int[][] newTiles = copyTiles();
            swap(newTiles, blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(new Board(newTiles));
        }
        // Left
        if (blankCol > 0) {
            int[][] newTiles = copyTiles();
            swap(newTiles, blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(new Board(newTiles));
        }
        // Right
        if (blankCol < n - 1) {
            int[][] newTiles = copyTiles();
            swap(newTiles, blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(new Board(newTiles));
        }

        return neighbors;
    }

    // A board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = copyTiles();
        int i1 = -1, j1 = -1;

        // Find first non-zero tile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (twinTiles[i][j] != 0) {
                    i1 = i;
                    j1 = j;
                    break;
                }
            }
            if (i1 != -1) break;
        }

        // Find second non-zero tile (different position)
        int i2 = -1, j2 = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (twinTiles[i][j] != 0 && (i != i1 || j != j1)) {
                    i2 = i;
                    j2 = j;
                    break;
                }
            }
            if (i2 != -1) break;
        }

        // Swap the two tiles
        swap(twinTiles, i1, j1, i2, j2);
        return new Board(twinTiles);
    }

    // Helper: Create a deep copy of the tiles array
    private int[][] copyTiles() {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, n);
        }
        return copy;
    }

    // Helper: Swap two elements in a 2D array
    private void swap(int[][] tiles, int i1, int j1, int i2, int j2) {
        int temp = tiles[i1][j1];
        tiles[i1][j1] = tiles[i2][j2];
        tiles[i2][j2] = temp;
    }

    // Unit testing (not graded)
    public static void main(String[] args) {
        // Test constructor and toString()
        int[][] tiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(tiles);
        System.out.println("Board:");
        System.out.println(board);

        // Test dimension()
        System.out.println("Dimension: " + board.dimension()); // Expected: 3

        // Test hamming()
        System.out.println("Hamming distance: " + board.hamming()); // Expected: 4

        // Test manhattan()
        System.out.println("Manhattan distance: " + board.manhattan()); // Expected: 4

        // Test isGoal()
        System.out.println("Is goal? " + board.isGoal()); // Expected: false

        // Test equals()
        int[][] sameTiles = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board sameBoard = new Board(sameTiles);
        System.out.println("Equals same board? " + board.equals(sameBoard)); // Expected: true

        int[][] differentTiles = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        Board differentBoard = new Board(differentTiles);
        System.out.println("Equals different board? " + board.equals(differentBoard)); // Expected: false

        // Test neighbors()
        System.out.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }

        // Test twin()
        System.out.println("Twin:");
        System.out.println(board.twin());
    }
}