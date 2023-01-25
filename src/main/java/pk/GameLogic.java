package pk;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

public class GameLogic {

    public static void UpdateWinner(ArrayList<Player> players, Logger log, boolean trace) {
        //Figure out who the winner is 
        int highestScore = players.get(0).getScore();
        ArrayList<Integer> playerWinnerIndex = new ArrayList<>();
        playerWinnerIndex.add(0);

        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getScore() > highestScore) {
                highestScore = players.get(i).getScore();
                playerWinnerIndex.clear();
                playerWinnerIndex.add(i);
            } else if (players.get(i).getScore() == highestScore) {
                playerWinnerIndex.add(i);
            }
        }

        //Update all the win scores 
        for (int i = 0; i < players.size(); i++) {
            if (playerWinnerIndex.contains(i)) {
                if (playerWinnerIndex.size() > 1) {
                    if (trace) log.info("Player " + (i+1) + " tied the round with " + players.get(i).getScore() + " points.");

                    players.get(i).tiedGame();
                } else {
                    if (trace) log.info("Player " + (i+1) + " won the round with " + players.get(i).getScore() + " points.");

                    players.get(i).wonGame();
                }
            } else {
                if (trace) log.info("Player " + (i+1) + " lost the round with " + players.get(i).getScore() + " points.");

                players.get(i).lostGame();
            }
        }
    }

    public static int CalculateScore(Faces[] myDice) {
        int score = 0;

        int[] diceCount = new int[5];

        //Calculate the number of each face present in the current 8 dice (exluding skulls since they do not contribute to score)
        for (Faces face : myDice) {
            switch(face) {
                case MONKEY:
                    diceCount[0]++;
                    break; 
                case PARROT:
                    diceCount[1]++;
                    break; 
                case GOLD:
                    diceCount[2]++;
                    break; 
                case DIAMOND:
                    diceCount[3]++;
                    break; 
                case SABER:
                    diceCount[4]++;
                    break; 
                default:
                    break; 
            }
        }

        for (int i = 0; i < diceCount.length; i++) {
            //Add the points for a x-of-a-kind 
            if (diceCount[i] == 8) score += 4000;
            else if (diceCount[i] == 7) score += 2000;
            else if (diceCount[i] == 6) score += 1000;
            else if (diceCount[i] == 5) score += 500;
            else if (diceCount[i] == 4) score += 200;
            else if (diceCount[i] == 3) score += 100;

            if (i == 2 || i == 3) score += diceCount[i] * 100; //Add 100 points for each gold or diamond 
        }

        return score;
    }

    public static int CheckSkulls(Faces[] myDice) {
        int skullCounter = 0;

        for (Faces face : myDice) if (face == Faces.SKULL) skullCounter++;

        if (skullCounter == 3) return 1;
        else if (skullCounter > 3) return 2;
        return 0;
    }
}