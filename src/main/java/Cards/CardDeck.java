package Cards;

import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    Stack<Card> cards = new Stack<>();

    Stack<Card> originalSetup = new Stack<>();

    public CardDeck() {
        for (int i = 0; i < 13; i++) cards.push(new NOPCard());
        for (int i = 0; i < 4; i++) cards.push(new CaptainCard());
        for (int i = 0; i < 4; i++) cards.push(new GoldCard());
        for (int i = 0; i < 4; i++) cards.push(new DiamondCard());
        for (int i = 0; i < 4; i++) cards.push(new MonkeyBusinessCard());
        for (int i = 0; i < 2; i++) cards.push(new SeaBattleCard(2));
        for (int i = 0; i < 2; i++) cards.push(new SeaBattleCard(3));
        for (int i = 0; i < 2; i++) cards.push(new SeaBattleCard(4));

        originalSetup.setSize(cards.size());
        Collections.copy(originalSetup, cards);

        Shuffle();
    }

    public void Shuffle() {
        Collections.shuffle(cards);
    }

    public <Any extends Card> Card Draw() {
        if (!cards.isEmpty()) return cards.pop();

        cards.setSize(originalSetup.size());
        Collections.copy(cards, originalSetup);
        Shuffle();
        return cards.pop();
    }

    public int size() {
        return cards.size();
    }
}
