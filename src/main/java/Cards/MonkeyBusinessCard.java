package Cards;

public class MonkeyBusinessCard implements Card {
    public CardFace face;

    public MonkeyBusinessCard() {
        this.face = CardFace.MONKEY_BUSINESS;
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

    public void useSkull() {

    }
}
