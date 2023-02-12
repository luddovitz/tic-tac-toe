/**
 * This class is responsible for checking game status and output correct labels.
 */

package tictactoe;

import java.util.HashMap;
import java.util.Map;

public class GameScore {
    static Map<Integer, String> markedPositions = new HashMap<Integer, String>();

    static void updateMarks(char symbol, Integer position) {
        switch (symbol) {
            case 'X' -> markedPositions.put(position, "X");
            case 'O' -> markedPositions.put(position, "O");
            default -> {
            }
        }
    }

    static String winCheck() {

        String[] winCombinations = {"012", "345", "678", "036", "147", "258", "642", "048"};
        String winner = "";

        // Loop through possible win combinations and build string of 3
        for (int i = 0; i < winCombinations.length; i++) {

            StringBuilder winningStringCheck = new StringBuilder();

            winningStringCheck.append(markedPositions.get(Character.getNumericValue(winCombinations[i].charAt(0))));
            winningStringCheck.append(markedPositions.get(Character.getNumericValue(winCombinations[i].charAt(1))));
            winningStringCheck.append(markedPositions.get(Character.getNumericValue(winCombinations[i].charAt(2))));

            if (winningStringCheck.toString().equals("XXX")) {
                winner = "X";
                break;
            }

            if (winningStringCheck.toString().equals("OOO")) {
                winner = "O";
                break;
            }

        }


        if (markedPositions.size() == 9 && winner.isEmpty()) {
            winner = "Draw";
        }

        return winner;
    }

}
