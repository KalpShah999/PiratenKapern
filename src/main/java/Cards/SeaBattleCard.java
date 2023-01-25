package Cards;

public class SeaBattleCard implements Card {
    public CardFace face;

    public int numSabers;
    public int bonusScore;

    public SeaBattleCard(int num_of_sabers) {
        this.face = CardFace.SEA_BATTLE;
        numSabers = num_of_sabers;

        switch (numSabers) {
            case 3:
                bonusScore = 500;
                break;
            case 4:
                bonusScore = 1000;
                break;
            default:
                bonusScore = 300;
                break; 
        }
    }

    public CardFace getFace() {
        return face;
    }

    public int target() {
        return numSabers;
    }

    public int bonusScore() {
        return bonusScore;
    }
}
