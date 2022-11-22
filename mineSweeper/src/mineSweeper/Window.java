package mineSweeper;

import javax.swing.*;

public class Window extends JFrame {
    JPanel panel;
    int height = 8;
    int width = 8;
    public final static JFrame frame = new JFrame ("Сапёр");
    Window(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //меню запуска
        JMenuBar menuBar = new JMenuBar();
        JMenu GameMenu = new JMenu("Game");
        menuBar.add(GameMenu);
        JMenuItem runItem = new JMenuItem("Run new game"); //обычная игра
        GameMenu.add(runItem);
        JMenuItem botItem = new JMenuItem("Bot Run"); //игра бота
        GameMenu.add(botItem);

        SingletonGetClass getClass = new SingletonGetClass ();

        panel = getClass.getMainClass ().createField (height,width);
        frame.setSize (getClass.getMainClass ().wightFrame, getClass.getMainClass ().heightFrame);
        frame.add (panel);

        //запуск игры
        runItem.addActionListener(e -> {
            frame.remove(panel);
            frame.repaint();
            panel = getClass.getMainClass ().createField (height,width);
            frame.setSize (getClass.getMainClass ().wightFrame, getClass.getMainClass ().heightFrame);
            frame.add (panel);
        });
        //запуск игры бота
        botItem.addActionListener(e -> {
            if(panel != null) {
                getClass.getMainClass().runBot();
            }
        });

        //меню уровня
        JMenu LevelMenu = new JMenu("Level"); //меню создания уровня
        menuBar.add(LevelMenu); //менюбар для выбора
        JMenuItem addSize = new JMenuItem ("Add size");
        JMenuItem reduceSize = new JMenuItem ("Reduce size");
        JMenuItem newLevel = new JMenuItem("Created level"); //уровень созданный
        JMenuItem CreateLevel = new JMenuItem("Create new level"); //создание нового уровня
        LevelMenu.addSeparator(); //разделяем на отдельные кнопки
        LevelMenu.add (addSize);
        LevelMenu.add (reduceSize);
        LevelMenu.add(newLevel); //связь кнопок и менюбара
        LevelMenu.add(CreateLevel);

        addSize.addActionListener(e -> {
            if (height < 18  && width < 18) {
                frame.remove(panel);
                height+=3;
                width+= 3;
                panel = getClass.getMainClass ().createField (height,width);
                frame.setSize (getClass.getMainClass ().wightFrame, getClass.getMainClass ().heightFrame);
                frame.add (panel);
            }
        });

        reduceSize.addActionListener(e -> {
            if (height > 8 && width > 8) {
                frame.remove(panel);
                height-=3;
                width-= 3;
                panel = getClass.getMainClass ().createField (height,width);
                frame.setSize (getClass.getMainClass ().wightFrame, getClass.getMainClass ().heightFrame);
                frame.add (panel);
            }
        });

        newLevel.addActionListener(e -> {
            frame.remove(panel);
            frame.repaint();
            panel = getClass.getMainClass ().createField ();
            frame.setSize (getClass.getMainClass().wightFrame, getClass.getMainClass().heightFrame);
            frame.add (panel);
            frame.validate();
        });

        CreateLevel.addActionListener(e -> {
            frame.remove(panel);
            frame.repaint();
            panel = getClass.getCreateNewLevel();
            frame.setSize (getClass.getMainClass().wightFrame, getClass.getMainClass().heightFrame);
            frame.add (panel);
            frame.validate();
        });


        frame.setJMenuBar(menuBar); //добавляем менюбар на фрейм
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Window ();
    }
}