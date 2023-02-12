package tictactoe;

import javax.swing.*;
import java.awt.*;

public class Cell extends JButton {

    public Cell(String name) {
        setName(name);
        setText(" ");
        setBackground(Color.orange);
        setOpaque(true);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 64));
        setFocusPainted(false);
    }
}
