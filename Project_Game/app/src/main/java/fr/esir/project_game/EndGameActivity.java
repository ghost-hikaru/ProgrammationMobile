package fr.esir.project_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EndGameActivity extends Activity {
    Map<String, String> leaderbord = new HashMap<>();
    TextView text_score;
    TextView text_name;
    TextView state;
    String text_score_string = "Score final : ";
    Button back_to_menu;
    MediaPlayer mediaPlayer = null;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_endgame);

        // Call the function to initialize the display
        initAff();

        // Button to return to the main menu
        back_to_menu = (Button) findViewById(R.id.return_button_endgame);
        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    /**
     * Initialize the display
     */
    private void initAff() {
        Intent intent = getIntent();
        // Get the score and the name of the player and display it
        text_score = (TextView) findViewById(R.id.finalscore_textview_endgame);
        text_score.setText(text_score_string + String.valueOf(intent.getIntExtra("PLAYER_SCORE", 0)));
        text_name = (TextView) findViewById(R.id.namePlayer_textview_endgame);
        text_name.setText(intent.getStringExtra("PLAYER_NAME"));

        // Display the leaderboard
        readScore();

        // Display the result of the game
        state = (TextView) findViewById(R.id.result_textview_endgame);
        if (intent.getIntExtra("PLAYER_SCORE", 0) > 2) {
            state.setText("Bravo, vous avez gagné !");
            mediaPlayer = MediaPlayer.create(EndGameActivity.this, R.raw.victory);
        } else {
            state.setText("Dommage, vous avez perdu !");
            mediaPlayer = MediaPlayer.create(EndGameActivity.this, R.raw.defeat_sound);
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                mediaPlayer=null;
            }
        });
        mediaPlayer.start();
    }

    /**
     * Read the leaderboard file and display it
     */
    private void readScore() {
        Leaderbord leaderbord_class = new Leaderbord(EndGameActivity.this);
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
