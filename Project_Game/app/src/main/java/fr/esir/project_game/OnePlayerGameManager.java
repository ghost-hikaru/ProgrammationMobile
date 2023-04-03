package fr.esir.project_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;


public class OnePlayerGameManager extends Activity{
    private String current_name;
    private int current_score;
    private int nb_defi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        current_name = intent.getStringExtra("PLAYER_NAME");
        current_score = intent.getIntExtra("PLAYER_SCORE", 0);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);
        nb_defi++;
        //initAff();
        if (nb_defi > 3){
            launchEndGame();
        }
        else {
            Random rand = new Random();
            if (rand.nextInt(2) == 0) {
                launchDefiQuestions();
            } else {
                launchDefiSecouer();
            }
        }
    }

    public void launchEndGame(){
        // Start EndGame
        System.out.println("EndGame");
        Player player = new Player(current_name, current_score, OnePlayerGameManager.this);
        try {
            player.AddNewPlayer();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier");
            throw new RuntimeException(e);
        }

        Intent intent = new Intent(OnePlayerGameManager.this, EndGameActivity.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        startActivity(intent);
    }
    public void launchDefiQuestions(){
        System.out.println("Questions");
        // Start Defi_Questions
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_Questions.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiSecouer(){
        System.out.println("Shaking");
        // Start Shaking Game
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_Secouer.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }
}
