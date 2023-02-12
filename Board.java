package tictactoe;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.MessageFormat;
import java.util.Random;

/**
 * This class will init the game board contained inside JFrame (TicTacToe).
 */
public class Board extends JPanel {

    private final int MAX_BOARD_SIZE = 3;
    private final String EMPTY_STRING = " ";
    private boolean gameEnd = false;
    public static boolean gameStarted = false;

    private static Player player1 = new Player("Human", 1);
    private static Player player2 = new Player("Human", 2);
    private Player currentPlayer;

    JLabel labelStatus = new JLabel(Status.GAME_NOT_STARTED.getValue());
    JButton resetButton = new JButton();
    static JButton player1Button = new JButton();
    static JButton player2Button = new JButton();
    Cell[] cells = new Cell[MAX_BOARD_SIZE*MAX_BOARD_SIZE];

    public Board() {

        /**
         * Create Board
         */
        setLayout(new BorderLayout());

        // Create Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 3, 10, 0));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 ,10));

        // Create player 1 button
        player1Button.setName("ButtonPlayer1");
        player1Button.setText("Human");
        player1Button.setForeground(Color.BLACK);
        player1Button.setOpaque(true);
        player1Button.setBorderPainted(true);
        player1Button.setBorder(new LineBorder(Color.BLACK));
        player1Button.setFocusPainted(false);
        player1Button.addActionListener(this::setPlayerType);
        player1Button.setEnabled(true);
        topPanel.add(player1Button);

        // Create Reset Button
        resetButton.setName("ButtonStartReset");
        resetButton.setText("Start");
        resetButton.addActionListener(this::startResetButtonEvent);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(Color.BLACK);
        resetButton.setOpaque(true);
        resetButton.setBorderPainted(false);
        resetButton.setFocusPainted(false);
        topPanel.add(resetButton);

        // Create player 2 button
        player2Button.setName("ButtonPlayer2");
        player2Button.setText("Human");
        player2Button.setOpaque(true);
        player2Button.setBorderPainted(true);
        player2Button.setFocusPainted(false);
        player2Button.setForeground(Color.BLACK);
        player2Button.setBorder(new LineBorder(Color.BLACK));
        player2Button.addActionListener(this::setPlayerType);
        player2Button.setEnabled(true);
        topPanel.add(player2Button);

        // Add top panel to frame
        add(topPanel, BorderLayout.PAGE_START);

        // Create Status Panel
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10 ,10));

        // Create Status Label
        labelStatus.setName("LabelStatus");
        statusPanel.add(labelStatus);
        statusPanel.add(Box.createHorizontalGlue());

        // Add status panel to frame
        add(statusPanel, BorderLayout.PAGE_END);

        // Create panel (game board) for cells
        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(MAX_BOARD_SIZE, MAX_BOARD_SIZE, 10, 10));
        gameBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10 ,10));

        // Add cells to game board
        for (int i = 0; i < MAX_BOARD_SIZE*MAX_BOARD_SIZE; i++) {
            int finalI = i;
            cells[i] = new Cell(CellNames.values()[i].toString());
            cells[i].addActionListener(event -> draw(cells[finalI]));
            cells[i].setEnabled(false);
            gameBoard.add(cells[i]);
        }

        // Add game board to main board
        add(gameBoard, BorderLayout.CENTER);

    }

    private void play() {
        switch(currentPlayer.getPlayerType()) {
            case "Robot":
                robotPlay();
                break;
            default:
                break;
        }
    }

    private void setCurrentPlayer() {
        currentPlayer = (currentPlayer.equals(player1)) ? player2 : player1;
    }

    private void robotPlay() {

        // Generate a random number 0-9 (exclusive)
        Random random = new Random();
        int randomNumber = random.nextInt(9);

        // Check random number against already marked positions until we get one that doesn't match
        while(GameScore.markedPositions.containsKey(randomNumber)) {
            randomNumber = random.nextInt(9);
        }

        // Get the random cell with that int
        Cell randomCell = cells[randomNumber];

        // Call draw and mark the cell
        draw(randomCell);
    }

    private void draw(Cell cell){

        // This will return false if:
        // - Cells are not empty
        // - Game is not started
        // - Game has ended
        // Else:
        // - Set text on cell ('O' or 'X')
        // - Update GameScore
        // - Call newRound
        // - return true

        if (!cell.getText().isBlank() || !gameStarted  || gameEnd) {
            return;
        }

        // Pass the if and setText on the cell and update game score list
        cell.setText(String.valueOf(currentPlayer.getSymbol()));
        GameScore.updateMarks(currentPlayer.getSymbol(), CellNames.valueOf(cell.getName()).ordinal());

        newRound();

    }

    private void winCheck() {
        switch (GameScore.winCheck()) {
            case "X", "O" -> {
                labelStatus.setText(playerWins());
                gameEnd = true;
            }
            case "Draw" -> {
                labelStatus.setText(Status.DRAW.getValue());
                gameEnd = true;
            }
        }
    }

    /**
     * This handles the event in clicking the Start/Reset button. If start game we run Start method.
     * If reset game we run reset method.
     * @param e
     */
    public void startResetButtonEvent(ActionEvent e) {
        if (labelStatus.getText().equals(Status.GAME_NOT_STARTED.getValue())) {
            startGame();
        } else {
            resetGame();
        }
    }

    public String messageCurrentPlayer() {
        return MessageFormat.format(
                "The turn of {0} Player ({1})", currentPlayer.getPlayerType(), currentPlayer.getSymbol()
        );
    }

    public String playerWins() {
        return MessageFormat.format(
                "The {0} Player ({1}) wins", currentPlayer.getPlayerType(), currentPlayer.getSymbol()
        );
    }

    public void startGame() {
        currentPlayer = player1;
        gameStarted = true;
        labelStatus.setText(messageCurrentPlayer());
        resetButton.setText("Reset");
        player1Button.setEnabled(false);
        player2Button.setEnabled(false);
        enableDisableButtons("Enable");
        play();
    }

    public void newRound() {
        winCheck();
        if (!gameEnd) {
            setCurrentPlayer();
            labelStatus.setText(messageCurrentPlayer());
            play();
        }
    }

    public void resetGame() {
        resetCells();
        labelStatus.setText(Status.GAME_NOT_STARTED.getValue());
        GameScore.markedPositions.clear();
        currentPlayer = player1;
        gameEnd = false;
        gameStarted = false;
        resetButton.setText("Start");
        player1Button.setEnabled(true);
        player2Button.setEnabled(true);
        enableDisableButtons("Disable");
    }

    public void resetCells() {
        for (int i = 0; i < cells.length; i++) {
            cells[i].setText(EMPTY_STRING);
        }
    }

    public void enableDisableButtons(String operation) {
        if (operation.equalsIgnoreCase("Disable")) {
            for (int i = 0; i < cells.length; i++) {
                cells[i].setEnabled(false);
            }
        } else {
            for (int i = 0; i < cells.length; i++) {
                cells[i].setEnabled(true);
            }
        }
    }

    public void setPlayerType(ActionEvent e) {
        if (e.getSource() == player1Button) {
            if (player1Button.getText().equals("Human")) {
                player1.setPlayerType("Robot");
                player1Button.setText("Robot");
            } else {
                player1.setPlayerType("Human");
                player1Button.setText("Human");
            }
        }

        if (e.getSource() == player2Button) {
            if (player2Button.getText().equals("Human")) {
                player2.setPlayerType("Robot");
                player2Button.setText("Robot");
            } else {
                player2.setPlayerType("Human");
                player2Button.setText("Human");
            }
        }
    }

    public void setPlayerType(String type) {

        switch (type) {
            case "MenuHumanHuman":
                player1.setPlayerType("Human");
                player2.setPlayerType("Human");
                player1Button.setText("Human");
                player2Button.setText("Human");
                break;
            case "MenuHumanRobot":
                player1.setPlayerType("Human");
                player2.setPlayerType("Robot");
                player1Button.setText("Human");
                player2Button.setText("Robot");
                break;
            case "MenuRobotHuman":
                player1.setPlayerType("Robot");
                player2.setPlayerType("Human");
                player1Button.setText("Robot");
                player2Button.setText("Human");
                break;
            case "MenuRobotRobot":
                player1.setPlayerType("Robot");
                player2.setPlayerType("Robot");
                player1Button.setText("Robot");
                player2Button.setText("Robot");
                break;
        }
    }
}
