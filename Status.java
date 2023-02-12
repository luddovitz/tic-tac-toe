package tictactoe;

public enum Status {

    GAME_NOT_STARTED("Game is not started"),
    GAME_IN_PROGRESS("Game in progress"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    DRAW("Draw");

    private final String status;

    public String getValue() {
        return status;
    }

    Status(String status) {
        this.status = status;
    }
}
