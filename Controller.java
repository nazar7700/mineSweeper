package sample;

import com.apple.laf.AquaUtilControlSize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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
        currentMineField.level = difficulty;
        currentMineField.makeField();
        currentMineField.setMines(currentMineField);
        makeButtonMatrix();
        btnsToExpose.setText("" + currentMineField.numCellsToExpose());
        currentMineField.print(currentMineField);
    }

//    private void setCorrectWindow(){
//
//        int pixWidth = -1;
//        int pixHeight= -1;
//
//        if(difficulty == 1){
//            pixWidth = 270;
//            pixHeight= 330;
//        }
//        if(difficulty == 2){
//            pixWidth = 480;
//            pixHeight= 540;
//
//        }
//        if(difficulty == 3){
//            pixWidth = 900;
//            pixHeight = 540;
//
//        }
//
//        currentMineField.stage.setScene(new Scene(currentMineField.sRoot, pixWidth, pixHeight));
//        currentMineField.stage.show();
//
//    }

    private void updateVIEW(){
        makeButtonMatrix();

        for (int i = 0; i < currentMineField.height; i++) {
            for (int j = 0; j < currentMineField.width; j++) {
                if(currentMineField.alreadyLost ){
                    btnMatrix[bombRow][bombCol].setStyle("-fx-background-color: red");
                    if(currentMineField.matrix[i][j].hasMine){
                        btnMatrix[i][j].setStyle("-fx-background-color: black");
                    }
                }
                if(currentMineField.matrix[i][j].marked){
                    btnMatrix[i][j].setText("X");
                }
                if (currentMineField.matrix[i][j].exposed && !currentMineField.matrix[i][j].hasMine) {
                    btnMatrix[i][j].setStyle("-fx-background-color: lightgrey");
                    if (currentMineField.matrix[i][j].numSurroundingMines != 0) {
                        btnMatrix[i][j].setText("" + currentMineField.matrix[i][j].numSurroundingMines);
                    }
                }
            }
        }
        if(currentMineField.alreadyLost){
            btnsToExpose.setText("You Lose");
        }
        else {
            btnsToExpose.setText("" + currentMineField.numCellsToExpose());
            if (currentMineField.numCellsToExpose() == 0){
                btnsToExpose.setText("You Win");
            }
        }
    }
    private void giveEachBtnAnAction(int i, int j){

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

                            else if(click != -2) {
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
//    private void makeWindowSize(){
//        bp = new BorderPane();
//        Node left = bp.getLeft();
//        bp.setAlignment(left , Pos.CENTER_LEFT);
//    }

    private void makeButtonMatrix(){

        gp.getRowConstraints().removeAll(gp.getRowConstraints());
        gp.getColumnConstraints().removeAll(gp.getColumnConstraints());
        gp.getChildren().removeAll(gp.getChildren());

        int row = currentMineField.height;
        int col = currentMineField.width;

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

                giveEachBtnAnAction(i , j);


            }
        }
    }

    @FXML
    private Button[][] btnMatrix;
    @FXML
    private GridPane gp;
    @FXML
    private BorderPane bp;
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
    private Label btnsToExpose;
    @FXML
    private void initialize(){
        level.setText("Easy");
        difficulty = 1;
        NewGame.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("smile.png")))));
        runGame();

        SaveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                MineField copy = new MineField();
                copy.level = difficulty;
                copy.makeField();


                copy.width = currentMineField.width;
                copy.height = currentMineField.height;
                copy.numOfMines = currentMineField.numOfMines;
                copy.alreadyLost = currentMineField.alreadyLost;
                copy.numExposedCells = currentMineField.numExposedCells;

                for (int i = 0; i < currentMineField.height; i++) {
                    for (int j = 0; j < currentMineField.width; j++) {
                        copy.matrix[i][j].hasMine = currentMineField.matrix[i][j].hasMine;
                        copy.matrix[i][j].exposed = currentMineField.matrix[i][j].exposed;
                        copy.matrix[i][j].marked = currentMineField.matrix[i][j].marked;
                        copy.matrix[i][j].numSurroundingMines = currentMineField.matrix[i][j].numSurroundingMines;
                    }
                }

                savedGame = copy;
                savedGame.print(savedGame);
                System.out.println("Saved Game");
            }
        });
        LoadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                MineField copy = new MineField();

                copy.level = savedGame.level;
                copy.makeField();

                for (int i = 0; i < savedGame.height; i++) {
                    for (int j = 0; j < savedGame.width; j++) {
                        copy.matrix[i][j].hasMine = savedGame.matrix[i][j].hasMine;
                        copy.matrix[i][j].exposed = savedGame.matrix[i][j].exposed;
                        copy.matrix[i][j].marked = savedGame.matrix[i][j].marked;
                        copy.matrix[i][j].numSurroundingMines = savedGame.matrix[i][j].numSurroundingMines;
                    }
                }

                copy.width = savedGame.width;
                copy.height = savedGame.height;
                copy.numOfMines = savedGame.numOfMines;
                copy.alreadyLost = savedGame.alreadyLost;
                copy.numExposedCells = savedGame.numExposedCells;

                currentMineField = copy;
                currentMineField.print(currentMineField);
                System.out.println("Loaded Game");
                updateVIEW();
            }
        });

        Beginner.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level.setText("Easy");
                difficulty = 1;
            }
        });

        Intermediate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level.setText("Medium");
                difficulty = 2;
            }
        });

        Expert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                level.setText("Hard");
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
