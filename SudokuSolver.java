public class SudokuSolver {
    private int[][] originalTable, workingTable;

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