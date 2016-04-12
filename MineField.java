package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MineField extends Application {

    private class Cell {
        boolean hasMine;
        boolean exposed;
        boolean marked;
        char numSurroundingMines;
    }

    private Cell[][] mineField(int row, int col){
        Cell[][] newMineField = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col ; j++) {
                Cell cell = new Cell();
                cell.exposed = false;
                cell.hasMine = false;
                cell.marked = false;
                cell.numSurroundingMines = 0;
                newMineField[i][j]=cell;
            }

        }
        return newMineField;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("minesweeper.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public void reset(){


    }


    public boolean mark(int column, int row){

        return false;
    }

    public int expose(int column, int row){

        return 0;
    }

    public int isExposed(int column, int row){
        return 0;
    }

    public int unexposedCount(){
        return 0;
    }



    public static void main(String[] args) {
        MineField newMineField = new MineField();

        int row = 5;
        int col = 5;
        newMineField.mineField( row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("[] ");
            }
            System.out.println();

        }

    }
}
