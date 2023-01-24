package pk;

public class Player {
    int score; 
    int gamesWon;
    int gamesPlayed;
    int gamesTied;

    Strategies.PlayerStrategies strategy;

    RollStrategy roll_strategy;

    public Player(Strategies.PlayerStrategies strat) {
        score = 0;
        gamesWon = 0;
        gamesPlayed = 0;
        gamesTied = 0;
        strategy = strat;

        roll_strategy = Strategies.strategy_List[Strategies.PlayerStrategies.valueOf(strat.toString()).ordinal()];
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public int getScore() {
        return score;
    }

    public Strategies.PlayerStrategies getStrategy() {
        return strategy;
    }

    public void wonGame() {
        gamesWon += 1;
        lostGame();
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void tiedGame() {
        gamesTied += 1;
        lostGame();
    }

    public int getGamesTied() {
        return gamesTied;
    }

    public void lostGame() {
        gamesPlayed += 1;
        score = 0;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public RollStrategy getRollStrategy() {
        return roll_strategy;
    }
}
