import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Board {
    private final int[][] blocks;
    private static final int BLANK = 0;
    private final int N;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new java.lang.IllegalArgumentException("Blocks are null");
        }
        N = tiles.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(tiles[i], 0, this.blocks[i], 0, N);
        }

    }

    // string representation of this board
    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(N);
        sb.append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        int count = 1;
        // compare the goal with every block in the board except the last one
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (count == N * N) {
                    return res;
                }
                if (blocks[i][j] != count) {
                    res++;
                }
                count++;
            }
        }
        return res;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != BLANK) {
                    int x = (blocks[i][j] - 1) / N;
                    int y = (blocks[i][j] - 1) % N;
                    count += Math.abs(x - i);
                    count += Math.abs(y - j);
                }
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) { // does this board equal y?
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        return y.toString().equals(this.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int i = 0;
        int j = 0;
        boolean flag = false;
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                if (blocks[i][j] == BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }


        List<Board> list = new ArrayList<Board>();
        if (indexValid(i - 1) && indexValid(j)) {
            list.add(getSwapBoard(i - 1, j, i, j));
        }
        if (indexValid(i + 1) && indexValid(j)) {
            list.add(getSwapBoard(i + 1, j, i, j));
        }
        if (indexValid(i) && indexValid(j - 1)) {
            list.add(getSwapBoard(i, j - 1, i, j));
        }
        if (indexValid(i) && indexValid(j + 1)) {
            list.add(getSwapBoard(i, j + 1, i, j));
        }
        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N - 1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j + 1] != 0) {
                    return getSwapBoard(i, j, i, j + 1);
                }
            }
        }
        return null;
    }

    private Board getSwapBoard(int firstX, int firstY, int secondX, int secondY) {
        if (!indexValid(firstX) || !indexValid(firstY) || !indexValid(secondX) || !indexValid(secondY)) {
            return null;
        }
        int[][] tempBoard = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(blocks[i], 0, tempBoard[i], 0, N);
        }
        int temp = tempBoard[firstX][firstY];
        tempBoard[firstX][firstY] = tempBoard[secondX][secondY];
        tempBoard[secondX][secondY] = temp;
        return new Board(tempBoard);
    }


    private boolean indexValid(int x) {
        return x >= 0 && x < N;
    }


    // unit testing (not graded)
    public static void main(String[] args) { // unit tests (not graded)
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
        System.out.println(board.hamming());
        System.out.println(board.manhattan());


        int[][] src = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] dst = src.clone();
        dst[0][0] = 1000;

        System.out.println(Arrays.deepToString(src)); // src[0][0] = 1000
        System.out.println(Arrays.deepToString(dst)); // dst[0][0] = 1000
        System.out.println(src == dst); // false
    }
}
