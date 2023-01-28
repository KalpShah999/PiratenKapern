import java.util.Scanner;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;

import Cards.CardDeck;
import pk.Dice;
import pk.Faces;
import pk.GameLogic;
import pk.Player;
import pk.Strategies;

public class PiratenKarpen {

    static CardDeck deck = new CardDeck();
    static Faces[] myDice = new Faces[8];

    static Scanner myScanner = new Scanner(System.in);
    static String input;

    static ArrayList<Player> players = new ArrayList<>();
    static int numOfPlayers;

    static int numOfGames;

    public static void main(String[] args) { 
        //Check to see if the user wants to GameLogic.trace the player decisions 
        GameLogic.log = LogManager.getLogger(GameLogic.class);
        GameLogic.trace = args.length > 0 && args[args.length - 1].toLowerCase().equals("trace");
        if (GameLogic.trace) System.out.println("Trace Mode Activated\n");

        //Manually add in combo and random strategy players depending on the player strategies given by the player 
        int limit = GameLogic.trace ? args.length - 1 : args.length;
        for (int i = 0; i < limit; i++) {
            switch(args[i].toLowerCase()) {
                case "combo":
                    players.add(new Player(Strategies.PlayerStrategies.COMBO));
                    if (GameLogic.trace) GameLogic.log.info("Added Player " + players.size() + " with the COMBO strategy!");
                    break;
                case "battle":
                    players.add(new Player(Strategies.PlayerStrategies.BATTLE));
                    if (GameLogic.trace) GameLogic.log.info("Added Player " + players.size() + " with the BATTLE strategy!");
                    break;
                default: 
                    players.add(new Player(Strategies.PlayerStrategies.RANDOM));
                    if (GameLogic.trace) GameLogic.log.info("Added Player " + players.size() + " with the RANDOM strategy!");
                    break;
            }
        }

        //Add at least two players if they were not previously defined 
        while (players.size() < 2) {
            players.add(new Player(Strategies.PlayerStrategies.RANDOM));
            if (GameLogic.trace) GameLogic.log.info("Added Player " + players.size() + " with the RANDOM strategy!");
        }

        //Ask the user how many rounds of the game they would like to play 
        System.out.print("Welcome to Piraten Karpen Simulator!\n\nHow many games would you like to play? ");
        numOfGames = Integer.parseInt(myScanner.nextLine());
        System.out.println();
        
        if (GameLogic.trace) GameLogic.log.info("Simulating " + numOfGames + " rounds with " + players.size() + " players.\n\n\n\n\n");

        SimulateRounds(numOfGames, 0);

        PrintWinRates();
    }

    public static void SimulateRounds(int roundsLeft, int startingPlayer) {
        if (roundsLeft == 0) return;

        if (GameLogic.trace) GameLogic.log.info("---   Round " + (numOfGames - roundsLeft + 1) + "   ---\n\n\n");

        boolean roundEnded = false;
        int playersLeftToPlay = 1;
        while (playersLeftToPlay > 0) {
            //Make all the players play the game 
            for (int i = 0; i < players.size(); i++) {
                i %= players.size();

                if (GameLogic.trace) GameLogic.log.info("Player " + (i + 1) + " starts their turn.");

                //Refresh the dice before the player starts their turn 
                Dice.RollAllDice(myDice);

                //The player must draw a card
                players.get(i).DrawCard(deck);

                if (GameLogic.trace) GameLogic.log.info("Player " + (i + 1) + " drew the " + players.get(i).card().getFace() + " card.");

                //Player makes their move
                players.get(i).setScore(players.get(i).getScore() + PlayerMove(players.get(i)));

                if (GameLogic.trace) GameLogic.log.info("Player " + (players.indexOf(players.get(i)) + 1) + "'s score is now " + players.get(i).getScore() + ".\n\n\n");

                //End the round if the player got a final score greater than or equal to 6000 
                if (!roundEnded && players.get(i).getScore() >= 6000) {
                    if (GameLogic.trace) GameLogic.log.info("Player " + (players.indexOf(players.get(i)) + 1) + " scores higher than 6000!\n\n\n");
                    roundEnded = true;
                    playersLeftToPlay = players.size();
                }

                //Ensure every player takes their turn once more before the round ends after a player reaches 6000 points
                if (roundEnded) playersLeftToPlay--;
                if (playersLeftToPlay <= 0) break;
            }
        }
            
        GameLogic.UpdateWinner(players);

        if (GameLogic.trace && roundsLeft > 1) GameLogic.log.info("\n\n\n\n");

        SimulateRounds(roundsLeft - 1, startingPlayer + 1);
    }

    public static void ShowAllDice() {
        String output = "Dice: ";

        for (int i = 0; i < myDice.length - 1; i++) output += String.format("(%d) %7s, ", i + 1, myDice[i]);
        output += String.format("(%d) %7s\n", myDice.length, myDice[myDice.length - 1]);

        if (GameLogic.trace) GameLogic.log.info(output);
    }

    public static int PlayerMove(Player player) {
        ShowAllDice();

        if (GameLogic.CheckSkulls(myDice, player.card()) == 0 && player.getRollStrategy().roll_dice(myDice, player.card())) {
            return PlayerMove(player);
        }
        else if (GameLogic.CheckSkulls(myDice, player.card()) == 0) {
            int finalScore = GameLogic.CalculateScore(myDice, player.card());

            if (GameLogic.trace) GameLogic.log.info("The player ended their turn. Final Score - " + finalScore);

            return finalScore;
        } else {
            if (GameLogic.trace) GameLogic.log.info("The player was disqualified.");

            return 0;
        }
    }

    public static void PrintWinRates() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (i + 1) + " Win Rate - " + (float)((players.get(i).getGamesWon() * 100) / players.get(i).getGamesPlayed()) + "%");
        }
    }
}
