import java.util.Scanner;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Cards.CardDeck;
import pk.Dice;
import pk.Faces;
import pk.GameLogic;
import pk.Player;
import pk.Strategies;

public class PiratenKarpen {

    static final Logger log = LogManager.getLogger(PiratenKarpen.class);

    static CardDeck deck = new CardDeck();
    static Faces[] myDice = new Faces[8];

    static Scanner myScanner = new Scanner(System.in);
    static String input;

    static ArrayList<Player> players = new ArrayList<>();
    static int numOfPlayers;

    static int numOfGames;

    static boolean trace;

    public static void main(String[] args) { 
        //Check to see if the user wants to trace the player decisions 
        trace = args.length > 0 && args[args.length - 1].toLowerCase().equals("trace");
        if (trace) System.out.println("Trace Mode Activated\n");

        //Manually add in combo and random strategy players depending on the player strategies given by the player 
        int limit = trace ? args.length - 1 : args.length;
        for (int i = 0; i < limit; i++) {
            switch(args[i].toLowerCase()) {
                case "combo":
                    players.add(new Player(Strategies.PlayerStrategies.COMBO));
                    if (trace) log.info("Added Player " + players.size() + " with the COMBO strategy!");
                    break;
                case "battle":
                    players.add(new Player(Strategies.PlayerStrategies.BATTLE));
                    if (trace) log.info("Added Player " + players.size() + " with the BATTLE strategy!");
                    break;
                default: 
                    players.add(new Player(Strategies.PlayerStrategies.RANDOM));
                    if (trace) log.info("Added Player " + players.size() + " with the RANDOM strategy!");
                    break;
            }
        }

        //Add at least two players if they were not previously defined 
        while (players.size() < 2) players.add(new Player(Strategies.PlayerStrategies.RANDOM));

        //Ask the user how many rounds of the game they would like to play 
        System.out.print("Welcome to Piraten Karpen Simulator!\n\nHow many games would you like to play? ");
        numOfGames = Integer.parseInt(myScanner.nextLine());
        System.out.println();
        
        if (trace) log.info("Simulating " + numOfGames + " rounds with " + players.size() + " players.\n\n\n\n\n");

        SimulateRounds(numOfGames, 0);

        PrintWinRates();
    }

    public static void SimulateRounds(int roundsLeft, int startingPlayer) {
        if (roundsLeft == 0) return;

        if (trace) log.info("---   Round " + (numOfGames - roundsLeft + 1) + "   ---\n\n\n");

        boolean roundEnded = false;
        while (!roundEnded) {
            //Make all the players play the game 
            for (int i = startingPlayer; i != (startingPlayer + players.size() - 1) % players.size(); i++) {
                i %= players.size();

                if (trace) log.info("Player " + (i + 1) + " starts their turn.");

                //Refresh the dice before the player starts their turn 
                Dice.RollAllDice(myDice);

                //The player must draw a card
                players.get(i).DrawCard(deck);

                if (trace) log.info("Player " + (i + 1) + " drew the " + players.get(i).card().getFace() + " card.");

                //Player makes their move
                players.get(i).setScore(players.get(i).getScore() + PlayerMove(players.get(i)));

                if (trace) log.info("Player " + (players.indexOf(players.get(i)) + 1) + "'s score is now " + players.get(i).getScore() + ".\n\n\n");

                //End the round if the player got a final score greater than or equal to 6000 
                if (players.get(i).getScore() >= 6000) {
                    roundEnded = true;
                    break;
                }
            }
        }
            
        GameLogic.UpdateWinner(players, log, trace);

        if (trace && roundsLeft > 1) log.info("\n\n\n\n");

        SimulateRounds(roundsLeft - 1, startingPlayer + 1);
    }

    public static void ShowAllDice() {
        String output = "Dice: ";

        for (int i = 0; i < myDice.length - 1; i++) output += String.format("(%d) %7s, ", i + 1, myDice[i]);
        output += String.format("(%d) %7s\n", myDice.length, myDice[myDice.length - 1]);

        if (trace) log.info(output);
    }

    public static int PlayerMove(Player player) {
        ShowAllDice();

        if (GameLogic.CheckSkulls(myDice) == 0 && player.getRollStrategy().roll_dice(myDice, player.card())) {
            return PlayerMove(player);
        } else {
            int finalScore = GameLogic.CalculateScore(myDice, player.card());

            if (trace) log.info("Turn ended. Final Score - " + finalScore);

            return finalScore;
        }
    }

    public static void PrintWinRates() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (i + 1) + " Win Rate - " + (float)((players.get(i).getGamesWon() * 100) / players.get(i).getGamesPlayed()) + "%");
        }
    }
}
