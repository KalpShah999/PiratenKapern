package pk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Cards.Card;
import Cards.CardFace;

public class Strategies {
    public static enum PlayerStrategies {
        RANDOM, COMBO, BATTLE
    }

    public static RollStrategy random_Strategy = (Faces[] myDice, Card card) -> {
        //Get the index of all the dice that are not skulls and can be rerolled 
        ArrayList<Integer> diceToReRoll = new ArrayList<Integer>();
        for (int i = 0; i < myDice.length; i++) if (myDice[i] != Faces.SKULL) diceToReRoll.add(i + 1);

        //Shuffle these indices 
        Collections.shuffle(diceToReRoll);

        //Select a random number of dice to roll and roll that many from the start of the shuffled list
        int numberOfDieToRoll = new Random().nextInt(diceToReRoll.size() - 2) + 2;
        for (int i = 0; i < numberOfDieToRoll; i++) {
            Dice.RollDie(myDice, diceToReRoll.get(i));
        }

        return true;
    };

    public static RollStrategy combo_Strategy = (Faces[] myDice, Card card) -> {
        int[] diceCount = new int[5];

        //Calculate the number of each face present in the current 8 dice (exluding skulls since they do not contribute to score)
        for (int i = 0; i < myDice.length; i++) {
            switch(myDice[i]) {
                case MONKEY:
                    diceCount[0]++;
                    break; 
                case PARROT:
                    if (card.getFace() == CardFace.MONKEY_BUSINESS) {
                        diceCount[0]++;
                    } else {
                        diceCount[1]++;
                    }
                    break; 
                case GOLD:
                    diceCount[2]++;
                    break; 
                case DIAMOND:
                    diceCount[3]++;
                    break; 
                case SABER:
                    diceCount[4]++;
                    break; 
                default:
                    break; 
            }
        }

        boolean hasDecidedToRoll = false;
        //If the face is not apart of a set-of-a-kind, it should re-roll it
        for (int i = 0; i < myDice.length; i++) {
            switch(myDice[i]) {
                case MONKEY:
                    if (card.getFace() == CardFace.MONKEY_BUSINESS && diceCount[0] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    } else if (card.getFace() != CardFace.MONKEY_BUSINESS && diceCount[0] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break; 
                case PARROT:
                    if (card.getFace() == CardFace.MONKEY_BUSINESS && diceCount[0] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    } else if (card.getFace() != CardFace.MONKEY_BUSINESS && diceCount[1] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break; 
                case GOLD:
                    if (diceCount[2] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break;
                case DIAMOND:
                    if (diceCount[3] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break;
                case SABER:
                    if (diceCount[4] < 3) {
                        Dice.RollDie(myDice, i + 1);
                        hasDecidedToRoll = true;
                    }
                    break; 
                default:
                    break; 
            }
        }

        return hasDecidedToRoll;
    };

    public static RollStrategy battle_Strategy = (Faces[] myDice, Card card) -> {
        if (card.getFace() != CardFace.SEA_BATTLE) return combo_Strategy.roll_dice(myDice, card);

        boolean hasDecidedToRoll = false;
        //If the face is not a skull or a saber 
        for (int i = 0; i < myDice.length; i++) {
            if (myDice[i] != Faces.SKULL && myDice[i] != Faces.SABER) {
                Dice.RollDie(myDice, i + 1);
                hasDecidedToRoll = true;
            }
        }

        return hasDecidedToRoll;
    };

    public static RollStrategy[] strategy_List = {random_Strategy, combo_Strategy, battle_Strategy};
}
