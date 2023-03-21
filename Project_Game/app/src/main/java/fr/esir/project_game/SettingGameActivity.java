package fr.esir.project_game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingGameActivity extends Activity {
    String player_lead = "leaderboard.txt";
    int score = 0;
    TextView text_nb_player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_game);

        EditText name_player = (EditText) findViewById(R.id.name_player_edittext_setting_game);
        SeekBar nb_player = (SeekBar) findViewById(R.id.seekBar_player_setting_game);
        text_nb_player = (TextView)  findViewById(R.id.nb_player_textview_setting_game);



        // Regarder si le joueur existe déja et reprendre son score
        /*
        String newPlayer = name_player+";"+score;

        try (FileOutputStream fos = openFileOutput(chellenge_file, Context.MODE_PRIVATE)) {
            fos.write(defi.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        nb_player.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Mettre à jour la valeur du TextView
                text_nb_player.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Ne rien faire
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Ne rien faire
            }
        });

    }
}

