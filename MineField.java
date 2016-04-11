package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MineField extends Application {

    Cell[][] matrix;
    private int width;
    private int height;
    private int numOfMines;
    private boolean alreadyLost;
    private int numExposedCells;


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

        numExposedCells = 0;
        alreadyLost = false;

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

    private MineField setMines(MineField mineField){
        int w = width;
        int h = height;
        int n = w*h;
        int m = numOfMines;
        int r, c;

        for(r = 0; r < h; r++){
            for(c = 0; c < w; c++) {
                double p = (double) m / (double) n; // probability of placing mine here
                double g = Math.random();
                if (g < p) {
                    matrix[r][c].hasMine = true;
                    m--;
                }
                n--;
            }
        }
        numOfMines -= m; // numOfMines = 0

        for (r = 0; r < h; r++){
            for(c = 0; c < w; c++){
                int i,j, count = 0;
                Cell currentCell = matrix[r][c];
                for(j = -1; j <= +1; j++){
                    for(i = -1; i <= +1; i++){
                        if (i == 0 && j == 0) continue;
                        int rr = r+j, cc = c+i;
                        if(rr < 0 || rr >= h || cc < 0 || cc >= w) continue;
                        Cell neighbor = matrix[rr][cc];
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

    public int expose(int row, int col){
        if(alreadyLost) return -2;
        Cell cell = matrix[row][col];
        if(matrix[row][col].exposed) return -2;
        cell.exposed = true;
        numExposedCells++;
        if(cell.hasMine){
            alreadyLost = true;
            return -1;
        }
        int n = cell.numSurroundingMines;
        System.out.println("numSurroundingMines = " + n);
        if (n == 0){
            int w = width;
            int h = height;
            boolean changed = true;
            //do{
            while(changed){
                //System.out.println("Entered while loop");
                int rr, cc;
                changed = false;
                for(rr = 0; rr < h; rr++){
                    for(cc = 0; cc < w; cc++){
                        //System.out.println("Entered Second for Loop");
                        if(autoExposeCellAt(rr, cc))
                            changed = true;
                    }
                }
            } //while(changed);
        }
        return n;
    }
    public boolean autoExposeCellAt(int row, int col){
        //System.out.println("Entered autoExpose");
        Cell cell = matrix[row][col];
        if(!cell.exposed && !cell.hasMine){
            int w = width;
            int h = height;
            int i, j;
            for(j = -1; j <= +1; j++){
                for(i = -1; i <= +1; i++){
                    if (i == 0 && j == 0) continue;
                    int rr = row+j;
                    int cc = col+i;
                    if(rr < 0 || rr >= h || cc < 0 || cc >= w) continue;
                    Cell neighbor = matrix[rr][cc];
                    //System.out.println("Reached Here");
                    if(neighbor.exposed && neighbor.numSurroundingMines == 0){
                        cell.exposed = true;
                        numExposedCells++;
                        return true;
                    }
                }
            }
        }
        return false;
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

//        for (int i = 0; i < newMineField.height; i++) {
//            for (int j = 0; j < newMineField.width; j++) {
//                System.out.print("[ ] ");
//            }
//            System.out.println();
//
//        }
//        System.out.println();

        newMineField.setMines(newMineField);

        int mines = 0;

        for (int i = 0; i < newMineField.height; i++) {
            for (int j = 0; j < newMineField.width; j++) {
                if(newMineField.matrix[i][j].hasMine == true){
                    System.out.print("[*] ");
                    mines ++;
                }
//                else if(newMineField.matrix[i][j].numSurroundingMines > 0){
//                    System.out.print("["+newMineField.matrix[i][j].numSurroundingMines +"] ");
//                }
                else System.out.print("[ ] ");
            }
            System.out.println();

        }
        //System.out.println("Mines: " + mines);
        System.out.println();

        int expRow = 1;
        int expCol = 1;
        newMineField.expose(expRow, expCol);

        for (int i = 0; i < newMineField.height; i++) {
            for (int j = 0; j < newMineField.width; j++) {
                if(newMineField.matrix[i][j].hasMine == true){
                    System.out.print("[*] ");
                    mines ++;
                }
                else if(newMineField.matrix[i][j].exposed == true){
                    System.out.print("[O] ");
                }
                else System.out.print("[ ] ");
            }
            System.out.println();

        }

    }
}
