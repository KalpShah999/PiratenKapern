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

    static Faces[] myDice = new Faces[8];

    static Scanner myScanner = new Scanner(System.in);
    static String input;

    static ArrayList<Player> players = new ArrayList<>();
    static int numOfPlayers;

    private static boolean trace;

    public static void main(String[] args) { 
        //Check to see if the user wants to trace the player decisions 
        trace = args.length > 0 && args[args.length - 1].equals("trace");
        if (trace) System.out.println("Trace Mode Activated\n");

        //Manually add in combo and random strategy players depending on the player strategies given by the player 
        if (args.length > 0 && args[0].equals("combo")) {
            players.add(new Player(Strategies.COMBO));
            if (trace) log.info("Added Player " + players.size() + " with the COMBO strategy!");
        } else {
            players.add(new Player(Strategies.RANDOM));
            if (trace) log.info("Added Player " + players.size() + " with the RANDOM strategy!");
        }
        if (args.length > 1 && args[1].equals("combo")) {
            players.add(new Player(Strategies.COMBO));
            if (trace) log.info("Added Player " + players.size() + " with the COMBO strategy!");
        } else {
            players.add(new Player(Strategies.RANDOM));
            if (trace) log.info("Added Player " + players.size() + " with the RANDOM strategy!");
        }

        // Initizlize Game
        //System.out.print("Welcome to Piraten Karpen Simulator!\n\nHow many players are playing? ");
        //int numOfPlayers = Integer.parseInt(myScanner.nextLine());
        //for (int i = 0; i < numOfPlayers; i++) players.add(new Player(Strategies.RANDOM));
        //players.add(new Player(Strategies.COMBO));
        //players.add(new Player(Strategies.RANDOM));


        System.out.print("Welcome to Piraten Karpen Simulator!\n\nHow many games would you like to play? ");
        int numberOfGames = Integer.parseInt(myScanner.nextLine());
        System.out.println();
        

        if (trace) log.info("Simulating " + numberOfGames + " rounds with " + players.size() + " players.\n\n\n\n\n");


        int roundCounter = 1;
        // Play Games
        while (roundCounter <= numberOfGames) { 
            if (trace) log.info("---   Round " + roundCounter + "   ---\n\n\n");

            boolean roundEnded = false;
            while (!roundEnded) {
                //Make all the players play the game 
                for (Player player : players) {
                    if (trace) log.info("Player " + (players.indexOf(player) + 1) + " starts their turn.");
    
                    //Refresh the dice before the player starts their turn 
                    Dice.RollAllDice(myDice);

                    //Make the appropriate player move based on their strategy 
                    switch(player.getStrategy()) {
                        case COMBO:
                            //Player takes their turn and their score gets added to their previous score 
                            player.setScore(player.getScore() + PlayerMove_ComboStrategy());
                            break;
                        default: //Random strategy 
                            //Player takes their turn and their score gets added to their previous score 
                            player.setScore(player.getScore() + PlayerMove_RandomStrategy());
                            break;
                    }

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

    public static void RollRandomDice() {
        //Get the index of all the dice that are not skulls and can be rerolled 
        ArrayList<Integer> diceToReRoll = new ArrayList<Integer>();
        for (int i = 0; i < myDice.length; i++) if (myDice[i] != Faces.SKULL) diceToReRoll.add(i + 1);

        //Shuffle these indices 
        Collections.shuffle(diceToReRoll);

        String diceRolledAgain = "";

        //Select a random number of dice to roll and roll that many from the start of the shuffled list
        int numberOfDieToRoll = new Random().nextInt(diceToReRoll.size() - 2) + 2;
        for (int i = 0; i < numberOfDieToRoll; i++) {
            Dice.RollDie(myDice, diceToReRoll.get(i));
            diceRolledAgain += diceToReRoll.get(i) + " ";
        }

        //Output which dice were rerolled if trace mode is activated 
        if (trace) log.info("Rerolling following dice: " + diceRolledAgain);
    }

    public static boolean RollComboDice() {
        int[] diceCount = new int[5];

        //Calculate the number of each face present in the current 8 dice (exluding skulls since they do not contribute to score)
        for (int i = 0; i < myDice.length; i++) {
            switch(myDice[i]) {
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

        String diceRolledAgain = "";

        boolean hasDecidedToRoll = false;
        //If the face is not apart of a set-of-a-kind, it should re-roll it
        for (int i = 0; i < myDice.length; i++) {
            switch(myDice[i]) {
                case MONKEY:
                    if (diceCount[0] < 3) {
                        diceRolledAgain += (i + 1) + " ";
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break; 
                case PARROT:
                    if (diceCount[1] < 3) {
                        diceRolledAgain += (i + 1) + " ";
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break; 
                case GOLD:
                    if (diceCount[2] < 3) {
                        diceRolledAgain += (i + 1) + " ";
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break;
                case DIAMOND:
                    if (diceCount[3] < 3) {
                        diceRolledAgain += (i + 1) + " ";
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break;
                case SABER:
                    if (diceCount[4] < 3) {
                        diceRolledAgain += (i + 1) + " ";
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break; 
                default:
                    break; 
            }
        }

        if (trace) log.info("Rerolling following dice: " + diceRolledAgain);

        return hasDecidedToRoll;
    }

    public static int PlayerMove_RandomStrategy() {
        ShowAllDice();

        if (CheckSkulls() == 0) {
            RollRandomDice();
            return PlayerMove_RandomStrategy();
        } else {
            int finalScore = CalculateScore();

            if (trace) log.info("Turn ended. Final Score - " + finalScore);

            return finalScore;
        }
    }

    public static int PlayerMove_ComboStrategy() {
        ShowAllDice();

        if (CheckSkulls() == 0 && RollComboDice()) {
            //RollComboDice();
            return PlayerMove_ComboStrategy();
        } else {
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
