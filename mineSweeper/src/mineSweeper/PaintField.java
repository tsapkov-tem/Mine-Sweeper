package mineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PaintField extends JPanel{
    private char[][] field;
    private Image bomb;
    private Image empty;
    private Image flag;
    private Image unknown;
    MainClass mainClass;
    int maxX;
    int maxY;
    boolean listenerEnable;
    boolean winGame = false;

    PaintField(SingletonGetClass SingletonGetClass){
        mainClass = SingletonGetClass.getMainClass ();
        loadIcon();
        activateListeners();
    }

    public void setField(char[][] field){
        this.field = field;
        maxX = field[0].length;
        maxY = field.length;
        listenerEnable=true;
        repaint ();
    }

    public void loadIcon(){
        bomb = new ImageIcon ("src/mineSweeper/icons/bomb.jpg").getImage ();
        empty = new ImageIcon ("src/mineSweeper/icons/empty.jpg").getImage ();
        flag = new ImageIcon ("src/mineSweeper/icons/flag.jpg").getImage ();
        unknown = new ImageIcon ("src/mineSweeper/icons/unknown.jpg").getImage ();
    }

    public void activateListeners(){
        listenerEnable = true;
        addMouseListener (new MouseAdapter () {
            int mouseX;
            int mouseY;
            int x;
            int y;
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listenerEnable) {
                    mouseX = e.getX (); //получаем координаты мыши
                    mouseY = (e.getY () - 39);
                    if (mouseY > 0) {
                        x = mouseX / 40; // присваиваем их
                        y = mouseY / 40;
                        if (x >= 0 && y < field.length && x < field[0].length) {
                            if (e.getButton () == MouseEvent.BUTTON1) {
                                mainClass.openDot (x, y);
                            }
                            if (e.getButton () == MouseEvent.BUTTON3) {
                                mainClass.setFlag (x, y);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) //графический метод прорисовки
    {
        super.paint (g);
        boolean looseGame = false;
        g.setFont (new Font ("font",  Font.BOLD, 20));
        g.setColor (Color.red);
        g.drawString ("Bombs: " + mainClass.minesLeft, 10, 20);
        if(winGame){
            g.drawString ("You win!", 130, 20);
            listenerEnable = false;
        }
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                switch (field[y][x]) {
                    case 'u' -> g.drawImage(unknown, x * 40, y * 40 + 39, this);
                    case 'e' -> g.drawImage(empty, x * 40, y * 40 + 39, this);
                    case 'm' -> {
                        g.drawImage(bomb, x * 40, y * 40 + 39, this);
                        looseGame = true;
                    }
                    case 'f' -> g.drawImage(flag, x * 40, y * 40 + 39, this);
                    default -> {
                        g.drawImage(empty, x * 40, y * 40 + 39, this);
                        g.drawString(String.valueOf(field[y][x]), x * 40 + 12, y * 40 + 62);
                    }
                }
            }
        }
        if(looseGame){
            g.drawString ("You loose", 130, 20);
            listenerEnable = false;
        }
        winGame=false;
    }
}
