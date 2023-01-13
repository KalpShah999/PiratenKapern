import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pk.Dice;
import pk.Faces;

public class PiratenKarpen {

    static Dice die = new Dice();
    static Faces[] myDice = new Faces[8];

    static Scanner myScanner = new Scanner(System.in);
    static String input;

    static float[] winRate = {0, 0};

    public static void main(String[] args) {
        // Initizlize Game
        //System.out.print("Welcome to Piraten Karpen Simulator!\n\nHow many games would you like to play? ");
        //int numberOfGames = Integer.parseInt(myScanner.nextLine());
        //System.out.println();
        System.out.println("Welcome to Piraten Kapern Simulator! Simulating 42 games...\n");
        int numberOfGames = 42;


        // Play Games
        while (numberOfGames > 0) { 
            RollAllDice();
            //System.out.println("Player 1 Turn.");
            int playerOneScore = PlayerMove();

            RollAllDice();
            //System.out.println("Player 2 Turn.");
            int playerTwoScore = PlayerMove();

            if (playerOneScore > playerTwoScore) {
                winRate[0]++;
            } else if (playerOneScore < playerTwoScore) {
                winRate[1]++;
            } 

            //PlayerMoveFirstTime(1);
            numberOfGames--;
        }

        // Print out the final score 
        System.out.println("Player 1 Win Rate - " + (float)((winRate[0] / (winRate[0] + winRate[1])) * 100) + "%\nPlayer 2 Win Rate - " + (float)((winRate[1] / (winRate[0] + winRate[1])) * 100) + "%");
    }

    public static void ShowAllDice() {
        System.out.print("Dice: ");
        for (int i = 0; i < myDice.length - 1; i++) System.out.printf("(%d) %7s, ", i + 1, myDice[i]);
        System.out.printf("(%d) %7s\n\n", myDice.length, myDice[myDice.length - 1]);
    }

    public static void RollDie(int... whichDie) {
        for (int i = 0; i < whichDie.length; i++) {
            myDice[whichDie[i] - 1] = die.roll();
        }
    }

    public static void RollAllDice() {
        RollDie(1, 2, 3, 4, 5, 6, 7, 8);
        //for (int i = 0; i < myDice.length; i++) myDice[i] = die.roll();
    }

    public static void RollRandomDice() {
        ArrayList<Integer> diceToReRoll = new ArrayList<Integer>();

        for (int i = 0; i < myDice.length; i++) if (myDice[i] != Faces.SKULL) diceToReRoll.add(i + 1);

        Collections.shuffle(diceToReRoll);

        int numberOfDieToRoll = new Random().nextInt(diceToReRoll.size());
        for (int i = 0; i < numberOfDieToRoll; i++) RollDie(diceToReRoll.get(i));
    }

    public static int PlayerMove() {
        //ShowAllDice();

        if (CheckSkulls() == 0) {
            RollRandomDice();
            return PlayerMove();
        } else {
            //System.out.println("Turn Ended.\n\n");
            return CalculateScore();
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

        for (Faces face : myDice) if (face == Faces.DIAMOND || face == Faces.GOLD) score += 100;

        return score;
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

    public static void CalculateScore() {
        int score = 0;
        int[] diceCount = new int[5];

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
            System.out.print(diceCount[i] + " "); 
        }
        System.out.println("\n");
    }
    */
}
