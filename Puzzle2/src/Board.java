import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private int n;
    private final int BLANK = 0;
    private int[][] board = null;

    public Board(
            int[][] blocks) { // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
        if (null == blocks) {
            throw new IllegalArgumentException();
        }
        n = blocks.length;
        assert (n >= 2 && n < 128);
        board = clone(blocks);
    }


    public int dimension() { // board dimension n
        return n;
    }

    public int hamming() { // number of blocks out of place
        int res = 0;
        int count = 1;
        // compare the goal with every block in the board except the last one
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (count == n * n) {
                    return res;
                }
                if (board[i][j] != count) {
                    res++;
                }
                count++;
            }
        }
        return res;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != BLANK) {
                    int x = (board[i][j] - 1) / n;
                    int y = (board[i][j] - 1) % n;
                    count += Math.abs(x - i);
                    count += Math.abs(y - j);
                }
            }
        }
        return count;
    }

    public boolean isGoal() { // is this board the goal board?
        return manhattan() == 0;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0) {
                    return getSwapBoard(i, j, i, j + 1);
                }
            }
        }
        return null;
    }

    private int[][] clone(int[][] src) {
        int[][] dst = new int[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            for (int j = 0; j < src[0].length; j++) {
                dst[i][j] = src[i][j];
            }
        }
        return dst;
    }

    private Board getSwapBoard(int firstX, int firstY, int secondX, int secondY) {
        if (!indexValid(firstX) || !indexValid(firstY) || !indexValid(secondX) || !indexValid(
                secondY)) {
            return null;
        }
        int[][] tempBoard = clone(board);
        int temp = tempBoard[firstX][firstY];
        tempBoard[firstX][firstY] = tempBoard[secondX][secondY];
        tempBoard[secondX][secondY] = temp;
        return new Board(tempBoard);
    }

    private boolean indexValid(int x) {
        return x >= 0 && x < n;
    }

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

    public Iterable<Board> neighbors() { // all neighboring boards
        // find the blank block position
        int i = 0;
        int j = 0;
        boolean flag = false;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (board[i][j] == BLANK) {
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

    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", board[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)
        Board board = new Board(new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } });
        System.out.println(board.hamming());
        System.out.println(board.manhattan());


        int[][] src = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] dst = src.clone();
        dst[0][0] = 1000;

        System.out.println(Arrays.deepToString(src)); // src[0][0] = 1000
        System.out.println(Arrays.deepToString(dst)); // dst[0][0] = 1000
        System.out.println(src == dst); // false
    }
}

