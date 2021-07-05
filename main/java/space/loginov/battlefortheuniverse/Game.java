package space.loginov.battlefortheuniverse;

import java.util.ArrayList;
import java.util.Collections;


public class Game {
    public static ArrayList<Card> EnemyDeck, PlayerDeck, EnemyHand, PlayerHand, EnemyField, PlayerField, HelpDeck;

    public Game(){
        EnemyDeck = GiveDeckCard();
        PlayerDeck = GiveDeckCard();
        HelpDeck = CopyCards();

        EnemyHand = new ArrayList<>();
        PlayerHand = new ArrayList<>();

        EnemyField = new ArrayList<>();
        PlayerField = new ArrayList<>();
    }

    public static ArrayList<Card> GiveDeckCard(){
        ArrayList<Card> list = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            list.add(Card.CardManager().get(i));
            list.add(Card.CardManager().get(i));
        }
        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<Card> CopyCards(){
        ArrayList<Card> list = new ArrayList<>();
        for(int i = 0; i < 28; i++){
            list.add(EnemyDeck.get(i));
        }
        return list;
    }


}
