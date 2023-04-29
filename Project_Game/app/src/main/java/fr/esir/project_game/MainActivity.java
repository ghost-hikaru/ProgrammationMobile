package fr.esir.project_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class MainActivity extends Activity {
    String player_lead = "leaderboard.txt";
    String chellenge_file = "challenge.csv";

    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //SetFiles();
        //delete();
        
        Button play_but = (Button) findViewById(R.id.button_play_home);
        Button train_but = (Button) findViewById(R.id.button_train_home);
        Button challenge_but = (Button) findViewById(R.id.button_add_challenge_home);
        Button leader_but = (Button) findViewById(R.id.button_leaderboard_home);
        ImageButton Logo_button = (ImageButton)findViewById(R.id.image_logo_home);

        Logo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                });
                mediaPlayer.start();
            }
        });

        play_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                Intent intent = new Intent(MainActivity.this, SettingGameActivity.class);
                startActivity(intent);
            }
        });

        train_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Début d'une nouvelle partie 1 joueur");
                builder.setMessage("Veuillez entrer votre nom de joueur : ");

                final EditText editText_dialog = new EditText(MainActivity.this);
                builder.setView(editText_dialog);

                builder.setPositiveButton("Commencer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name_player = editText_dialog.getText().toString();
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        Intent intent = new Intent(MainActivity.this, OnePlayerGameManager.class);
                        intent.putExtra("PLAYER_NAME",name_player);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        challenge_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddChallengeActivity.class);
                startActivity(intent);
            }
        });

        leader_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

        //TextView text = (TextView) findViewById(R.id.top_right_textview_home);
        //text.setText(ShowContent());

    }


    public void SetFiles() {
        String[] files = this.fileList();
        boolean existe = false;
        int i = 0;
        if (files.length > 0) {
            while (!existe) {
                if (files[i].equals(player_lead)) {
                    existe = true;
                } else {
                    i++;
                }
            }
            if (!existe) {
                File file = new File(this.getFilesDir(), player_lead);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            boolean existeChallenge = false;
            int j = 0;
            while (!existeChallenge) {
                if (files[j].equals(chellenge_file)) {
                    existeChallenge = true;
                } else {
                    j++;
                }
            }
            if (!existeChallenge) {
                File file = new File(this.getFilesDir(), chellenge_file);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        File file = new File(this.getFilesDir(), chellenge_file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String ShowContent() {
        try {
            FileInputStream fis = openFileInput(chellenge_file);

            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                sb.append((char) ch);
            }
            fis.close();
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Fichier vide";
    }

    public boolean delete(){
        File file = new File(this.getFilesDir(),player_lead);
        if(file.delete()){
            return true;
        }
        return false;
    }

}


/*
private MediaPlayer player;
// dans le Oncreate
player = null;
/////
if(player == null){
    player = MediaPlayer.create(MainActivity.this, R.raw.defeat_sound);
}
player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        player.release();
        player=null;
    }
});
player.start();
 */