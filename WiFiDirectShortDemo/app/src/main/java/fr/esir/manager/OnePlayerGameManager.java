package fr.esir.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;
import java.util.Random;

import fr.esir.game.Defi_Blindtest;
import fr.esir.game.Defi_Dessin;
import fr.esir.game.Defi_Questions;
import fr.esir.game.Defi_Secouer;
import fr.esir.game.Defi_compass;
import fr.esir.game.Defi_slide;
import fr.esir.End.EndGameActivity;


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

        if (nb_defi > 3){
            launchEndGame();
        }
        else {
            Random rand = new Random();
            int random = rand.nextInt(6);
            switch (random) {
                case 0:
                    launchDefiQuestions();
                    break;
                case 1:
                    launchDefiDraw();
                    break;
                case 2:
                    launchDefiSecouer();
                    break;
                case  3:
                    launchDefiCompass();
                    break;
                case  4:
                    launchDefiSlide();
                    break;
                case 5 :
                    launchBlindTest();
                    break;
            }
        }
    }
    private void launchBlindTest() {
        // Start BlindTest
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_Blindtest.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }
    private void launchDefiSlide() {
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_slide.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchEndGame(){
        // Start EndGame
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
        // Start fr.esir.game.Defi_Questions
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_Questions.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiSecouer(){
        // Start Shaking Game
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_Secouer.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiDraw(){
        // Start Defi_Draw
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_Dessin.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiCompass(){
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(OnePlayerGameManager.this, Defi_compass.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }
}
