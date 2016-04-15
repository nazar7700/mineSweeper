package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Controller {

    private int difficulty;
    MineField currentMineField;
    MineField savedGame;

    private void runGame(){
        currentMineField = new MineField();
        currentMineField.makeField(difficulty);
        currentMineField.setMines(currentMineField);
        makeButtonMatrix();

        currentMineField.print(currentMineField);
    }

    private void updateVIEW(){
        makeButtonMatrix();

        gp.setGridLinesVisible(true);
        for (int i = 0; i < currentMineField.height; i++) {
            for (int j = 0; j < currentMineField.width; j++) {
                if(currentMineField.matrix[i][j].exposed){
                   if(!currentMineField.matrix[i][j].hasMine){
                       btnMatrix[i][j].setBackground(Background.EMPTY);
                       if(currentMineField.matrix[i][j].numSurroundingMines != 0) {
                           String num = "" + currentMineField.matrix[i][j].numSurroundingMines;
                           btnMatrix[i][j].setText(num);
                       }

                   }
                }
            }
        }

    }

    private void makeButtonMatrix(){

        gp.getRowConstraints().removeAll(gp.getRowConstraints());
        gp.getColumnConstraints().removeAll(gp.getColumnConstraints());
        gp.getChildren().removeAll(gp.getChildren());

        int row = currentMineField.height;
        int col = currentMineField.width;
        //System.out.println(row +"," +col);

        for (int i = 0; i < row; i++) {
            RowConstraints rConstriant = new RowConstraints();
            rConstriant.setPrefHeight(30);
            gp.getRowConstraints().add(rConstriant);
        }

        for (int j = 0; j < col; j++) {
            ColumnConstraints cConstraint = new ColumnConstraints();
            cConstraint.setPrefWidth(30);
            gp.getColumnConstraints().add(cConstraint);
        }

        btnMatrix = new Button[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Button btn = new Button();
                btn.setMaxSize(30, 30);
                btn.setMinSize(30, 30);
                btnMatrix[i][j] = btn;
                gp.add(btn, j, i);

                int currRow = i;
                int currCol = j;

                btnMatrix[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if(!currentMineField.matrix[currRow][currCol].exposed && !currentMineField.matrix[currRow][currCol].marked){
                            currentMineField.expose(currRow, currCol);
                            currentMineField.print(currentMineField);
                            updateVIEW();
                        }
                    }
                });
            }
        }

        gp.setGridLinesVisible(true);
    }

    @FXML
    private Button[][] btnMatrix;
    @FXML
    private GridPane gp;
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
        runGame();
        makeButtonMatrix();


        SaveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                savedGame = currentMineField;
                System.out.println("Saved Game");
            }
        });
        LoadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentMineField = savedGame;
                currentMineField.print(currentMineField);
                System.out.println("Loaded Game");
                updateVIEW();
            }
        });

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
                runGame();
            }
        });

    }

}
