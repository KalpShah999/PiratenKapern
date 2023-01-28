package Cards;

public class SorceressCard implements Card {
    public CardFace face;

    int numSkulls;

    public SorceressCard() {
        this.face = CardFace.SORCERESS;
        numSkulls = 1;
    }

    public CardFace getFace() {
        return face;
    }

    public int target() {
        return numSkulls;
    }

    public int bonusScore() {
        numSkulls = 1;
        return -1;
    }

    public void useSkull() {
        numSkulls = 0;
    }
}
