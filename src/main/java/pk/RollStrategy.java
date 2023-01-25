package pk;

import Cards.Card;

public interface RollStrategy {
    boolean roll_dice(Faces[] diceToRollWith, Card playerCard);
}