package tictactoe;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Menu extends JMenuBar {

    private Board board;

    public Menu(Board board) {

        this.board = board;

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.setName("MenuGame");
        JMenuItem humanVsHuman = new JMenuItem("Human vs Human");
        humanVsHuman.setName("MenuHumanHuman");
        JMenuItem humanVsRobot = new JMenuItem("Human vs Robot");
        humanVsRobot.setName("MenuHumanRobot");
        JMenuItem robotVsHuman = new JMenuItem("Robot vs Human");
        robotVsHuman.setName("MenuRobotHuman");
        JMenuItem robotVsRobot = new JMenuItem("Robot vs Robot");
        robotVsRobot.setName("MenuRobotRobot");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        exitMenuItem.addActionListener(event -> System.exit(0));
        humanVsHuman.addActionListener(event -> setPlayerType(humanVsHuman));
        humanVsRobot.addActionListener(event -> setPlayerType(humanVsRobot));
        robotVsHuman.addActionListener(event -> setPlayerType(robotVsHuman));
        robotVsRobot.addActionListener(event -> setPlayerType(robotVsRobot));

        fileMenu.add(humanVsHuman);
        fileMenu.add(humanVsRobot);
        fileMenu.add(robotVsRobot);
        fileMenu.add(robotVsHuman);
        fileMenu.add(robotVsRobot);

        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        add(fileMenu);

    }

    private void setPlayerType(JMenuItem menuItem) {
        board.setPlayerType(menuItem.getName());
        board.startGame();
    }
}
