package fr.esir.game;

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

import fr.esir.progm.wifidirectdemo.R;

public class Defi_Questions extends Activity{

    private String categorie;
    private String question;
    private String answer_player;
    private String answer;
    private Activity Ctx;
    private String challenge_file = "challenge.csv";
    private TextView text_name_player;
    private TextView text_score_player;
    private TextView text_number_defi;
    private final String current_defi_string = "Défi n° ";
    private int nb_defi;
    private int mode;
    private int current_score;
    private String current_name;
    private TextView content_defi;
    private EditText edit_answer;
    private Button valid_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question);
        InitAff();
        long startTime = System.nanoTime();
        valid_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long endTime = System.nanoTime();
                // Calculation of elapsed time in seconds
                long elapsedTimeMs = (endTime - startTime) / 1000000000;
                answer_player = edit_answer.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(Defi_Questions.this);
                builder.setTitle("Résultat de la question : ");
                if (answer_player.toLowerCase().replaceAll(" ", "").equals(answer.toLowerCase().replaceAll(" ", ""))){
                    builder.setMessage("Bravo vous avez entré la bonne réponse\nVous avez mis "+elapsedTimeMs+" ms");
                    current_score += 1;
                }
                else{
                    builder.setMessage("Dommage vous avez entré la mauvaise réponse\nValeur attendu : "+answer+"\nValeur entrée : "+answer_player);
                }
                builder.setCancelable(false);
                builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        if(mode == 1){
                            intent = new Intent(Defi_Questions.this, TrainingGameManager.class);
                        }else {
                            intent = new Intent(Defi_Questions.this, OnePlayerGameManager.class);
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

        this.challenge_file = read();
        LoadDefi();
        System.out.println("Défi chargé");
        String affQuestion = "Question :\n"+question;

        content_defi = (TextView) findViewById(R.id.content_defi_textview_training);
        content_defi.setText(affQuestion);
        edit_answer = (EditText) findViewById(R.id.response_edittext_training);
        valid_button = (Button) findViewById(R.id.validate_button_training);

        if (mode == 1){
            Button back_menu = (Button) findViewById(R.id.back_menu_button_question);
            back_menu.setVisibility(Button.VISIBLE);
            back_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_back = new Intent(Defi_Questions.this, TrainingGameManager.class);
                    startActivity(intent_back);
                }
            });
        }
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

    public void LoadDefi() {
        // transformation en tableau --> 1 case = 1 défis
        List<String> listDefis = new ArrayList<>();
        listDefis = Arrays.asList(challenge_file.split(";"));

        System.out.println("loadDefi 1");

        // on choisit un défis au hasard
        int index = randomInt(listDefis.size());

        // on récupère le défis
        List<String> defi = Arrays.asList(listDefis.get(index).split(","));

        /**
        // tant que l'on a pas un défi question
        while (!defi.get(0).equals(categorie)) {
            // on choisit un défis au hasard
            index = randomInt(listDefis.size());
            // on récupère le défis
            defi = new ArrayList<>();
            defi = Arrays.asList(listDefis.get(index).split(","));
        }*/
        // Initialisation du défis
        this.question = defi.get(0);
        this.answer = defi.get(1);
    }

    public static int randomInt(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }
}
