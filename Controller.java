package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class Controller {

    private int bombRow;
    private int bombCol;
    private MineField currentMineField;
    private MineField savedGame;


    private void runGame(){
        currentMineField = new MineField();
        SaveGame.setText("Save Game");
        NewGame.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("smile.png")))));
        currentMineField.level = getNewDiff();
        currentMineField.makeField();
        makeButtonMatrix();

        //currentMineField.setMines();
        btnsToExpose.setText("" + currentMineField.numCellsToExpose());
        //currentMineField.print();
    }

    private int getNewDiff(){
        int difficulty = 1;

        if (level.getText() == "Medium"){
            difficulty = 2;
        }
        if(level.getText() == "Hard"){
            difficulty = 3;
        }

        return difficulty;
    }

    private void updateVIEW(){
        makeButtonMatrix();

        for (int i = 0; i < currentMineField.height; i++) {
            for (int j = 0; j < currentMineField.width; j++) {
                if(currentMineField.alreadyLost ){
                    btnMatrix[bombRow][bombCol].setStyle("-fx-background-color: red");
                    btnMatrix[bombRow][bombCol].setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("bomb.png")))));
                    NewGame.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("skull.png")))));

                    if(currentMineField.matrix[i][j].hasMine){
                        btnMatrix[i][j].setBackground(Background.EMPTY);
                        btnMatrix[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("bomb.png")))));
                    }
                }
                if(currentMineField.matrix[i][j].marked){
                    btnMatrix[i][j].setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("flag.png")))));

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
                LoadGame.setText("Load Game");
                if(currentMineField.numCellsToExpose() != 0) {
                    if (event.isSecondaryButtonDown()) {
                        //System.out.println("Right Click");
                        if (!currentMineField.matrix[currRow][currCol].marked && !currentMineField.alreadyLost) {
                            currentMineField.matrix[currRow][currCol].marked = true;
                            //currentMineField.print();
                        } else {
                            currentMineField.matrix[currRow][currCol].marked = false;
                        }
                    }
                    if (event.isPrimaryButtonDown()) {

                        if(currentMineField.firstTurn[0] < 0 && currentMineField.firstTurn[1] < 0){
                            currentMineField.firstTurn[0] = currRow;
                            currentMineField.firstTurn[1] = currCol;
                            currentMineField.setMines();

                        }

                        if (!currentMineField.alreadyLost && !currentMineField.matrix[currRow][currCol].marked) {
                            int click = currentMineField.expose(currRow, currCol);
                            if (click == -1) {
                                bombRow = currRow;
                                bombCol = currCol;
                                //System.out.println("You Lose");
                            }

                            else if(click != -2) {
                                currentMineField.expose(currRow, currCol);
                                //currentMineField.print();
                                if (currentMineField.numCellsToExpose() == 0) {
                                    //System.out.println("You Win");
                                }
                            }

                        }
                    }
                }
                updateVIEW();
            }
        });
    }

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
        savedGame = null;

        runGame();

        SaveGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!currentMineField.alreadyLost) {
                    MineField copy = new MineField();
                    copy.level = getNewDiff();
                    copy.makeField();


                    copy.width = currentMineField.width;
                    copy.height = currentMineField.height;
                    copy.numOfMines = currentMineField.numOfMines;
                    copy.alreadyLost = currentMineField.alreadyLost;
                    copy.numExposedCells = currentMineField.numExposedCells;
                    copy.firstTurn[0] = currentMineField.firstTurn[0];
                    copy.firstTurn[1] = currentMineField.firstTurn[1];

                    for (int i = 0; i < currentMineField.height; i++) {
                        for (int j = 0; j < currentMineField.width; j++) {
                            copy.matrix[i][j].hasMine = currentMineField.matrix[i][j].hasMine;
                            copy.matrix[i][j].exposed = currentMineField.matrix[i][j].exposed;
                            copy.matrix[i][j].marked = currentMineField.matrix[i][j].marked;
                            copy.matrix[i][j].numSurroundingMines = currentMineField.matrix[i][j].numSurroundingMines;
                        }
                    }

                    savedGame = copy;
                    //savedGame.print();
                    //System.out.println("Saved Game");
                }
                else SaveGame.setText("Can't Save");
            }
        });
        LoadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (savedGame == null) {
                    LoadGame.setText("Can't Load");
                }
                else {
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
                    copy.firstTurn[0] = savedGame.firstTurn[0];
                    copy.firstTurn[1] = savedGame.firstTurn[1];

                    currentMineField = copy;
                    //currentMineField.print();
                    //System.out.println("Loaded Game");
                    NewGame.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(("smile.png")))));
                    updateVIEW();
                }
            }
        });

        Beginner.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level.setText("Easy");
            }
        });

        Intermediate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                level.setText("Medium");
            }
        });

        Expert.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                level.setText("Hard");
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
