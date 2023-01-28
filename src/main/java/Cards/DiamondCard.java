package Cards;

public class DiamondCard implements Card {
    public CardFace face;

    public DiamondCard() {
        this.face = CardFace.DIAMOND;
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
