public class SudokuSolver {
    private int[][] workingTable;
    private int[][] originalTable;

    public SudokuSolver(int[][] table) {
        if(!(table.length == 9 && table[0].length == 9))
            throw new IllegalArgumentException("The sudoku table must 9 x 9");
        originalTable = (int[][]) table.clone();
        workingTable = (int[][]) table.clone();
    }

    public boolean solve() {
        return solve(0, 0);
    }

    /**
     * Recursive method that solves sudoku with backtracking
     * @param row
     * @param col
     * @return
     */
    private boolean solve(int row, int col) {
        if(originalTable[row][col] != 0)
            if(checkRules(row, col)) {
                solve(row, col + 1);
            } else {
                return false;
            }
        return false;
    }

    /**
     * Checks if the number in workingTable at position specified by row and col fulfills rules of sudoku
     * @param row
     * @param col
     * @return
     */
    private boolean checkRules(int row, int col) {
        return false;
    }
}