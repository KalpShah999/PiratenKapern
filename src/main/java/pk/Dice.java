package pk;
import java.util.Random;

public class Dice {
    
    public static Faces roll() {
        int howManyFaces = Faces.values().length;
        Random bag = new Random();
        return Faces.values()[bag.nextInt(howManyFaces)];
    }

    public static void RollDie(Faces[] myDice, int... whichDie) {
        for (int i = 0; i < whichDie.length; i++) myDice[whichDie[i] - 1] = roll();
    }

    public static void RollAllDice(Faces[] myDice) {
        RollDie(myDice, 1, 2, 3, 4, 5, 6, 7, 8);
    }
}
