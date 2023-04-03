package fr.esir.project_game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingGameActivity extends Activity {

    TextView text_nb_player;
    EditText name_player_edit;
    SeekBar nb_player;
    Button onOff,discover;
    public static final String TAG = "SettingGame :";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting_game);


        initSettting();

    }


    private void initSettting(){
        onOff = (Button) findViewById(R.id.p2POnOff_button_setting);
        discover = (Button) findViewById(R.id.discover_button_setting);
        text_nb_player = (TextView)  findViewById(R.id.nb_player_textview_setting_game);
        nb_player = (SeekBar) findViewById(R.id.seekBar_player_setting_game);
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

        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        discover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}


