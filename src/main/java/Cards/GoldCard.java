package Cards;

public class GoldCard implements Card {
    public CardFace face;

    public GoldCard() {
        this.face = CardFace.GOLD;
    }

    public CardFace getFace() {
        return face;
    }

    public int target() {
        return -1;
    }

    public int bonusScore() {
        return -1;
    }
}
