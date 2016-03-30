/**
 * Created by bert2 on 2016-03-23.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SudokuGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku solver");
        primaryStage.getIcons().add(new Image("file:Icon.png"));
        BorderPane root = new BorderPane();

        //Sudoku grid
        TilePane sudokuGrid = new TilePane();
        sudokuGrid.setPrefColumns(3);
        sudokuGrid.setPrefRows(3);
        sudokuGrid.setPadding(new Insets(6, 0, 6, 0));
        sudokuGrid.setHgap(3);
        sudokuGrid.setVgap(3);
        sudokuGrid.setAlignment(Pos.CENTER);
        sudokuGrid.setTileAlignment(Pos.CENTER);
        sudokuGrid.setStyle("-fx-background-color: #DADADF");
        for(int index = 0; index < 9; index++) {
            createSquare(sudokuGrid, index);
        }
        root.setTop(sudokuGrid);

        //Button to solve sudoku
        Button buttonSolve = new Button();
        buttonSolve.setText("Solve");
        buttonSolve.setStyle("-fx-font: 18 segoiui;");
        buttonSolve.setPrefSize(120, 40);
        buttonSolve.setOnAction(event -> {
            int[][] ret = new int[9][9];
            for(Node pane:sudokuGrid.getChildren()){
                for(Node field:((TilePane) pane).getChildren()) {
                    int row = ((SudokuTextField) field).getRowPosition();
                    int col = ((SudokuTextField) field).getColumnPosition();
                    String string = ((SudokuTextField) field).getText();
                    if(string.length() == 0)
                        ret[row][col] = 0;
                    else
                        ret[row][col] = Integer.parseInt(string);
                }
            }
            SudokuSolver solver = new SudokuSolver(ret);
            ret = solver.solve();
            int index = 0;
            for(Node pane:sudokuGrid.getChildren()){
                for(Node field:((TilePane) pane).getChildren()){
                    SudokuTextField sudokuField = (SudokuTextField) field;
                    int row = sudokuField.getRowPosition();
                    int col = sudokuField.getColumnPosition();

                    if(ret[row][col] == -1) {
                        field.setStyle("-fx-background-color: #ff7a7a; -fx-font: 22 segoiui;");
                    } else if(ret[row][col] == 0) {
                        sudokuField.setText("");
                    } else {
                        sudokuField.setText(Integer.toString(ret[row][col]));
                        sudokuField.setFieldStyle(index);
                    }
                }
                index++;
            }
        });

        //Button to clear the grid
        Button buttonClear = new Button();
        buttonClear.setText("Clear");
        buttonClear.setStyle("-fx-font: 18 segoiui;");
        buttonClear.setPrefSize(120, 40);
        buttonClear.setOnAction(event -> {
            int index = 0;
            for(Node pane:sudokuGrid.getChildren()){
                for(Node field:((TilePane) pane).getChildren()){
                    ((SudokuTextField) field).setText("");
                    ((SudokuTextField) field).setFieldStyle(index);
                }
                index++;
            }
        });

        //Horizontal box that contains buttons
        HBox controls = new HBox();
        controls.setPadding(new Insets(4, 4, 4, 4));
        controls.setSpacing(20);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(buttonSolve, buttonClear);

        root.setCenter(controls);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void createSquare(TilePane sudokuGrid, int index) {
        //3x3 subsquare
        TilePane pane = new TilePane();
        pane.setPrefRows(3);
        pane.setPrefColumns(3);
        pane.setVgap(index % 2 == 0 ? 3 : 4);
        pane.setHgap(index % 2 == 0 ? 3 : 4);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(1, 1, 1, 1));

        //Fill the square and map tiles to their coordinates
        for(int i = 0; i < 9; i++) {
            //Determine the tile's position in the sudoku grid
            int fieldNumber = (index % 3) * 3 + (index / 3) * 27 + (i / 3) * 9 + i % 3 + 1;
            int row = (fieldNumber - 1) / 9;
            int col = (fieldNumber - 1) % 9;

            //Formatting
            SudokuTextField field = new SudokuTextField(row, col);
            field.setPrefSize(index % 2 == 0 ? 45 : 43, index % 2 == 0 ? 40 : 39);
            field.setAlignment(Pos.CENTER);
            field.setPadding(new Insets(0, 0, 0, 0));

            //Styling
            field.setFieldStyle(index);

            pane.getChildren().add(field);
        }
        sudokuGrid.getChildren().add(pane);
    }

    /**
     * TextField with limited maximum length that only accepts number 1-9 and holds its coordinates in the sudoku grid
     */
    private class SudokuTextField extends TextField {
        private final int length;
        private int compare;
        private final int rowPosition;
        private final int columnPosition;

        private SudokuTextField(int row, int col)
        {
            super();
            this.length = 1;
            rowPosition = row;
            columnPosition = col;
        }

        //Ensures that only numbers can be entered by the user
        public void replaceText(int start, int end, String text) {
            if(text.length() == 0)
                super.replaceText(start, end, text);
            compare = getText().length() - (end - start) + text.length();
            if(compare <= length || start != end) {
                try {
                    if(Integer.parseInt(text) > 0)
                        super.replaceText(start, end, text);
                } catch (Exception ignored) {

                }
            }
        }

        //Ensures that only numbers can be entered by the user
        public void replaceSelection(String text) {
            if(text.length() == 0)
                super.replaceSelection(text);
            compare = getText().length() + text.length();
            if(compare <= length )
                try {
                    if(Integer.parseInt(text) > 0)
                        super.replaceSelection(text);
                } catch (Exception ignored) {

                }

        }

        //Styling
        public void setFieldStyle(int index) {
            if(index % 2 == 0)
                this.setStyle("-fx-background-color: #ffa500; -fx-font: 22 segoiui;");
            else
                this.setStyle("-fx-font: 22 segoiui;");
        }

        public int getRowPosition() {
            return rowPosition;
        }

        public int getColumnPosition() {
            return columnPosition;
        }
    }
}
