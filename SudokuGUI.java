/**
 * Created by bert2 on 2016-03-23.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private SudokuTextField[][] sudokuTable = new SudokuTextField[9][9];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sudoku solver");
        primaryStage.getIcons().add(new Image("file:Icon.png"));
        BorderPane root = new BorderPane();

        TilePane sudokuGrid = new TilePane();
        sudokuGrid.setPrefColumns(3);
        sudokuGrid.setPrefRows(3);
        sudokuGrid.setPadding(new Insets(4, 4, 4, 4));
        sudokuGrid.setHgap(3);
        sudokuGrid.setVgap(3);
        sudokuGrid.setAlignment(Pos.CENTER);
        sudokuGrid.setTileAlignment(Pos.CENTER);
        sudokuGrid.setStyle("-fx-background-color: #DADADF");
        for(int index = 0; index < 9; index++) {
            createSquare(sudokuGrid, index);
        }
        root.setTop(sudokuGrid);

        Button buttonSolve = new Button();
        buttonSolve.setText("Solve");
        buttonSolve.setStyle("-fx-font: 18 segoiui;");
        buttonSolve.setPrefSize(120, 40);
        buttonSolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int[][] ret = new int[9][9];
                for(int i = 0; i < 9; i++) {
                    for(int j = 0; j < 9; j++) {
                        ret[i][j] = Integer.parseInt(sudokuTable[i][j].getText());
                    }
                }
            }
        });

        Button buttonClear = new Button();
        buttonClear.setText("Clear");
        buttonClear.setStyle("-fx-font: 18 segoiui;");
        buttonClear.setPrefSize(120, 40);
        buttonClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(Node pane:sudokuGrid.getChildren()) {
                    for(Node field:((TilePane) pane).getChildren()) {
                        ((SudokuTextField) field).setText("");
                    }
                }
            }
        });

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
        TilePane pane = new TilePane();
        pane.setPrefRows(3);
        pane.setPrefColumns(3);
        pane.setVgap(index % 2 == 0 ? 3 : 4);
        pane.setHgap(index % 2 == 0 ? 3 : 4);
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(2, 2, 2, 2));

        for(int i = 0; i < 9; i++) {
            SudokuTextField field = new SudokuTextField(1);
            field.setPrefSize(index % 2 == 0 ? 45 : 43, index % 2 == 0 ? 40 : 39);
            field.setAlignment(Pos.CENTER);
            field.setPadding(new Insets(0, 0, 0, 0));
            if(index % 2 == 0)
                field.setStyle("-fx-background-color: #ffa500; -fx-font: 22 segoiui;");
            else
                field.setStyle("-fx-font: 22 segoiui;");
            pane.getChildren().add(field);
        }
        sudokuGrid.getChildren().add(pane);
    }

    /**
     * TextField with limited maximum length
     */
    private class SudokuTextField extends TextField {
        int length;
        int compare;

        private SudokuTextField(int length)
        {
            super();
            this.length = length;
        }

        public void replaceText(int start, int end, String text)
        {
            compare = getText().length() - (end - start) + text.length();
            if(compare <= length || start != end)
                super.replaceText( start, end, text );
        }

        public void replaceSelection(String text)
        {
            compare = getText().length() + text.length();
            if(compare <= length )
                super.replaceSelection( text );
        }

    }
}
