package fr.esir.game;
//content://com.example.android.wifidirect.fileprovider/gameway/gameway.txt
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MultiPlayerGameManager extends Activity {
    private String current_name;
    private String file_path_string;
    private int current_score;
    private int nb_defi;
    ArrayList<Integer> tab_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        current_name = intent.getStringExtra("PLAYER_NAME");
        current_score = intent.getIntExtra("PLAYER_SCORE", 0);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);
        tab_game = getIntent().getIntegerArrayListExtra("ArrayList");

        if(tab_game.size() == 0){
            launchEndGame();
        }else{
            int temp = tab_game.get(0);
            tab_game.remove(0);
            lauchGame(temp);
        }

    }

    private void lauchGame(int games){
        switch (games) {
            case 0:
                //launchDefiQuestions();
                launchDefiSlide();
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
        }
    }

    private void launchDefiSlide() {
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_slide.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchEndGame(){
        // Start EndGame
        Player player = new Player(current_name, current_score, MultiPlayerGameManager.this);
        try {
            player.AddNewPlayer();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier");
            throw new RuntimeException(e);
        }

        Intent intent = new Intent(MultiPlayerGameManager.this, EndGameActivity.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        startActivity(intent);
    }
    public void launchDefiQuestions(){
        // Start fr.esir.game.Defi_Questions
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Questions.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiSecouer(){
        // Start Shaking Game
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Secouer.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiDraw(){
        // Start Defi_Draw
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Dessin.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }

    public void launchDefiCompass(){
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_compass.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        startActivity(intent);
    }


}