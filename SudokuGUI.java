/**
 * Created by bert2 on 2016-03-23.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));

        TilePane sudokuGrid = new TilePane();
        sudokuGrid.setPrefColumns(9);
        sudokuGrid.setPrefRows(9);
        for(int index = 0; index < 81; index++) {
            TextField field = new TextField();
            field.setPrefSize(50, 50);
            sudokuGrid.getChildren().add(field);
        }
        root.setTop(sudokuGrid);

        Button buttonSolve = new Button();
        buttonSolve.setText("Solve");
        buttonSolve.setStyle("-fx-font: 18 segoiui;");
        buttonSolve.setPrefSize(150, 50);
        buttonSolve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Solve");
            }
        });

        Button buttonClear = new Button();
        buttonClear.setText("Clear");
        buttonClear.setStyle("-fx-font: 18 segoiui;");
        buttonClear.setPrefSize(150, 50);
        buttonClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clear");
            }
        });

        HBox controls = new HBox();
        controls.setSpacing(20);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(buttonSolve, buttonClear);

        root.setBottom(controls);

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 460, 520));
        primaryStage.show();
    }
}
