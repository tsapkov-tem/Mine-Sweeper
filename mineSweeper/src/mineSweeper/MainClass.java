package mineSweeper;

import java.awt.*;
import java.util.LinkedList;

public class MainClass {
    public char[][] field;
    public char[][] fieldNow;
    public int heightFrame;
    public int wightFrame;
    public int unknownDots;
    private PaintField paintField;
    private final FieldCreate fieldCreate;
    private final LinkedList<Point> points;
    private final SingletonGetClass getClass;
    public int minesLeft;
    public int mines;
    public boolean looseGame;

    public MainClass(SingletonGetClass singletonGetClass){
        getClass = singletonGetClass;
        fieldCreate = getClass.getFieldCreate ();
        points = new LinkedList<>();
    }

    public PaintField createField(int height, int wight){
        fieldCreate.setField (height,wight);
        unknownDots = height*wight;
        heightFrame = height * 40 + 100;
        wightFrame = wight * 40 + 15;
        field = fieldCreate.getField ();
        initField ();
        mines = fieldCreate.mines;
        minesLeft = mines;
        paintField = getClass.getPaintField ();
        paintField.setField (fieldNow);
        return paintField;
    }

    public PaintField createField(){
        fieldCreate.setField ();
        field = fieldCreate.getField ();
        mines = fieldCreate.mines;
        unknownDots = field.length * field[0].length;
        heightFrame = field.length * 40 + 100;
        wightFrame = field[0].length * 40 + 15;
        initField ();
        paintField = getClass.getPaintField ();
        paintField.setField (fieldNow);
        return paintField;
    }

    public void openDot(int x, int y){
        if(fieldNow[y][x]!='f' && fieldNow[y][x] == 'u') {
            switch (field[y][x]) {
                case 'm' -> looseGame();
                case '0' -> {
                    fieldNow[y][x] = 'e';
                    unknownDots--;
                    openEmpty(x, y);
                }
                default -> {
                    fieldNow[y][x] = field[y][x];
                    unknownDots--;
                    checkForWin();
                }
            }
            paintField.setField (fieldNow);
        }
    }

    public void setFlag(int x, int y){
        if(fieldNow[y][x] == 'f'){
            fieldNow[y][x] = 'u';
            minesLeft++;
        }else if (fieldNow[y][x] == 'u'){
            fieldNow[y][x] = 'f';
            minesLeft--;
        }
        paintField.setField (fieldNow);
    }

    public void looseGame(){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if(field[i][j] == 'm'){
                    fieldNow[i][j]='m';
                    looseGame = true;
                }
            }
        }
    }

    public void checkForWin(){
        if (unknownDots - mines == 0){
            for (int i = 0; i < fieldNow.length; i++) {
                for (int j = 0; j < fieldNow[0].length; j++) {
                    if(fieldNow[i][j] == 'u'){
                        fieldNow[i][j]='f';
                    }
                }
            }
            paintField.winGame=true;
            paintField.setField (fieldNow);
        }
    }

    public void runBot(){
        getClass.getScriptForWin().setField(fieldNow);
        Point point;
        boolean scriptBroke = false;
        while (!paintField.winGame && !looseGame && !scriptBroke){
            point = getClass.getScriptForWin().getStep(fieldNow);
            try {
                openDot(point.x, point.y);
            }catch (NullPointerException e){
                System.out.println("The player's actions broke the script, or uncertainty");
                scriptBroke = true;
            }
        }
    }

    public void openEmpty(int x, int y){
        int xMax = field[0].length-1;
        int xMin = 0;
        int yMax = field.length-1;
        int yMin = 0;
        points.add (new Point (x, y));
        Point point;
        while (!points.isEmpty ()){
            point = points.poll ();
            x = point.x;
            y = point.y;
            if (y > yMin && x > xMin && fieldNow[y - 1][x - 1] == 'u' && fieldNow[y - 1][x - 1] != 'f') {
                if (field[y - 1][x - 1] == '0') {
                    fieldNow[y - 1][x - 1] = 'e';
                    points.add (new Point (x - 1, y - 1));
                } else {
                    fieldNow[y - 1][x - 1] = field[y - 1][x - 1];
                }
                unknownDots--;
            }
            if (y > yMin && fieldNow[y - 1][x] == 'u' && fieldNow[y - 1][x] != 'f') {
                if (field[y - 1][x] == '0') {
                    fieldNow[y - 1][x] = 'e';
                    points.add (new Point (x, y - 1));
                } else {
                    fieldNow[y - 1][x] = field[y - 1][x];
                }
                unknownDots--;
            }
            if (x > xMin && fieldNow[y][x - 1] == 'u' && fieldNow[y][x - 1] != 'f') {
                if (field[y][x - 1] == '0') {
                    fieldNow[y][x - 1] = 'e';
                    points.add (new Point (x - 1, y));
                } else {
                    fieldNow[y][x - 1] = field[y][x - 1];
                }
                unknownDots--;
            }
            if (y > yMin && x < xMax && fieldNow[y - 1][x + 1] == 'u' && fieldNow[y - 1][x + 1] != 'f') {
                if (field[y - 1][x + 1] == '0') {
                    fieldNow[y - 1][x + 1] = 'e';
                    points.add (new Point (x + 1, y - 1));
                } else {
                    fieldNow[y - 1][x + 1] = field[y - 1][x + 1];
                }
                unknownDots--;
            }
            if (y < yMax && x > xMin && fieldNow[y + 1][x - 1] == 'u' && fieldNow[y + 1][x - 1] != 'f') {
                if (field[y + 1][x - 1] == '0') {
                    fieldNow[y + 1][x - 1] = 'e';
                    points.add (new Point (x - 1, y + 1));
                } else {
                    fieldNow[y + 1][x - 1] = field[y + 1][x - 1];
                }
                unknownDots--;
            }
            if (y < yMax && fieldNow[y + 1][x] == 'u' && fieldNow[y + 1][x] != 'f') {
                if (field[y + 1][x] == '0') {
                    fieldNow[y + 1][x] = 'e';
                    points.add (new Point (x, y + 1));
                } else {
                    fieldNow[y + 1][x] = field[y + 1][x];
                }
                unknownDots--;
            }
            if (x < xMax && fieldNow[y][x + 1] == 'u' && fieldNow[y][x + 1] != 'f') {
                if (field[y][x + 1] == '0') {
                    fieldNow[y][x + 1] = 'e';
                    points.add (new Point (x + 1, y));
                } else {
                    fieldNow[y][x + 1] = field[y][x + 1];
                }
                unknownDots--;
            }
            if (y < yMax && x < xMax && fieldNow[y + 1][x + 1] == 'u' && fieldNow[y + 1][x + 1] != 'f') {
                if (field[y + 1][x + 1] == '0') {
                    fieldNow[y + 1][x + 1] = 'e';
                    points.add (new Point (x + 1, y + 1));
                } else {
                    fieldNow[y + 1][x + 1] = field[y + 1][x + 1];
                }
                unknownDots--;
            }
        }
        checkForWin();
    }


    public void initField(){
        fieldNow = new char[field.length][field[0].length];
        for (int i = 0; i < fieldNow.length; i++) {
            for (int j = 0; j < fieldNow[0].length; j++) {
                fieldNow[i][j] = 'u';
                looseGame=false;
            }
        }
    }
}
