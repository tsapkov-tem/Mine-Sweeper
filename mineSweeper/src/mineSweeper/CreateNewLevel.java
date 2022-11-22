package mineSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;

public class CreateNewLevel extends JPanel {
    private char[][] field;
    private final LinkedList<Point> points;

    public int heightFrame;
    public int wightFrame;

    private int height;
    private int wight;

    private Image unknown;
    private Image bomb;

    public CreateNewLevel(){
        height = 8;
        wight = 8;
        points = new LinkedList<>();
        loadIcon();
        initField();
        activateActionListener();
    }

    public void activateActionListener(){
        addMouseListener (new MouseAdapter() {
            int mouseX;
            int mouseY;
            int x;
            int y;
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getX () > 80 && e.getX() < 105 && e.getY ()>5 && e.getY()<30 && wight<16) {
                        height++;
                        wight++;
                        initField();
                    }
                    if (e.getX () > 110 && e.getX() < 135 && e.getY ()>5 && e.getY()<30 && wight>5) {
                        height--;
                        wight--;
                        initField();
                    }
                    if (e.getX () > 145 && e.getX() < 220 && e.getY ()>5 && e.getY()<30 && wight>5) {
                        save();
                    }
                }
                mouseX = e.getX (); //получаем координаты мыши
                mouseY = (e.getY () - 39);
                if (mouseY>0) {
                    x = mouseX / 40; // присваиваем их
                    y = mouseY / 40;
                    if (x >= 0 && y < field.length && x < field[0].length) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            setBomb(x, y);
                        }
                    }
                }
            }
        });
    }
    public void loadIcon(){
        bomb = new ImageIcon ("src/mineSweeper/icons/bomb.jpg").getImage ();
        unknown = new ImageIcon ("src/mineSweeper/icons/unknown.jpg").getImage ();
    }

    public void initField(){
        heightFrame = height * 40 + 100;
        wightFrame = wight * 40 + 15;
        Window.frame.setSize(wightFrame,heightFrame);
        field = new char[height][wight];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < wight; j++) {
                field[i][j] = 'u';
            }
        }
        repaint();
    }

    public void setBomb(int x, int y){
        if(field[y][x] == 'm') {
            field[y][x] = 'u';
        }else {
            field[y][x] = 'm';
        }
        repaint();
    }

    public void save(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < wight; j++) {
                if (field[i][j] == 'm'){
                    points.add(new Point(j,i));
                }
            }
        }
        try {
            FileWriter writer = new FileWriter("src/mineSweeper/Levels/CreatedLevel.txt", false);
            writer.write(height + " ");
            writer.write(Integer.toString(wight));
            writer.write('\n');
            writer.write(Integer.toString(points.size()));
            writer.write('\n');
            Point point;
            while (!points.isEmpty()){
                point = points.poll();
                writer.write(point.x + " ");
                writer.write(Integer.toString(point.y));
                writer.write('\n');
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Save");
    }

    @Override
    public void paint(Graphics g) //графический метод прорисовки
    {
        super.paint (g);
        g.setFont (new Font ("font",  Font.BOLD, 30));
        g.setColor (Color.black);
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                if (field[y][x] == 'm') {
                    g.drawImage(bomb, x * 40, y * 40 + 39, this);
                } else {
                    g.drawImage(unknown, x * 40, y * 40 + 39, this);
                }
            }
        }
        g.drawString("Size", 10,25);
        g.drawRect(80,5,25,25);
        g.drawString("+", 85,28);
        g.drawRect(110,5,25,25);
        g.drawString("-", 115,25);
        g.drawString("save", 150, 25);
        g.drawRect(145,5,75,25);
    }

}
