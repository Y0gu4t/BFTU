package space.loginov.battlefortheuniverse;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Card extends AppCompatActivity {
    public String name;
    public int LogoPath;
    public int Attact, Defense, cost;

    public Card(String name,int LogoPath, int Attact, int Defense, int cost){
        setName(name);
        setAttact(Attact);
        setDefense(Defense);
        setCost(cost);
        setLogoPath(LogoPath);
    }

    public int getCost(){return cost;}
    public void setCost(int cost) {this.cost = cost;}

    public String getName(){return name;}
    public void setName(String name){ this.name = name;}

    public int getLogoPath(){ return LogoPath;}
    public void setLogoPath(int LogoPath) { this.LogoPath = LogoPath;}

    public int getAttact(){return Attact;}
    public void setAttact(int Attact){this.Attact = Attact;}

    public int getDefense(){return Defense;}
    public void setDefense(int Defense){this.Defense = Defense;}


    public static ArrayList<Card> CardManager(){
       ArrayList<Card> AllCards = new ArrayList<Card>();
       AllCards.add(new Card("Foxi",R.drawable.foxi, 1, 2, 1));
       AllCards.add(new Card("Bebe", R.drawable.bebe, 2,4,2));
       AllCards.add(new Card("Sniper", R.drawable.sniper_fox, 3, 2, 2));
       AllCards.add(new Card("Shira",R.drawable.shira, 3,4,3));
       AllCards.add(new Card("Squad",R.drawable.fox_squad,2,6,4));
       AllCards.add(new Card("Bully", R.drawable.bully, 3,2,2));
       AllCards.add(new Card("Scout",R.drawable.scout, 5,2,4));
       AllCards.add(new Card("Thug",R.drawable.thug,5,6,5));
       AllCards.add(new Card("Agent", R.drawable.agent, 2,2,1));
       AllCards.add(new Card("Alice", R.drawable.alice, 5,8,6));
       AllCards.add(new Card("Leader", R.drawable.robot_leader, 8,8,7));
       AllCards.add(new Card("Suicides", R.drawable.suicide_squad, 8,2,5));
       AllCards.add(new Card("Cap", R.drawable.commander, 10,10,9));
       AllCards.add(new Card("YEG-02", R.drawable.yeg_0203, 12,12,10));

       return AllCards;
    }
}


