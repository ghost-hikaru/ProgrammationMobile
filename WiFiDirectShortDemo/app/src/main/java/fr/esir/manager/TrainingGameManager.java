package fr.esir.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.esir.game.Defi_Blindtest;
import fr.esir.game.Defi_Dessin;
import fr.esir.game.Defi_Questions;
import fr.esir.game.Defi_Secouer;
import fr.esir.game.Defi_compass;
import fr.esir.game.Defi_slide;
import fr.esir.Main.MainActivity;
import fr.esir.progm.wifidirectdemo.R;


//import com.bumptech.glide.Glide;


public class TrainingGameManager extends Activity {
    private int current_score;
    private int nb_defi;
    private String current_name;
    Button dessin_but,compass_but,shake_but,question_but,slide_but,blind_but,back_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choose_train);
        //
        dessin_but = findViewById(R.id.button_draw_choose);
        compass_but = findViewById(R.id.button_compass_choose);
        shake_but = findViewById(R.id.button_shake_choose);
        question_but = findViewById(R.id.button_question_choose);
        slide_but = findViewById(R.id.button_slider_choose);
        blind_but = findViewById(R.id.button_blind_test_choose);
        back_but = findViewById(R.id.button_back_home_choose);
        //
        /*ImageView GIF_train = (ImageView) findViewById(R.id.imageview_GIF_training);

        Glide.with(this)
                .asGif()
                .load(R.drawable.giphy)
                .into(GIF_train);*/
        //
        dessin_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDefiDraw();
            }
        });
        compass_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDefiCompass();
            }
        });
        shake_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDefiSecouer();
            }
        });
        question_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDefiQuestions();
            }
        });
        slide_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 launchDefiSlide();
            }
        });
        blind_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { launchDefiBlindtest(); }
        });
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingGameManager.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        current_name = intent.getStringExtra("PLAYER_NAME");
        current_score = intent.getIntExtra("PLAYER_SCORE", 0);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);
        nb_defi++;

    }

    private void launchDefiSlide() {
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(TrainingGameManager.this, Defi_slide.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("MODE",1);
        startActivity(intent);
    }

    public void launchDefiQuestions(){
        // Start fr.esir.game.Defi_Questions
        Intent intent = new Intent(TrainingGameManager.this, Defi_Questions.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("MODE",1);
        startActivity(intent);
    }

    public void launchDefiSecouer(){
        // Start Shaking Game
        Intent intent = new Intent(TrainingGameManager.this, Defi_Secouer.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("MODE",1);
        startActivity(intent);
    }

    public void launchDefiDraw(){
        // Start Defi_Draw
        Intent intent = new Intent(TrainingGameManager.this, Defi_Dessin.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("MODE",1);
        startActivity(intent);
    }

    public void launchDefiCompass(){
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(TrainingGameManager.this, Defi_compass.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("MODE",1);
        startActivity(intent);
    }

    public void launchDefiBlindtest(){
        // Start Defi_Blindtest
        Intent intent = new Intent(TrainingGameManager.this, Defi_Blindtest.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("MODE",1);
        startActivity(intent);
    }
}
