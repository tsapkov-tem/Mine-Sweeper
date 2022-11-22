package test.mineSweeper; 

import mineSweeper.MainClass;
import mineSweeper.SingletonGetClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MainClassTest{
    MainClass mainClass = new MainClass(new SingletonGetClass());

    @Test
    public void testLooseGame(){
        mainClass.createField(8,8);
        mainClass.initField();
        mainClass.looseGame();
        for (int y = 0; y < mainClass.field.length; y++) {
            for (int x = 0; x < mainClass.field[0].length; x++) {
                if(mainClass.field[y][x] == 'm'){
                    assertEquals('m', mainClass.fieldNow[y][x]);
                }
            }
        }
    }

    @Test
    public void testCheckForWin() {
        mainClass.createField(8,8);
        mainClass.initField();
        for (int y = 0; y < mainClass.field.length; y++) {
            for (int x = 0; x < mainClass.field[0].length; x++) {
                if(mainClass.field[y][x] != 'm'){
                    mainClass.fieldNow[y][x] = 'e';
                    mainClass.unknownDots--;
                }
            }
        }
        mainClass.checkForWin();
        for (int y = 0; y < mainClass.field.length; y++) {
            for (int x = 0; x < mainClass.field[0].length; x++) {
                if(mainClass.field[y][x] == 'm'){
                    assertEquals('f',mainClass.fieldNow[y][x]);
                }
            }
        }
    }

    @Test
    public void testOpenEmpty() {
        mainClass.createField(8,8);
        mainClass.initField();
        int unknownDots = mainClass.unknownDots-1;
        for (int y = 0; y < mainClass.field.length; y++) {
            for (int x = 0; x < mainClass.field[0].length; x++) {
                if(mainClass.field[y][x] == '0'){
                    mainClass.openEmpty(x,y);
                    break;
                }
            }
        }
        assertNotEquals(unknownDots, mainClass.unknownDots);
    }
} 
