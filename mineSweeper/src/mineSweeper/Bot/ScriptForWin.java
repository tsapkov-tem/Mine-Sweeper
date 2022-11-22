package mineSweeper.Bot;

import java.awt.*;
import java.util.*;

public class ScriptForWin {
    private char[][] field;

    private LinkedList<Point> steps;
    private ArrayList<CheckingDot> checkingDots;
    private ArrayList<Point> unknownPoints;
    private int[][] numbersField;
    private boolean updateField;

    public void setField(char[][] field) {
        checkingDots = new ArrayList<>();
        unknownPoints = new ArrayList<>();
        steps = new LinkedList<>();
        this.field = field;
        setNumbers();
    }

    public void setNumbers(){
        numbersField = new int[field.length][field[0].length];
        int number = 0;
        for (int y = 0; y < numbersField.length; y++) {
            for (int x = 0; x < numbersField[0].length; x++) {
                numbersField[y][x] = number;
                unknownPoints.add(new Point(x,y));
                number++;
            }
        }
    }

    public Point getStep(char[][] getterField){
        Point point;
        if (updateField){
            field=getterField;
            findSafeDots();
            updateField=false;
        }
        if (steps.isEmpty()){
            if (field[0][0] == 'u'){
                updateField=true;
                return new Point(0,0);
            }
            if (field[field.length-1][0] == 'u'){
                updateField=true;
                return new Point(0, field.length-1);
            }
            if (field[0][field[0].length-1] == 'u'){
                updateField=true;
                return new Point(field[0].length-1, 0);
            }
            if (field[field.length-1][field[0].length-1] == 'u'){
                updateField=true;
                return new Point(field[0].length-1, field.length-1);
            }
        }
        if (steps.size() == 1){
            updateField=true;
            return steps.poll();
        }else{
            point = steps.poll();
            return point;
        }
    }

    public void getGroups(){
        checkingDots.clear();
        int yMin = 0;
        int xMin = 0;
        int xMax = field[0].length-1;
        int yMax = field.length-1;
        int index = 0;
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                if (field[y][x] != 'u' && field[y][x] != 'e'){

                    checkingDots.add(new CheckingDot());
                    checkingDots.get(index).point = new Point(x,y);
                    checkingDots.get(index).minesInThisGroup = field[y][x] - '0';

                    if (y > yMin && x > xMin && field[y - 1][x - 1] == 'u'){
                        checkingDots.get(index).group.add(numbersField[y - 1][x - 1]);
                    }
                    if (y > yMin && field[y - 1][x] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y - 1][x]);
                    }
                    if (x > xMin && field[y][x - 1] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y][x - 1]);
                    }
                    if (y > yMin && x < xMax && field[y - 1][x + 1] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y - 1][x + 1]);
                    }
                    if (x > xMin && y < yMax && field[y + 1][x - 1] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y + 1][x - 1]);
                    }
                    if (y < yMax && field[y + 1][x] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y + 1][x]);
                    }
                    if (x < xMax &&  field[y][x + 1] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y][x + 1]);
                    }
                    if (x < xMax && y < yMax && field[y + 1][x + 1] == 'u') {
                        checkingDots.get(index).group.add(numbersField[y + 1][x + 1]);
                    }
                    index++;
                }
            }
        }
        updateGroups();
    }

    public void updateGroups(){
        boolean repeat = true;
        while (repeat) {
            repeat = false;
                for (int i = 0; i < checkingDots.size() - 1; i++) {
                    for (int j = i + 1; j < checkingDots.size(); j++) {
                        if (checkingDots.get(i).group.equals(checkingDots.get(j).group)) {
                            checkingDots.remove(j);
                            break;
                        }
                        if (checkingDots.get(i).group.size() > checkingDots.get(j).group.size()) {
                            repeat = isRepeat(repeat, i, j);
                        } else {
                            repeat = isRepeat(repeat, j, i);
                        }
                    }
                }
            }
    }

    private boolean isRepeat(boolean repeat, int i, int j) {
        if (checkingDots.get(i).group.containsAll(checkingDots.get(j).group)) {
            checkingDots.get(i).group.removeAll(checkingDots.get(j).group);
            checkingDots.get(i).minesInThisGroup = checkingDots.get(i).minesInThisGroup - checkingDots.get(j).minesInThisGroup;
            repeat = true;
        }
        return repeat;
    }

    public void findSafeDots(){
        getGroups();
        HashSet<Integer> set = new HashSet<>();
            for (CheckingDot checkingDot : checkingDots) {
                if (checkingDot.minesInThisGroup <= 0) {
                    set.addAll(checkingDot.group);
                }
            }
            for (int index : set) {
                steps.add(unknownPoints.get(index));
            }
    }
}
