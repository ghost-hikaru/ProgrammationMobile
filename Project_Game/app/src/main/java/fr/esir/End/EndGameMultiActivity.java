package fr.esir.End;

import static fr.esir.progm.wifidirectdemo.WifiDirectActivity.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.esir.LeaderBoard.Leaderbord;
import fr.esir.Main.MainActivity;
import fr.esir.progm.wifidirectdemo.DeviceListFragment;
import fr.esir.progm.wifidirectdemo.R;

public class EndGameMultiActivity extends Activity{
    private Map<String, String> leaderbord = new HashMap<>();
    private TextView text_score_j1,text_score_j2,text_name_j1,text_name_j2,state;

    private final String text_score_string = "Score final : ";
    private Button back_to_menu,exchange;
    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = true;

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
        setContentView(R.layout.layout_endgame_multi);

        // Call the function to initialize the display
        initAff();

        // Call the function to get the name of the other player and his score (vice-versa)
        //getInfoP2()

        // Button to return to the main menu
        back_to_menu = (Button) findViewById(R.id.return_button_endgame);
        back_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WifiP2pInfo wifiP2pInfo = new WifiP2pInfo();
                //WifiP2pManager wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

                shouldStop();
                disconection();
                Intent intent = new Intent(EndGameMultiActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Button to exchange the score
        exchange = findViewById(R.id.echange_button_endgame);
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String score_temp = text_score_j1.getText().toString();
                text_score_j1.setText(text_score_j2.getText().toString());
                text_score_j2.setText(score_temp);
            }
        });

    }

    /**
     * Initialize the display
     */
    private void initAff() {
        Intent intent = getIntent();
        // Get the score and the name of the player and display it
        text_score_j1 = (TextView) findViewById(R.id.scorePlayer_textview_endgame);
        text_name_j1 = (TextView) findViewById(R.id.namePlayer_textview_endgame);

        text_score_j1.setText(text_score_string + String.valueOf(intent.getIntExtra("PLAYER_SCORE", 0)));
        text_name_j1.setText(intent.getStringExtra("PLAYER_NAME"));

        text_score_j2 = (TextView) findViewById(R.id.scorePlayer_textview_endgame2);
        text_name_j2 = (TextView) findViewById(R.id.namePlayer_textview_endgame2);
        text_score_j2.setText(text_score_string + String.valueOf(intent.getIntExtra("PLAYER_SCORE_2", 0)));
        text_name_j2.setText("J2"/*intent.getStringExtra("PLAYER_NAME_J2")*/);

        // Display the leaderboard
        readScore();

        // Display the result of the game
        state = (TextView) findViewById(R.id.finalscore_textview_endgame);
        if (intent.getIntExtra("PLAYER_SCORE", 0) > 0/*Score J2*/) { //J1 gagne

            state.setText("Bravo J1, vous avez gagné !");
            mediaPlayer = MediaPlayer.create(EndGameMultiActivity.this, R.raw.victory);
        } else {

            state.setText("Dommage, vous avez perdu !");
            mediaPlayer = MediaPlayer.create(EndGameMultiActivity.this, R.raw.defeat_sound);
        }

        if (intent.getIntExtra("PLAYER_SCORE", 0) < 0/*Score J2*/) { //J2 gagne

            state.setText("Bravo J2, vous avez gagné !");
            mediaPlayer = MediaPlayer.create(EndGameMultiActivity.this, R.raw.victory);
        } else {

            state.setText("Dommage, vous avez perdu !");
            mediaPlayer = MediaPlayer.create(EndGameMultiActivity.this, R.raw.defeat_sound);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                isPlaying = false;
                mediaPlayer.release();
            }
        });
        mediaPlayer.start();
    }

    /**
     * Read the leaderboard file and display it
     */
    private void readScore() {
        Leaderbord leaderbord_class = new Leaderbord(EndGameMultiActivity.this);
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

    /**
     * Method to stop the music if it isn't the case
     */
    private void shouldStop(){
        if (isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private void disconection() {
        WifiP2pManager manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        WifiP2pManager.Channel channel = manager.initialize(this, getMainLooper(), null);

        if (manager != null && channel != null) {
                manager.removeGroup(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "Disconnect success");
                    }

                    @Override
                    public void onFailure(int reason) {
                        Log.d(TAG, "Disconnect failed: " + reason);
                    }
                });
        }
        /*Socket socket = null;
        InetAddress hostAddress = info.groupOwnerAddress;
        int port = 8988;
        try {
            socket = new Socket(hostAddress, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

    }

}
