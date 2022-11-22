package mineSweeper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class FieldCreate {
    private char[][] field;
    int mines;

    public void setField() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader ("src/mineSweeper/Levels/CreatedLevel.txt");
        } catch (FileNotFoundException e) {
            System.out.println ("File doesn't exist!");
        }
        assert fileReader != null;
        Scanner scanner = new Scanner (fileReader);
        int height = scanner.nextInt ();
        int width = scanner.nextInt ();
        field = new char[height][width];
        mines = scanner.nextInt ();
        int x;
        int y;
        for (int i = 0; i < mines; i++) {
            x = scanner.nextInt ();
            y = scanner.nextInt ();
            field[y][x] = 'm';
        }
        putNumbers ();
    }

    public void setField(int height, int width){
        field = new char[height][width];
        int numberOfMines = width * height / 8;
        Random random = new Random ();
        mines = 0;
        int x;
        int y;
        while (mines<numberOfMines){
            x = random.nextInt (width);
            y = random.nextInt (height);
            if (field[y][x] != 'm'){
                field[y][x] = 'm';
                mines++;
            }
        }
        putNumbers ();
    }

    public void putNumbers(){
        int minesAround;
        int xMax = field[0].length-1;
        int xMin = 0;
        int yMax = field.length-1;
        int yMin = 0;
        for  (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                if(field[y][x] != 'm') {
                    minesAround = 0;
                    if (y > yMin && x > xMin && field[y - 1][x - 1] == 'm') minesAround++;
                    if (y > yMin && field[y - 1][x] == 'm') minesAround++;
                    if (x > xMin && field[y][x - 1] == 'm') minesAround++;
                    if (y > yMin && x < xMax && field[y - 1][x + 1] == 'm') minesAround++;
                    if (y < yMax && x > xMin && field[y + 1][x - 1] == 'm') minesAround++;
                    if (y < yMax && field[y + 1][x] == 'm') minesAround++;
                    if (x < xMax && field[y][x + 1] == 'm') minesAround++;
                    if (y < yMax && x < xMax && field[y + 1][x + 1] == 'm') minesAround++;
                    field[y][x] = Integer.toString (minesAround).charAt (0);
                }
            }
        }
    }

    public char[][] getField() {
        return field;
    }
}

