package fr.esir.project_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to manage the BlindTest challenge
 */
public class Defi_Blindtest extends Activity {
    private String answer_player;
    private String answer;
    private TextView text_name_player;
    private TextView text_score_player;
    private TextView text_number_defi;
    private final String current_defi_string = "Défi n° ";
    private final String play_music_string = "Jouer la musique";
    private final String stop_music_string = "Arrêter la musique";
    private int nb_defi;
    private int mode;
    private int current_score;
    private String current_name;
    private EditText edit_answer;
    private Button valid_button;
    private int resId;
    private Uri uri;
    private boolean isPlaying = false;
    private MediaPlayer mediaPlayer;
    private boolean fromFile;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_blindtest);

        InitAff();
        long startTime = System.nanoTime();

        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long endTime = System.nanoTime();
                // Calculation of elapsed time in milliseconds
                long elapsedTimeMs = (endTime - startTime) / 1000000;
                // Stop the music if it is playing
                shouldStop();
                // Get the answer from the player
                answer_player = edit_answer.getText().toString();
                // Create a dialog to display the result of the challenge
                AlertDialog.Builder builder = new AlertDialog.Builder(Defi_Blindtest.this);
                builder.setTitle("Résultat de la question : ");
                if (answer_player.toLowerCase().replaceAll(" ", "").equals(answer.toLowerCase().replaceAll("_", ""))){
                    builder.setMessage("Bravo vous avez entré la bonne réponse\nVous avez mis "+elapsedTimeMs+" ms");
                    current_score += 1;
                }
                else{
                    builder.setMessage("Dommage vous avez entré la mauvaise réponse\nValeur attendu : "+answer.replaceAll("_", " ")+"\nValeur entrée : "+answer_player);
                }
                builder.setCancelable(false);
                builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        if(mode == 1){
                            intent = new Intent(Defi_Blindtest.this, TrainingGameManager.class);
                        }else {
                            intent = new Intent(Defi_Blindtest.this, OnePlayerGameManager.class);
                        }
                        intent.putExtra("PLAYER_NAME", current_name);
                        intent.putExtra("PLAYER_SCORE", current_score);
                        intent.putExtra("CURRENT_DEFIS", nb_defi);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * Init the affectation of the variables
     */
    public void InitAff(){
        Intent intent = getIntent();
        mode = intent.getIntExtra("MODE",0);

        current_name = intent.getStringExtra("PLAYER_NAME");
        current_score = intent.getIntExtra("PLAYER_SCORE", 0);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);

        text_name_player = (TextView) findViewById(R.id.namePlayer_textview_training);
        text_name_player.setText(current_name);

        text_score_player = (TextView) findViewById(R.id.scorePlayer_textview_training);

        text_number_defi = (TextView) findViewById(R.id.title_defi_textview_training);
        text_number_defi.setText(current_defi_string+String.valueOf(nb_defi));

        if(current_score<10){
            text_score_player.setText("0"+String.valueOf(current_score));
        }else{
            text_score_player.setText(String.valueOf(current_score));
        }

        // Select a random song from the files directory, and set the answer to the name of the song
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            selectRandomFromFilesDir();
            fromFile = true;
        }
        else {
            selectRandomFromRawDir();
            fromFile = false;
        }

        // Load the musique on a button
        Button play_button = (Button) findViewById(R.id.play_music_button_blindtest);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.stop();
                    isPlaying = false;
                    play_button.setText(play_music_string);
                }
                else {
                    if (fromFile)
                        playMp3(uri);
                    else
                        playMp3(resId);
                    isPlaying = true;
                    play_button.setText(stop_music_string);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            isPlaying = false;
                            play_button.setText(play_music_string);
                        }
                    });
                }
            }
        });

        edit_answer = (EditText) findViewById(R.id.response_edittext_training);
        valid_button = (Button) findViewById(R.id.validate_button_training);

        if (mode == 1){
            Button back_menu = (Button) findViewById(R.id.back_menu_button_question);
            back_menu.setVisibility(Button.VISIBLE);
            back_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shouldStop();
                    Intent intent_back = new Intent(Defi_Blindtest.this, TrainingGameManager.class);
                    startActivity(intent_back);
                }
            });
        }
    }

    /**
     * Select a random song from the files directory, and set the answer to the name of the song
     */
    private void selectRandomFromFilesDir() {
        File filesDir = getFilesDir();
        File[] files = filesDir.listFiles();

        List<File> mp3Files = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".mp3")) {
                System.out.println("Fichier MP3 trouvé : " + file.getName());
                mp3Files.add(file);
            }
        }

        if (mp3Files.isEmpty()) {
            // Aucun fichier MP3 trouvé, gestion de l'erreur ou sortie de la méthode
            fromFile = false;
            selectRandomFromRawDir();
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(mp3Files.size());
        File randomMp3File = mp3Files.get(randomIndex);

        String filePath = randomMp3File.getAbsolutePath();
        answer = randomMp3File.getName().replace(".mp3", "");
        uri = Uri.parse(filePath);
    }

    /**
     * Select a random mp3 file from the res/raw directory
     */
    private void selectRandomFromRawDir() {
        Resources resources = this.getResources();
        String packageName = this.getPackageName();
        System.out.println("Package name : " + packageName);

        int resourceId = resources.getIdentifier("mp3_files", "array", packageName);
        String[] mp3Files = resources.getStringArray(resourceId);

        if (mp3Files.length == 0) {
            return;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(mp3Files.length);
        answer = mp3Files[randomIndex];
        resId = resources.getIdentifier(answer, "raw", packageName);
    }

    /**
     * Play the mp3 file
     * @param uri the uri of the mp3 file
     */
    private void playMp3(Uri uri) {
        mediaPlayer = MediaPlayer.create(this, uri);
        mediaPlayer.start();
    }

    private void playMp3(int resId) {
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();
    }

    /**
     * Stop the mp3 file if it's playing and release the media player
     */
    private void shouldStop(){
        if (isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
