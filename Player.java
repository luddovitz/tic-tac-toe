package tictactoe;

public class Player {

    private String playerType;
    private char symbol;

    public Player(String playerType, int playerNumber) {
        this.playerType = playerType;
        switch(playerNumber) {
            case 1:
                this.symbol = 'X';
                break;
            case 2:
                this.symbol = 'O';
                break;
            default:
                break;
        }
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public char getSymbol() {
        return symbol;
    }

}
