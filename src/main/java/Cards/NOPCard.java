package Cards;

public class NOPCard implements Card {
    public CardFace face;

    public NOPCard() {
        this.face = CardFace.NOP;
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
