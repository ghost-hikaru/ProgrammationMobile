package fr.esir.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.esir.progm.wifidirectdemo.R;


public class LeaderBoardActivity extends Activity {
    Map<String, String> leaderbord = new HashMap<>();
    Button back_to_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_leaderboard);

        // Display the leaderboard
        readScore();

        // Button to return to the main menu
        back_to_menu = (Button) findViewById(R.id.return_button_endgame);
        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Read the leaderboard file and display it
     */
    private void readScore() {
        Leaderbord leaderbord_class = new Leaderbord(LeaderBoardActivity.this);
        leaderbord = leaderbord_class.getLeaderbord();
        ArrayList<String> score_player = new ArrayList<String>();

        for (Map.Entry<String, String> entry : leaderbord.entrySet()) {
            String playerName = entry.getKey();
            String playerScore = entry.getValue();
            String scoreString = playerName + " : " + playerScore;
            score_player.add(scoreString);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, score_player);
        ListView listView = (ListView) findViewById(R.id.leaderbord_listview_endgame);
        listView.setAdapter(adapter);
    }
}
