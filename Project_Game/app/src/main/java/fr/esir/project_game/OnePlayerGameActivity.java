package fr.esir.project_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OnePlayerGameActivity extends Activity {
    String player_lead = "leaderboard.txt";
    String challenge_file = "challenge.csv";

    String defi_file;
    TextView text_name_player;
    TextView text_score_player;
    TextView content_defi;
    Button valid_button;
    EditText edit_answer;
    Player currentPlayer;
    String current_name;
    int current_score;
    Defi current_defi;

    String current_defi_string = "Défi n° ";
    int current_defi_id = 1;
    TextView text_number_defi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_training);
        defi_file = this.read();
        Intent intent = getIntent();
        current_name = intent.getStringExtra("PLAYER_NAME");
        current_score = intent.getIntExtra("PLAYER_SCORE",0);
        initAff();
        treatmentAnswer();
    }

    public void initAff(){
        text_name_player = (TextView) findViewById(R.id.namePlayer_textview_training);
        text_name_player.setText(current_name);

        text_score_player = (TextView) findViewById(R.id.scorePlayer_textview_training);

        text_number_defi = (TextView) findViewById(R.id.title_defi_textview_training);
        text_number_defi.setText(current_defi_string+String.valueOf(current_defi_id++));

        if(current_score<10){
            text_score_player.setText("0"+String.valueOf(current_score));
        }else{
            text_score_player.setText(String.valueOf(current_score));
        }

        current_defi = recupDefi();
        String affQuestion = "Question :\n"+current_defi.getQuestion();

        content_defi = (TextView) findViewById(R.id.content_defi_textview_training);
        content_defi.setText(affQuestion);
    }

    public void treatmentAnswer(){
        edit_answer = (EditText) findViewById(R.id.response_edittext_training);
        valid_button = (Button) findViewById(R.id.validate_button_training);

        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = edit_answer.getText().toString().toLowerCase();
                String TrueAnswer = current_defi.getAnswer().toLowerCase();

                AlertDialog.Builder builder = new AlertDialog.Builder(OnePlayerGameActivity.this);
                builder.setTitle("Résultat de la question : ");
                if(answer.equals(TrueAnswer)){
                    builder.setMessage("Bravo vous avez entré la bonne réponse");
                    current_score++;
                }else{
                    builder.setMessage("Désolé ce n'est pas la bonne réponse\nValeur attendu : "+TrueAnswer+"\nValeur entrée : "+answer);
                }
                builder.setPositiveButton("Question suivante", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (current_defi_id > 3){
                            Intent intent = new Intent(OnePlayerGameActivity.this, EndGameActivity.class);
                            intent.putExtra("PLAYER_NAME", current_name);
                            intent.putExtra("PLAYER_SCORE", current_score);
                            startActivity(intent);
                        }
                        else {
                            // défi suivant
                            ((EditText) findViewById(R.id.response_edittext_training)).setText("");
                            initAff();
                        }
                    }
                });
                builder.show();
            }
        });

    }
    public Defi recupDefi(){
        String AllDefis  = read();
        Defi defi = new Defi_Questions(this, AllDefis);
        defi.LoadDefi();
        return defi;
    }

    public String read(){
        System.out.println("Début de la lecture du fichier");
        File file = new File(getFilesDir() +"/" + challenge_file);
        StringBuilder sb = new StringBuilder();
        String st;
        try {
            BufferedReader br= new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null)
                sb.append(st);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Fin de la lecture du fichier");
        System.out.println("Contenu du fichier : "+sb.toString());
        return sb.toString();
    }

}
