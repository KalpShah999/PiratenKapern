package Cards;

public interface Card {
    CardFace getFace();

    int target();

    int bonusScore();

    void useSkull();
}
