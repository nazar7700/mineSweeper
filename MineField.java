package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MineField extends Application {

    private int width;
    private int height;
    private int numOfMines;
    Cell[][] matrix;


    private class Cell {
        boolean hasMine;
        boolean exposed;
        boolean marked;
        int numSurroundingMines;
    }

    private Cell[][] makeField(int difficulty){

        if (difficulty == 1){
            height = 9;
            width = 9;
            numOfMines = 10;
        }
        if (difficulty == 2){
            height = 16;
            width = 16;
            numOfMines = 40;
        }
        if (difficulty == 3){
            height = 16;
            width = 30;
            numOfMines = 99;
        }

        matrix = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width ; j++) {
                Cell cell = new Cell();
                cell.exposed = false;
                cell.hasMine = false;
                cell.marked = false;
                cell.numSurroundingMines = 0;
                matrix[i][j]=cell;
            }

        }


        return matrix;
    }

    private static MineField setMines(MineField mineField){
        int w = mineField.width;
        int h = mineField.height;
        int n = w*h;
        int m = mineField.numOfMines;
        int r, c;

        for(r = 0; r < h; r++){
            for(c = 0; c < w; c++) {
                double p = (double) m / (double) n; // probability of placing mine here
                double g = Math.random();
                if (g < p) {
                    mineField.matrix[r][c].hasMine = true;
                    m--;
                }
                n--;
            }
        }
        mineField.numOfMines -= m; // numOfMines = 0

        for (r = 0; r < h; r++){
            for(c = 0; c < w; c++){
                int i,j, count = 0;
                Cell currentCell = mineField.matrix[r][c];
                for(j = -1; j <= +1; j++){
                    for(i = -1; i <= +1; i++){
                        if (i == 0 && j == 0) continue;
                        int rr = r+j, cc = c+i;
                        if(rr < 0 || rr >= h || cc < 0 || cc >= w) continue;
                        Cell neighbor = mineField.matrix[rr][cc];
                        if(neighbor.hasMine) count++;
                    }
                }
                currentCell.numSurroundingMines = count;
            }
        }


        return mineField;
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

        int difficulty = 1;


        newMineField.makeField(difficulty);

        for (int i = 0; i < newMineField.height; i++) {
            for (int j = 0; j < newMineField.width; j++) {
                System.out.print("[ ] ");
            }
            System.out.println();

        }

        newMineField = setMines(newMineField);

        System.out.println();
        int mines = 0;

        for (int i = 0; i < newMineField.height; i++) {
            for (int j = 0; j < newMineField.width; j++) {
                if(newMineField.matrix[i][j].hasMine == true){
                    System.out.print("[*] ");
                    mines ++;
                }
                else if(newMineField.matrix[i][j].numSurroundingMines > 0){
                    System.out.print("["+newMineField.matrix[i][j].numSurroundingMines +"] ");
                }
                else System.out.print("[ ] ");
            }
            System.out.println();

        }
        System.out.println("Mines: " + mines);


    }
}
