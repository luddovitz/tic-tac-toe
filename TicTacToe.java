package tictactoe;

import javax.swing.*;

public class TicTacToe extends JFrame {

    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Tic Tac Toe");

        //Create board
        Board board = new Board();
        add(board);

        //Create menu and pass board to constructor
        Menu menu = new Menu(board);
        setJMenuBar(menu);

        setVisible(true);
    }
}