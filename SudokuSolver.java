public class SudokuSolver {
    private int[][] workingTable;
    private int[][] originalTable;

    public SudokuSolver(int[][] table) {
        originalTable = (int[][]) table.clone();
        workingTable = (int[][]) table.clone();
    }

    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int x, int y) {
        return false;
    }

}