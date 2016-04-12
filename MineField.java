package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

<<<<<<< HEAD

public class MineField extends Application {

=======
import java.util.Scanner;


public class MineField extends Application {

    Cell[][] matrix;
    private int width;
    private int height;
    private int numOfMines;
    private boolean alreadyLost;
    private int numExposedCells;


>>>>>>> 59320860db09db8f82230288ac4470e58f6e8790
    private class Cell {
        boolean hasMine;
        boolean exposed;
        boolean marked;
<<<<<<< HEAD
        char numSurroundingMines;
    }

    private Cell[][] mineField(int row, int col){
        Cell[][] newMineField = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col ; j++) {
=======
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
>>>>>>> 59320860db09db8f82230288ac4470e58f6e8790
                Cell cell = new Cell();
                cell.exposed = false;
                cell.hasMine = false;
                cell.marked = false;
                cell.numSurroundingMines = 0;
<<<<<<< HEAD
                newMineField[i][j]=cell;
            }

        }
        return newMineField;
=======
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
>>>>>>> 59320860db09db8f82230288ac4470e58f6e8790
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

<<<<<<< HEAD
    public int expose(int column, int row){

        return 0;
=======
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
        if (n == 0){
            int w = width;
            int h = height;
            boolean changed = true;
            while(changed){
                int rr, cc;
                changed = false;
                for(rr = 0; rr < h; rr++){
                    for(cc = 0; cc < w; cc++){
                        if(autoExposeCellAt(rr, cc))
                            changed = true;
                    }
                }
            }
        }
        return n;
    }
    public boolean autoExposeCellAt(int row, int col){
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
                    if(neighbor.exposed && neighbor.numSurroundingMines == 0){
                        cell.exposed = true;
                        numExposedCells++;
                        return true;
                    }
                }
            }
        }
        return false;
>>>>>>> 59320860db09db8f82230288ac4470e58f6e8790
    }

    public int isExposed(int column, int row){
        return 0;
    }

    public int unexposedCount(){
        return 0;
    }

<<<<<<< HEAD
=======
    public void print(){
        System.out.print("   ");
        for (int i = 0; i < width; i++) {
            if(i > 9){
                System.out.print(i+"  ");
            }
            else
            System.out.print(i+ "   ");
        }
        System.out.println();

        for (int i = 0; i < height; i++) {
            if(i > 9){
                System.out.print(i+" ");
            }else System.out.print(i+"  ");
            for (int j = 0; j < width; j++) {
                if(matrix[i][j].hasMine == true){
                    System.out.print("[*] ");
                }
                else if(matrix[i][j].exposed == true){
                    System.out.print("[O] ");
                }
                else System.out.print("[ ] ");
            }
            System.out.println();

        }
    }

>>>>>>> 59320860db09db8f82230288ac4470e58f6e8790


    public static void main(String[] args) {
        MineField newMineField = new MineField();

<<<<<<< HEAD
        int row = 5;
        int col = 5;
        newMineField.mineField( row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print("[] ");
            }
            System.out.println();

=======
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Choose Difficulty (1 2 3): ");
        int difficulty = reader.nextInt(); // Scans the next token of the input as an int.

        newMineField.makeField(difficulty);

        newMineField.setMines(newMineField);

        newMineField.print();

        System.out.println();
        while(!newMineField.alreadyLost && newMineField.numExposedCells + newMineField.numOfMines != newMineField.height*newMineField.width ) {
            reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter row col: ");
            int row = reader.nextInt(); // Scans the next token of the input as an int.
            int col = reader.nextInt();

            newMineField.expose(row, col);
            System.out.println("Exposed Cells: "+ newMineField.numExposedCells);
            System.out.println("Mines: "+ newMineField.numOfMines);

            newMineField.print();
        }

        if(newMineField.alreadyLost){
            System.out.println();
            System.out.println("--------------YOU LOST--------------");
        }
        else{
            System.out.println();
            System.out.println("--------------YOU WON--------------");
>>>>>>> 59320860db09db8f82230288ac4470e58f6e8790
        }

    }
}
