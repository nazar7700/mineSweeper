package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class Controller {

    private int bombRow;
    private int bombCol;
    private int difficulty;
    MineField currentMineField;
    MineField savedGame;

    private void runGame(){
        currentMineField = new MineField();
        bombRow = -1;
        bombCol = -1;
        currentMineField.makeField(difficulty);
        currentMineField.setMines(currentMineField);
        makeButtonMatrix();

        currentMineField.print(currentMineField);
    }

    private void losingCondition(){

    }

    private void updateVIEW(){
        makeButtonMatrix();

        gp.setGridLinesVisible(true);
        for (int i = 0; i < currentMineField.height; i++) {
            for (int j = 0; j < currentMineField.width; j++) {
                if(bombCol != -1 && bombRow != -1 ){
                    btnMatrix[bombRow][bombCol].setStyle("-fx-background-color: red");
                    if(currentMineField.matrix[i][j].hasMine){
                        btnMatrix[i][j].setStyle("-fx-background-color: red");
                    }
                }
                if(currentMineField.matrix[i][j].marked){
                    btnMatrix[i][j].setText("X");
                }
                if (currentMineField.matrix[i][j].exposed && !currentMineField.matrix[i][j].hasMine) {
                    btnMatrix[i][j].setStyle("-fx-background-color: lightgrey");
                    if (currentMineField.matrix[i][j].numSurroundingMines != 0) {
                        String num = "" + currentMineField.matrix[i][j].numSurroundingMines;
                        btnMatrix[i][j].setText(num);
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

                final int currRow = i;
                final int currCol = j;

                btnMatrix[i][j].setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if(currentMineField.numCellsToExpose() != 0) {
                            if (event.isSecondaryButtonDown()) {
                                if (!currentMineField.matrix[currRow][currCol].marked) {
                                    currentMineField.matrix[currRow][currCol].marked = true;
                                    currentMineField.print(currentMineField);
                                } else {
                                    currentMineField.matrix[currRow][currCol].marked = false;
                                }
                            }
                            if (event.isPrimaryButtonDown()) {
                                if (!currentMineField.alreadyLost && !currentMineField.matrix[currRow][currCol].marked) {
                                    int click = currentMineField.expose(currRow, currCol);
                                    if (click == -1) {
                                        bombRow = currRow;
                                        bombCol = currCol;
                                        System.out.println("You Lose");
                                    }

                                    if(click != -2) {
                                        currentMineField.expose(currRow, currCol);
                                        currentMineField.print(currentMineField);
                                        if (currentMineField.numCellsToExpose() == 0) {
                                            System.out.println("You Win");
                                        }
                                    }
                                }
                            }
                        }
                        updateVIEW();
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
                System.out.println(currentMineField.alreadyLost);
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
