package space.loginov.battlefortheuniverse;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CardView extends Fragment {

    private List<View> allEds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup card_pos, Bundle savedInstanceState) {

        Context context = getActivity().getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundResource(Card.CardManager().get(1).LogoPath);

        allEds = new ArrayList<View>();
        final View view = getLayoutInflater().inflate(R.layout.card_layout,null);
        TextView name_card = (TextView)view.findViewById(R.id.name_card);
        name_card.setText(Card.CardManager().get(1).name);
        name_card.setTextColor(Color.WHITE);

        TextView attack_card = (TextView)view.findViewById(R.id.attack_card);
        attack_card.setText(Integer.toString(Card.CardManager().get(1).Attact));
        attack_card.setTextColor(Color.GREEN);

        TextView defense_card = (TextView)view.findViewById(R.id.defense_card);
        defense_card.setText(Integer.toString(Card.CardManager().get(1).Defense));
        defense_card.setTextColor(Color.YELLOW);

        TextView cost_card = (TextView)view.findViewById(R.id.cost_card);
        cost_card.setText(Integer.toString(Card.CardManager().get(1).cost));
        cost_card.setTextColor(Color.BLUE);
        allEds.add(view);

        layout.addView(view);
        return layout;
    }

}
