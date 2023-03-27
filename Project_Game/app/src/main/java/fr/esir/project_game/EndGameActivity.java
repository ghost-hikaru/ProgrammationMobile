package fr.esir.project_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EndGameActivity extends Activity {
    Map<String, String> pop = new HashMap<>();
    TextView text_score;
    TextView text_name;
    TextView state;

    String text_score_string = "Score final : ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_endgame);
        initAff();

        pop.put("Côtes-d'Armor - Bretagne", "598 814");
        pop.put("Finistère - Bretagne", "909 028");
        pop.put("Ille-et-Vilaine - Bretagne", "1 060 199");
        pop.put("Morbihan - Bretagne", "750 863");

        List<String> departementsName = new ArrayList<String>(pop.keySet());
        ArrayList<String> departementsList = new ArrayList<String>(pop.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, departementsList);

        ListView listView = (ListView) findViewById(R.id.leaderbord_listview_endgame);
        listView.setAdapter(adapter);

    }

    private void initAff(){
        Intent intent = getIntent();

        text_score = (TextView) findViewById(R.id.finalscore_textview_endgame);
        text_score.setText(text_score_string + String.valueOf(intent.getIntExtra("PLAYER_SCORE",0)));
        text_name = (TextView) findViewById(R.id.namePlayer_textview_endgame);
        text_name.setText(intent.getStringExtra("PLAYER_NAME"));
        state = (TextView) findViewById(R.id.result_textview_endgame);
        if (intent.getIntExtra("PLAYER_SCORE",0) > 2){
            state.setText("Bravo, vous avez gagné !");
        } else {
            state.setText("Dommage, vous avez perdu !");
        }
    }
}
