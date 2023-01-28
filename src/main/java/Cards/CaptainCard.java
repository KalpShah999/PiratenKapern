package Cards;

public class CaptainCard implements Card {
    public CardFace face;

    public CaptainCard() {
        this.face = CardFace.CAPTAIN;
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
