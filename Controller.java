package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

public class Controller {

    private int difficulty;
    private int rows;
    private int cols;
    MineField currentMineField;

    MineField savedGame;
    private int savedRows;
    private int savedCols;

    @FXML
    private MenuButton level;
    @FXML
    private MenuItem Beginner;
    @FXML
    private MenuItem Intermediate;
    @FXML
    private MenuItem Expert;
    @FXML
    private Button NewGame;
    @FXML
    private Button SaveGame;
    @FXML
    private Button LoadGame;
    @FXML
    private void initialize(){
        level.setText("Beginner");
        difficulty = 1;

//        SaveGame.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                savedGame = new MineField();
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < cols; j++) {
//                        savedGame.matrix[i][j].exposed = currentMineField.matrix[i][j].exposed;
//                        savedGame.matrix[i][j].hasMine = currentMineField.matrix[i][j].hasMine;
//                        savedGame.matrix[i][j].marked = currentMineField.matrix[i][j].marked;
//                        savedGame.matrix[i][j].numSurroundingMines = currentMineField.matrix[i][j].numSurroundingMines;
//                        savedRows = currentMineField.height;
//                        savedCols = currentMineField.width;
//
//                    }
//                }
//                System.out.println("Saved Game");
//
//            }
//        });
//        LoadGame.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                    for (int i = 0; i < savedRows; i++) {
//                        for (int j = 0; j < savedCols; j++) {
//                            currentMineField = savedGame;
//
//                        }
//                    }
//                currentMineField.print(currentMineField);
//                System.out.println("Loaded Game");
//
//                }
//
//        });

        Beginner.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level.setText("Beginner");
                difficulty = 1;
            }
        });

        Intermediate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level.setText("Intermediate");
                difficulty = 2;
            }
        });

        Expert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                level.setText("Expert");
                difficulty = 3;
            }
        });

        NewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentMineField = new MineField();
                currentMineField.makeField(difficulty);
                currentMineField.setMines(currentMineField);



                currentMineField.print(currentMineField);
            }
        });




//        btnMatrix = new Button[9][9];
//        for (int i = 0; i < btnMatrix.length; i++) {
//            for (int j = 0; j < btnMatrix.length; j++) {
//                Button btn = new Button();
//                System.out.println("It is: "+ btn.getScaleX());
//                btnMatrix[i][j] = btn;
//                gp.add(btn, j, i);
//            }
//        }


    }

}
