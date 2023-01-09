import pk.Dice;
import pk.Faces;

public class PiratenKarpen {

    static Dice die = new Dice();
    static Faces[] myDice = new Faces[8];

    public static void main(String[] args) {
        //Play Game
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("I'm rolling a dice");
        RollAllDice();
        ShowAllDice();
        RollDie(2, 3, 4);
        ShowAllDice();
        //System.out.println(myDice[0].roll());
        System.out.println("That's all folks!");
    }

    public static void ShowAllDice() {
        for (int i = 0; i < myDice.length - 1; i++) System.out.printf("%7s, ", myDice[i]);
        System.out.printf("%10s\n", myDice[myDice.length - 1]);
    }

    public static void RollDie(int... whichDie) {
        for (int i = 0; i < whichDie.length; i++) {
            myDice[whichDie[i] - 1] = die.roll();
        }
    }

    public static void RollAllDice() {
        RollDie(1, 2, 3, 4, 5, 6, 7, 8);
        //for (int i = 0; i < myDice.length; i++) myDice[i] = die.roll();
    }
    
}
