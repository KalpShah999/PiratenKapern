import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pk.Dice;
import pk.Faces;
import pk.Player;
import pk.Strategies;

public class PiratenKarpen {

	private static final Logger log = LogManager.getLogger(PiratenKarpen.class);

    static Dice die = new Dice();
    static Faces[] myDice = new Faces[8];

    static Scanner myScanner = new Scanner(System.in);
    static String input;

    static ArrayList<Player> players = new ArrayList<>();
    static int numOfPlayers;

    private static boolean trace;

    public static void main(String[] args) { 
        //Check to see if the user wants to trace the player decisions 
        trace = args.length > 2 && args[2].equals("trace");
        if (trace) System.out.println("Trace Mode Activated\n");

        // Initizlize Game
        System.out.print("Welcome to Piraten Karpen Simulator!\n\nHow many players are playing? ");
        int numOfPlayers = Integer.parseInt(myScanner.nextLine());
        for (int i = 0; i < numOfPlayers; i++) players.add(new Player(Strategies.RANDOM));
        System.out.println("\n\nHow many games would you like to play? ");
        int numberOfGames = Integer.parseInt(myScanner.nextLine());
        System.out.println();
        

        if (trace) log.info("Simulating " + numberOfGames + " rounds with " + numOfPlayers + " players.\n\n\n\n\n");


        int roundCounter = 1;
        // Play Games
        while (roundCounter <= numberOfGames) { 
            if (trace) log.info("---   Round " + roundCounter + "   ---\n\n\n");

            boolean roundEnded = false;
            while (!roundEnded) {
                //Make all the players play the game 
                for (Player player : players) {
                    if (trace) log.info("Player " + (players.indexOf(player) + 1) + " starts their turn.");
    
                    //Player takes their turn and their score gets added to their previous score 
                    RollAllDice();
                    player.setScore(player.getScore() + PlayerMove());

                    if (trace) log.info("Player " + (players.indexOf(player) + 1) + "'s score is now " + player.getScore() + ".\n\n\n");
    
                    //End the round if the player got a final score greater than or equal to 6000 
                    if (player.getScore() >= 6000) {
                        roundEnded = true;
                        break;
                    }
                }
            }
            
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

            if (trace && roundCounter < numberOfGames) log.info("\n\n\n\n");

            //PlayerMoveFirstTime(1);
            roundCounter++;
        }

        PrintWinRates();
    }

    public static void ShowAllDice() {
        String output = "Dice: ";

        for (int i = 0; i < myDice.length - 1; i++) output += String.format("(%d) %7s, ", i + 1, myDice[i]);
        output += String.format("(%d) %7s\n", myDice.length, myDice[myDice.length - 1]);

        if (trace) log.info(output);
    }

    public static void RollDie(int... whichDie) {
        for (int i = 0; i < whichDie.length; i++) myDice[whichDie[i] - 1] = die.roll();
    }

    public static void RollAllDice() {
        RollDie(1, 2, 3, 4, 5, 6, 7, 8);
    }

    public static void RollRandomDice() {
        ArrayList<Integer> diceToReRoll = new ArrayList<Integer>();

        for (int i = 0; i < myDice.length; i++) if (myDice[i] != Faces.SKULL) diceToReRoll.add(i + 1);

        Collections.shuffle(diceToReRoll);

        String diceRolledAgain = "";

        int numberOfDieToRoll = new Random().nextInt(diceToReRoll.size() - 2) + 2;
        for (int i = 0; i < numberOfDieToRoll; i++) {
            RollDie(diceToReRoll.get(i));
            diceRolledAgain += diceToReRoll.get(i) + " ";
        }

        if (trace) log.info("Rerolling following dice: " + diceRolledAgain);
    }

    public static int PlayerMove() {
        ShowAllDice();

        if (CheckSkulls() == 0) {
            RollRandomDice();
            return PlayerMove();
        } else {
            //System.out.println("Turn Ended.\n\n");
            int finalScore = CalculateScore();

            if (trace) log.info("Turn ended. Final Score - " + finalScore);

            return finalScore;
        }
    }

    public static int CheckSkulls() {
        int skullCounter = 0;

        for (Faces face : myDice) if (face == Faces.SKULL) skullCounter++;

        if (skullCounter == 3) return 1;
        else if (skullCounter > 3) return 2;
        return 0;
    }

    public static int CalculateScore() {
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

    public static void PrintWinRates() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (i + 1) + " Win Rate - " + (float)((players.get(i).getGamesWon() * 100) / players.get(i).getGamesPlayed()) + "%");
        }
    }
    
    /*
    public static void PlayerMoveFirstTime(int playerNumber) {
        System.out.println("Player " + playerNumber + "'s Turn:");

        ShowAllDice();

        if (CheckSkulls() == 1) {
            System.out.println("Turn Ended.\n");
            CalculateScore();
        } else if (CheckSkulls() == 2) {
            System.out.println("Welcome to the Underworld!\n");
            CalculateScore();
        } else {
            //Get user input 
            System.out.println("Enter which dice will you like to roll again (Ex. 2, 4, 7), or press enter to end your turn? ");
            input = myScanner.nextLine();
            input = input.replace(" ", ""); // Remove all spaces 
    
            if (input.replace(",", "") == "") {
                System.out.println("Turn Ended.\n"); //Leave if they no longer want to roll die
                CalculateScore();
            }
            else {
                //Add values from the string input to an integer array 
                int[] values = new int[input.split(",").length];
                int counter = 0;
                for (String s : input.split(",")) {
                    values[counter] = Integer.valueOf(s);
                    counter++;
                } 
        
                RollDie(values);
            
                PlayerMoveSecondTime();
            }
        }
    }
    
    public static void PlayerMoveSecondTime() {
        ShowAllDice();

        if (CheckSkulls() > 0) {
            System.out.println("Turn Ended.\n");
            CalculateScore();
        } else {
            //Get user input 
            System.out.println("Enter which dice will you like to roll again (Ex. 2, 4, 7), or press enter to end your turn? ");
            input = myScanner.nextLine();
            input = input.replace(" ", ""); // Remove all spaces 
    
            if (input.replace(",", "") == "") {
                System.out.println("Turn Ended.\n"); //Leave if they no longer want to roll die
                CalculateScore();
            }
            else {
                //Add values from the string input to an integer array 
                int[] values = new int[input.split(",").length];
                int counter = 0;
                for (String s : input.split(",")) {
                    values[counter] = Integer.valueOf(s);
                    counter++;
                } 
        
                RollDie(values);
            
                PlayerMoveSecondTime();
            }
        }
    }
    */
}
