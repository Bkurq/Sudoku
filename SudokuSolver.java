/**
 * Class for solving sudoku by recursive backtracking
 */
public class SudokuSolver {
    private int[][] workingTable;
    private int[][] originalTable;

    public SudokuSolver(int[][] table) {
        if(!(table.length == 9 && table[0].length == 9))
            throw new IllegalArgumentException("The sudoku table must 9 x 9 integer array");
        workingTable = new int[9][9];
        originalTable = new int[9][9];
        for(int a = 0; a < 9; a++) {
            for(int b = 0; b < 9; b++) {
                workingTable[a][b] = new Integer(table[a][b]);
                originalTable[a][b] = new Integer(table[a][b]);
            }
        }
    }

    public boolean solve() {
        return solve(0, 0);
    }

    public int[][] getAnswer() { return workingTable;}

    /**
     * Recursive method that solves sudoku with backtracking
     * @param row specifies row position
     * @param col specifies column position
     * @return true if the value in the cell follows rules of sudoku, false otherwise
     */
    private boolean solve(int row, int col) {
        if(col > 8) {
            if(row == 8)
                return true;
            col = 0;
            row++;
        }
        if(workingTable[row][col] == - 1)
            return false;
        if(originalTable[row][col] != 0) {
            if (checkRules(row, col)) {
                return solve(row, col + 1);
            }
        }
        else {
            for(int a = 1; a < 10; a++) {
                workingTable[row][col] = a;
                if(checkRules(row, col))
                    if(solve(row, col + 1)) {
                        return true;
                    }
            }
        }
        workingTable[row][col] = -1;
        return false;
    }

    /**
     * Checks if the number in workingTable at position specified by row and col follows rules of sudoku
     * @param row specifies row position
     * @param col specifies column position
     * @return true if the value in the cell follows rules of sudoku, false otherwise
     */
    private boolean checkRules(int row, int col) {
        for(int index = 0; index < 9; index++) {
            if(((workingTable[row][col] == workingTable[index][col]) && index != row) || ((workingTable[row][col] == workingTable[row][index]) && index != col))
                return false;
        }
        int lowRowQuadrant = row - row % 3;
        int lowColQuadrant = col - col % 3;
        for(int a = lowRowQuadrant; a < lowRowQuadrant + 3; a++) {
            for(int b = lowColQuadrant; b < lowColQuadrant + 3; b++) {
                if( workingTable[a][b] == workingTable[row][col])
                    return false;
            }
        }
        return true;
    }
}