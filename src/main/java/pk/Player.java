package pk;

public class Player {
    int score; 
    int gamesWon;
    int gamesPlayed;
    int gamesTied;

    public Player() {
        score = 0;
        gamesWon = 0;
        gamesPlayed = 0;
        gamesTied = 0;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public int getScore() {
        return score;
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
}
