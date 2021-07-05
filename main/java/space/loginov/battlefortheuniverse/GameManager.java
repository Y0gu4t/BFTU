package space.loginov.battlefortheuniverse;


import java.util.ArrayList;


public class GameManager {
    public static Game CurrentGame;

    static void start(){
        CurrentGame = new Game();
    }

    static void GiveHandCards(ArrayList<Card> deck, ArrayList<Card> hand){
        int i = 0;
        while (i++ < 4){
            GiveCardToHand(deck, hand);
        }
        deck.trimToSize();
    }

   static void GiveCardToHand(ArrayList<Card> deck, ArrayList<Card> hand){
        if(deck.size() == 0) return;
        hand.add(deck.get(0));
        deck.remove(0);
    }


}
