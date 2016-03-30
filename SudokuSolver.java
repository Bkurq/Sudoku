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
            for (int b = 0; b < 9; b++) {
                workingTable[a][b] = table[a][b];
                originalTable[a][b] = table[a][b];
            }
        }
    }

    /**
     * Solves sudoku
     * @return 9x9 array with answer or -1 in cells where entries do not follow sudoku rules
     */
    public int[][] solve() {
        //Check if user input satisfies sudoku rules
        boolean solvable = true;
        for(int a = 0; a < 9; a++) {
            for (int b = 0; b < 9; b++) {
                if(originalTable[a][b] != 0)
                    if(!checkSudokuRules(a, b)) {
                        originalTable[a][b] = -1;
                        solvable = false;
                    }
            }
        }

        //Solve
        if(solvable) {
            solve(0, 0);
            return workingTable;
        }
        return originalTable;
    }

    /**
     * Recursive method that solves sudoku with backtracking
     * @param row specifies row position
     * @param col specifies column position
     * @return true if the value in the cell follows rules of sudoku, false otherwise
     */
    private boolean solve(int row, int col) {
        //Check if the whole table has been filled
        if(col == 9) {
            if(row == 8) {
                return true;
            }
            col = 0;
            row++;
        }
        //The cell is empty
        if(originalTable[row][col] == 0) {
            for(int a = 1; a < 10; a++) {
                workingTable[row][col] = a;
                if(checkSudokuRules(row, col)) {
                    if (solve(row, col + 1)) {
                        return true;
                    }
                }
            }
            workingTable[row][col] = 0;
        }
        //The cell has been filled by the user
        else {
            if(checkSudokuRules(row, col)) {
                return solve(row, col + 1);
            }
        }
        return false;
    }

    /**
     * Checks if the number in workingTable at position specified by row and col follows rules of sudoku
     * @param row specifies row position
     * @param col specifies column position
     * @return true if the value in the cell follows rules of sudoku, false otherwise
     */
    private boolean checkSudokuRules(int row, int col) {
        //check row and column for same number
        for(int index = 0; index < 9; index++) {
            if(((workingTable[row][col] == workingTable[index][col]) && index != row) || ((workingTable[row][col] == workingTable[row][index]) && index != col))
                return false;
        }

        //determine the top left most corner of 3x3 subsquare
        int lowRowQuadrant = row - row % 3;
        int lowColQuadrant = col - col % 3;
        //check 3x3 subsquare
        for(int a = lowRowQuadrant; a < lowRowQuadrant + 3; a++) {
            for(int b = lowColQuadrant; b < lowColQuadrant + 3; b++) {
                if(!(a == row && b == col) && workingTable[a][b] == workingTable[row][col])
                    return false;
            }
        }
        return true;
    }
}