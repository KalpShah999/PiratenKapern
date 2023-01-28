package Cards;

public class SkullCard implements Card {
    public CardFace face;
    
    public int num_skulls;

    public SkullCard(int target) {
        this.face = CardFace.SKULL;
        num_skulls = target;
    }

    public CardFace getFace() {
        return face;
    }

    public int target() {
        return num_skulls;
    }

    public int bonusScore() {
        return -1;
    }

    public void useSkull() {
        
    }
}
