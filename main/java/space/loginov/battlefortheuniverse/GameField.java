package space.loginov.battlefortheuniverse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static space.loginov.battlefortheuniverse.Game.EnemyDeck;
import static space.loginov.battlefortheuniverse.Game.EnemyHand;
import static space.loginov.battlefortheuniverse.Game.HelpDeck;
import static space.loginov.battlefortheuniverse.Game.PlayerDeck;
import static space.loginov.battlefortheuniverse.Game.PlayerField;
import static space.loginov.battlefortheuniverse.Game.PlayerHand;



public class GameField extends AppCompatActivity {

    public LinearLayout myField, myDeck;
    public FrameLayout card1_deck, card2_deck, card3_deck, card4_deck, card5_deck;
    public FrameLayout card1_edeck, card2_edeck, card3_edeck, card4_edeck, card5_edeck;
    public ArrayList<FrameLayout> card_pos_array = new ArrayList<>();
    public LinearLayout my_hand;
    public Button endTurn;
    public static Game BeginGame;
    public String card_poss = "card_pos";
    private int num_in_deck=0;
    private int cards_in_enemy_deck = 17;
    private int cards_in_player_hand = 0, cards_in_enemy_hand = 4;
    private int current_mana_player = 1, current_mana_enemy = 1;
    private int mana_player = 1, mana_enemy = 1;
    private int enemy_health = 30, player_health = 30;
    private long backPressedTime, animationTime;
    private Toast backToast;
    private List<View> allEds;

    public ArrayList<TextView> Ids_enemy, Ids;
    public ArrayList<FrameLayout> card_eposess, card_posess;
    public ArrayList<Card> cards_in_efield, cards_in_field;
    public ArrayList<View> views, views_enemy;
    public int card_counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        endTurn = findViewById(R.id.end_turn_but);



        card1_deck = (FrameLayout) findViewById(R.id.card_pos1_deck);
        card2_deck = (FrameLayout) findViewById(R.id.card_pos2_deck);
        card3_deck = (FrameLayout) findViewById(R.id.card_pos3_deck);
        card4_deck = (FrameLayout) findViewById(R.id.card_pos4_deck);
        card5_deck = (FrameLayout) findViewById(R.id.card_pos5_deck);

        card1_edeck = (FrameLayout) findViewById(R.id.card_pos1_edeck);
        card2_edeck = (FrameLayout) findViewById(R.id.card_pos2_edeck);
        card3_edeck = (FrameLayout) findViewById(R.id.card_pos3_edeck);
        card4_edeck = (FrameLayout) findViewById(R.id.card_pos4_edeck);
        card5_edeck = (FrameLayout) findViewById(R.id.card_pos5_edeck);



        my_hand = (LinearLayout)findViewById(R.id.my_deck_layout);
        myField = (LinearLayout) findViewById(R.id.my_field_layout);
        myDeck = (LinearLayout) findViewById(R.id.my_deck_layout);



        card1_deck.setOnDragListener(new MyOnDragListener(1));
        card2_deck.setOnDragListener(new MyOnDragListener(2));
        card3_deck.setOnDragListener(new MyOnDragListener(3));
        card4_deck.setOnDragListener(new MyOnDragListener(4));
        card5_deck.setOnDragListener(new MyOnDragListener(5));

        card1_edeck.setOnClickListener(null);
        card2_edeck.setOnClickListener(null);
        card3_edeck.setOnClickListener(null);
        card4_edeck.setOnClickListener(null);
        card5_edeck.setOnClickListener(null);


        card_posess = new ArrayList<>();
        card_eposess = new ArrayList<>();
        cards_in_efield = new ArrayList<>();
        cards_in_field = new ArrayList<>();
        Ids = new ArrayList<>();
        Ids_enemy = new ArrayList<>();
        views = new ArrayList<>();
        views_enemy = new ArrayList<>();

        for(int i =0; i < 5; i++){
            cards_in_efield.add(null);
            cards_in_field.add(null);
            Ids.add(null);
            Ids_enemy.add(null);
            views.add(null);
            views_enemy.add(null);
        }


        card_eposess.add(card1_edeck);
        card_eposess.add(card2_edeck);
        card_eposess.add(card3_edeck);
        card_eposess.add(card4_edeck);
        card_eposess.add(card5_edeck);

        card_posess.add(card1_deck);
        card_posess.add(card2_deck);
        card_posess.add(card3_deck);
        card_posess.add(card4_deck);
        card_posess.add(card5_deck);


      // EnemyDeck = Game.GiveDeckCard();
       //PlayerDeck = Game.GiveDeckCard();

        BeginGame = new Game();
       for(card_counter = 0; card_counter < 4; card_counter++){
            DrawCard(my_hand,card_counter);
            PlayerHand.add(PlayerDeck.get(0));
            PlayerDeck.remove(0);
            EnemyHand.add(EnemyDeck.get(0));
            EnemyDeck.remove(0);
            cards_in_player_hand++;
        }
        EnemyHand.add(EnemyDeck.get(0));
        EnemyDeck.remove(0);


        endTurn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mana_player < 10) {
                    mana_player++;
                }
                current_mana_player = mana_player;
                endTurn.setClickable(false);
                endTurn.setBackgroundResource(R.drawable.style_btn_stroke_black95);
                EnemyTurn();
                if (PlayerDeck.size() > 0 && cards_in_player_hand < 7) {
                    DrawCard(my_hand, card_counter);
                    card_counter++;
                    PlayerHand.add(PlayerDeck.get(0));
                    PlayerDeck.remove(0);
                    cards_in_player_hand++;
                } else return;

            }
        });

        //Развернуть игру на весь экран - начало
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //Развернуть игру на весь экран - конец

    }

    public void EnemyTurn(){
        int j, i;

            for(i =0; i < EnemyHand.size(); i++) {
                if (EnemyHand.get(i).cost <= current_mana_enemy) {
                    for (j = 0; j < 5; j++) {
                        if (card_eposess.get(j).getTag().toString().equals("0")) {
                            DrawEnemyCard(card_eposess.get(j), i, j);
                            current_mana_enemy -= EnemyHand.get(i).cost;
                            EnemyHand.remove(i);
                            card_eposess.get(j).setTag("1");
                            j = 5;
                        }
                    }
                }
            }




                        for (j = 0; j < 5; j++) {
                            if (card_eposess.get(j).getTag().toString().equals("1")) {
                                if (cards_in_field.get(j) != null) {
                                    cards_in_efield.get(j).Defense -= cards_in_field.get(j).Attact;
                                    cards_in_field.get(j).Defense -= cards_in_efield.get(j).Attact;
                                    TextView def = views.get(j).findViewById(R.id.defense_card);
                                    def.setText(Integer.toString(cards_in_field.get(j).Defense));
                                    Ids_enemy.get(j).setText(Integer.toString(cards_in_efield.get(j).Defense));
                                    if (cards_in_efield.get(j).Defense <= 0) {
                                        card_eposess.get(j).removeAllViews();
                                        cards_in_efield.add(j, null);
                                        card_eposess.get(j).setTag("0");
                                    }
                                    if (cards_in_field.get(j).Defense <= 0) {
                                        card_posess.get(j).removeAllViews();
                                        card_posess.get(j).setTag("0");
                                        cards_in_field.add(j, null);
                                    }
                                } else {
                                    player_health -= cards_in_efield.get(j).Attact;
                                }

                                if (player_health <= 0) {
                                    backToast = Toast.makeText(getBaseContext(), "YOU LOSE!!!", Toast.LENGTH_SHORT);
                                    backToast.show();
                                    Intent intent = new Intent(GameField.this, MainActivity.class);
                                    startActivity(intent);
                                }
                        }
                    }


        for(int i1 = 0; i1 < 5; i1++) {
            if (views.get(i1) != null) {
                views.get(i1).setOnClickListener(new MyOnClickListener());
                views.get(i1).setBackgroundTintList(ColorStateList.valueOf(Color.argb(0, 100, 100, 100)));
                views.get(i1).setBackgroundTintMode(PorterDuff.Mode.SCREEN);
            }
        }

        endTurn.setClickable(true);
        endTurn.setBackgroundResource(R.drawable.can_end);
        if(mana_enemy < 10) {
            mana_enemy++;
        }
        current_mana_enemy = mana_enemy;
        if (EnemyDeck.size() > 0 && EnemyHand.size() < 7) {
            EnemyHand.add(EnemyDeck.get(0));
            EnemyDeck.remove(0);
        }
    }

    public void DrawCard(LinearLayout my_hand, int num_card){

        allEds = new ArrayList<View>();
        final View view = getLayoutInflater().inflate(R.layout.card_layout,null);

        FrameLayout card_pos = (FrameLayout)view.findViewById(R.id.card_pos);

        card_pos.setBackgroundResource(PlayerDeck.get(0).LogoPath);
        card_pos.setOnTouchListener(new GameField.MyOnTouchListener());
        card_pos.setTag(num_card);
        card_pos_array.add(card_pos);



        TextView name_card = (TextView)view.findViewById(R.id.name_card);
        name_card.setText(PlayerDeck.get(0).name);
        name_card.setTextColor(Color.WHITE);

        TextView attack_card = (TextView)view.findViewById(R.id.attack_card);
        attack_card.setText(Integer.toString(PlayerDeck.get(0).Attact));
        attack_card.setTextColor(Color.GREEN);

        TextView defense_card = (TextView)view.findViewById(R.id.defense_card);
        defense_card.setText(Integer.toString(PlayerDeck.get(0).Defense));
        defense_card.setTextColor(Color.YELLOW);

        TextView cost_card = (TextView)view.findViewById(R.id.cost_card);
        cost_card.setText(Integer.toString(PlayerDeck.get(0).cost));
        cost_card.setTextColor(Color.BLUE);
        allEds.add(view);
        my_hand.addView(view);
    }

    public void DrawEnemyCard(FrameLayout enemy_field, int card, int num_in_field){

        allEds = new ArrayList<View>();

        final View view = getLayoutInflater().inflate(R.layout.card_layout,null);

        FrameLayout card_pos = (FrameLayout)view.findViewById(R.id.card_pos);
       // card_pos.setId(R.id.card_pos + num_in_field);

        card_pos.setBackgroundResource(EnemyHand.get(card).LogoPath);
        cards_in_efield.add(num_in_field, EnemyHand.get(card));



        TextView name_card = (TextView)view.findViewById(R.id.name_card);
        name_card.setText(EnemyHand.get(card).name);
        name_card.setTextColor(Color.WHITE);

        TextView attack_card = (TextView)view.findViewById(R.id.attack_card);
        //attack_card.setId(R.id.attack_card + num_in_field);
        attack_card.setText(Integer.toString(EnemyHand.get(card).Attact));
        attack_card.setTextColor(Color.GREEN);

        TextView defense_card = (TextView)view.findViewById(R.id.defense_card);
       // defense_card.setId(R.id.defense_card + num_in_field);
        defense_card.setText(Integer.toString(EnemyHand.get(card).Defense));
        defense_card.setTextColor(Color.YELLOW);
        Ids_enemy.add(num_in_field, defense_card);

        TextView cost_card = (TextView)view.findViewById(R.id.cost_card);
        cost_card.setText(Integer.toString(EnemyHand.get(card).cost));
        cost_card.setTextColor(Color.BLUE);
        allEds.add(view);
        enemy_field.addView(view);
    }

    public void Info(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameField.this);
        builder.setTitle("Game information")
                .setMessage("Enemy : \n     Health : " + enemy_health + "\n" + "     Cards in deck : "  + EnemyDeck.size() + "\n" + "     Cards in hand :" + EnemyHand.size() + "\n" + "    Mana : " + current_mana_enemy + "/" + mana_enemy + "\n" +
                        "Player : \n    Health : " + player_health + "\n" + "    Cards in deck : "  + PlayerDeck.size() + "\n" + "    Cards in hand :" + cards_in_player_hand  + "\n" + "    Mana : " + current_mana_player + "/" + mana_player)
                .setCancelable(false)
                .setPositiveButton("Сlose",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog Error = builder.create();
        Error.show();
    }


    class MyOnTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event){
            ClipData data = ClipData.newPlainText("simple_text", "text");
            View.DragShadowBuilder sb = new View.DragShadowBuilder(v);
            v.startDrag(data, sb, v, 0);
            v.setVisibility(View.VISIBLE);
            return true;
        }
    }

    class MyOnLongListener implements  View.OnLongClickListener{
        @Override
        public boolean onLongClick(View v) {
          /*  v.setBackgroundTintList(ColorStateList.valueOf(Color.argb(0,100,100,100)));
            v.setBackgroundTintMode(PorterDuff.Mode.SCREEN); */
            return false;
        }
    }

    class MyOnClickListener implements  View.OnClickListener{
        @Override
        public void onClick(View v){
            v.setBackgroundTintList(ColorStateList.valueOf(Color.argb(100,100,100,100)));
            v.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
            if(cards_in_efield.get((Integer)(v.getTag())) != null){
                cards_in_efield.get((Integer)(v.getTag())).Defense -= cards_in_field.get((Integer)(v.getTag())).Attact;
                cards_in_field.get((Integer)(v.getTag())).Defense -= cards_in_efield.get((Integer)(v.getTag())).Attact;
                TextView def = v.findViewById(R.id.defense_card);
                def.setText(Integer.toString(cards_in_field.get((Integer)(v.getTag())).Defense));
                Ids_enemy.get((Integer)(v.getTag())).setText(Integer.toString(cards_in_efield.get((Integer)(v.getTag())).Defense));
                if(cards_in_efield.get((Integer)(v.getTag())).Defense <= 0){
                    card_eposess.get((Integer)(v.getTag())).removeAllViews();
                    cards_in_efield.add(((Integer)(v.getTag())), null);
                    card_eposess.get((Integer)(v.getTag())).setTag("0");
                }
                if(cards_in_field.get((Integer)(v.getTag())).Defense <= 0){
                    card_posess.get((Integer)(v.getTag())).removeAllViews();
                    card_posess.get((Integer)(v.getTag())).setTag("0");
                    cards_in_field.add(((Integer)(v.getTag())), null);
                }
            } else{
            enemy_health -= cards_in_field.get((Integer)(v.getTag())).Attact; }
            v.setOnClickListener(null);

            if(enemy_health <=0){
                backToast = Toast.makeText(getBaseContext(),"YOU WIN!!!",Toast.LENGTH_SHORT);
                backToast.show();
                Intent intent = new Intent(GameField.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }





    class MyOnDragListener implements View.OnDragListener{
        private int num;
        public MyOnDragListener(int num){
            super();
            this.num = num;
        }
        @Override
        public boolean onDrag(View v, DragEvent event){
            int action = event.getAction();
            final View view = (View) event.getLocalState();
            switch (action){
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i("Script", num+" - ACTION_DRAG_STARTED");
                    if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
                        return true;
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                  Log.i("Script", num+" - ACTION_DRAG_ENTERED");
                    v.setBackgroundColor(Color.argb(160,100,100,100));
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                  Log.i("Script", num+" - ACTION_DRAG_LOCATION");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.argb(0,0,0,0));
                   Log.i("Script", num+" - ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DROP:
                   Log.i("Script", num+" - ACTION_DROP");
                    int tagValue = (Integer)(view.getTag());
                    Log.i("Script",  tagValue + " ТАКОЕ ЗНАЧЕНИЕ " + PlayerHand.get(tagValue).cost);
                    if (PlayerHand.get(tagValue).cost <= current_mana_player && v.getTag().toString().equals("0")) {
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        FrameLayout container = (FrameLayout) v;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        PlayerField.add(PlayerHand.get(tagValue));
                        //PlayerHand.remove(tagValue);
                        current_mana_player -= PlayerHand.get(tagValue).cost;
                        v.setTag("1");
                        views.add(card_posess.indexOf(v),view);
                        cards_in_field.add(card_posess.indexOf(v),PlayerHand.get(tagValue));
                        view.setTag(card_posess.indexOf(v));
                        cards_in_player_hand--;
                        view.setOnTouchListener(null);
                        view.setOnLongClickListener(new MyOnLongListener());
                        view.setOnClickListener(new MyOnClickListener());
                        Log.i("Script",  tagValue + " ТАКОЕ ЗНАЧЕНИЕ " + HelpDeck.get(tagValue).cost);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("Script", num+" - ACTION_DRAG_ENDED");
                    v.setBackgroundColor(Color.argb(0,0,0,0));
                    break;
            }
            return true;
        }
    }




    //Системная кнопка "назад" - начало
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        } else{
            backToast = Toast.makeText(getBaseContext(),"Сlick again to exit",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();

    }

    //Системная кнопка "назад" - конец
}
