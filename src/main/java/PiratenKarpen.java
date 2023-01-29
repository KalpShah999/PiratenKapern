import java.util.Scanner;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;

import Cards.CardDeck;
import Cards.CardFace;
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

        //Add at least two players if they were not previously defined through command line arguments 
        while (players.size() < 2) {
            players.add(new Player(Strategies.PlayerStrategies.RANDOM));
            if (GameLogic.trace) GameLogic.log.info("Added Player " + players.size() + " with the RANDOM strategy!");
        }

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
            boolean loopStart = true;

            //Make all the players play the game 
            for (int i = startingPlayer; loopStart || i != startingPlayer; i++) {
                loopStart = false;
                i %= players.size();

                if (GameLogic.trace) GameLogic.log.info("Player " + (i + 1) + " starts their turn.");

                players.get(i).reset();

                Dice.RollAllDice(myDice);

                players.get(i).DrawCard(deck);

                //Reset the sorceress card if it was chosen 
                if (players.get(i).card().getFace() == CardFace.SORCERESS) players.get(i).card().bonusScore();

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

    public static int PlayerMove(Player player) {
        GameLogic.ShowAllDice(myDice);

        if (GameLogic.CountSkulls(myDice, player.card()) > 3 && player.firstRoll() == true) {
            if (GameLogic.trace) GameLogic.log.info("Player entered the island of skulls."); 
            IslandOfSkulls(player);
            return 0;
        }
        else if (GameLogic.CountSkulls(myDice, player.card()) < 3 && player.getRollStrategy().roll_dice(myDice, player.card())) {
            player.rolled();
            return PlayerMove(player);
        }
        else if (GameLogic.CountSkulls(myDice, player.card()) < 3) {
            int finalScore = GameLogic.CalculateScore(myDice, player.card());

            if (GameLogic.trace) GameLogic.log.info("The player ended their turn. Final Score - " + finalScore);

            return finalScore;
        } else {
            if (GameLogic.trace) GameLogic.log.info("The player was disqualified.");

            return 0;
        }
    }

    public static void IslandOfSkulls(Player player) {
        int prevNumSkullsRolled = 0;
        int newNumSkullsRolled = 0;

        do {
            prevNumSkullsRolled = newNumSkullsRolled;
            for (Faces die : myDice) if (die != Faces.SKULL) die = Dice.roll();
            GameLogic.ShowAllDice(myDice);
            newNumSkullsRolled = GameLogic.CountSkulls(myDice, player.card());
        } while (newNumSkullsRolled > prevNumSkullsRolled);

        if (GameLogic.trace) GameLogic.log.info("Rolled " + prevNumSkullsRolled + " skulls."); 

        if (player.card().getFace() == CardFace.CAPTAIN) prevNumSkullsRolled *= 2;

        player.setScore(player.getScore() + prevNumSkullsRolled * 100);

        for (Player otherPlayer : players) otherPlayer.setScore(otherPlayer.getScore() - prevNumSkullsRolled * 100);
    }

    public static void PrintWinRates() {
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player " + (i + 1) + " Win Rate - " + (float)((players.get(i).getGamesWon() * 100) / players.get(i).getGamesPlayed()) + "%");
        }
    }
}
