package fr.esir.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import fr.esir.progm.wifidirectdemo.R;


public class Defi_slide extends AppCompatActivity {

    private TextView text_name_player,text_score_player,text_number_defi;
    private final String current_defi_string = "Défi n° ";
    private int score;
    private String player_name;
    private int nb_defi;
    private int mode;
    //
    private ImageView movement1_image,movement2_image,movement3_image;
    private final String[] mouvement_tab = {"UP", "RIGHT", "DOWN", "LEFT"};
    private ArrayList<String> mouvement_tab_user = new ArrayList<String>();
    private ArrayList<String> mouvement_tab_ref = new ArrayList<String>();
    private SwipeGestureDetector gestureDetector;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_slidedefis);
        initAff();
        startTime = System.nanoTime();
    }

    public void initAff(){
        Intent intent = getIntent();
        mode = intent.getIntExtra("MODE",0);

        text_name_player = (TextView) findViewById(R.id.namePlayer_textview_compass);
        player_name = intent.getStringExtra("PLAYER_NAME");
        text_name_player.setText(player_name);

        text_number_defi = (TextView) findViewById(R.id.title_defi_textview_compass);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);
        text_number_defi.setText(current_defi_string + String.valueOf(nb_defi));

        text_score_player = (TextView) findViewById(R.id.scorePlayer_textview_compass);

        score = intent.getIntExtra("PLAYER_SCORE", 0);
        if(score<10){
            text_score_player.setText("0"+String.valueOf(score));
        }else{
            text_score_player.setText(String.valueOf(score));
        }
        //
        movement1_image = findViewById(R.id.movement1_imageview_slidedefis);
        movement2_image = findViewById(R.id.movement2_imageview_slidedefis);
        movement3_image = findViewById(R.id.movement3_imageview_slidedefis);
        // Initialisation de la réponse
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(mouvement_tab.length);
            mouvement_tab_ref.add(i,mouvement_tab[randomIndex]);
        }
        //initialisation des images
        if (mouvement_tab_ref.get(0).equals("UP")) {
            movement1_image.setImageResource(R.drawable.ic_arrow_up_foreground);
        } else if (mouvement_tab_ref.get(0).equals("RIGHT")) {
            movement1_image.setImageResource(R.drawable.ic_arrow_right_foreground);
        } else if (mouvement_tab_ref.get(0).equals("DOWN")) {
            movement1_image.setImageResource(R.drawable.ic_arrow_down_foreground);
        } else if (mouvement_tab_ref.get(0).equals("LEFT")) {
            movement1_image.setImageResource(R.drawable.ic_arrow_left_foreground);
        }

        if (mouvement_tab_ref.get(1).equals("UP")) {
            movement2_image.setImageResource(R.drawable.ic_arrow_up_foreground);
        } else if (mouvement_tab_ref.get(1).equals("RIGHT")) {
            movement2_image.setImageResource(R.drawable.ic_arrow_right_foreground);
        } else if (mouvement_tab_ref.get(1).equals("DOWN")) {
            movement2_image.setImageResource(R.drawable.ic_arrow_down_foreground);
        } else if (mouvement_tab_ref.get(1).equals("LEFT")) {
            movement2_image.setImageResource(R.drawable.ic_arrow_left_foreground);
        }

        if (mouvement_tab_ref.get(2).equals("UP")) {
            movement3_image.setImageResource(R.drawable.ic_arrow_up_foreground);
        } else if (mouvement_tab_ref.get(2).equals("RIGHT")) {
            movement3_image.setImageResource(R.drawable.ic_arrow_right_foreground);
        } else if (mouvement_tab_ref.get(2).equals("DOWN")) {
            movement3_image.setImageResource(R.drawable.ic_arrow_down_foreground);
        } else if (mouvement_tab_ref.get(2).equals("LEFT")) {
            movement3_image.setImageResource(R.drawable.ic_arrow_left_foreground);
        }
        gestureDetector= new SwipeGestureDetector ( this );

    }

    public boolean dispatchTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent (event);
    }

    public void onSwipe (SwipeGestureDetector.SwipeDirection direction){
        String message = "";
        String directionTab = "";

        switch( direction) {
            case LEFT_TO_RIGHT:
                message = "Left to right swipe";
                directionTab = "RIGHT";
                break;
            case RIGHT_TO_LEFT:
                message = "Right to left swipe";
                directionTab = "LEFT";
                break;
            case TOP_TO_BOTTOM:
                message = "Top to bottom swipe";
                directionTab = "DOWN";
                break;
            case BOTTOM_TO_TOP:
                message = "Bottom to Top swipe";
                directionTab = "UP";
                break;
        }
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        mouvement_tab_user.add(directionTab);

        if(mouvement_tab_user.size()==3){
            long endTime = System.nanoTime();
            // Calculation of elapsed time in seconds
            long elapsedTimeMs = (endTime - startTime) / 1000000000;
            System.out.println(mouvement_tab_user.get(0)+"-"+mouvement_tab_user.get(1)+"-"+mouvement_tab_user.get(2));
            boolean same = true;
            String titlePopUp = "Félicitation";
            String textPopUp = "Vous avez réussi !\nVous avez mis "+elapsedTimeMs+" s";

            for (int i = 0; i < mouvement_tab_user.size(); i++) {
                // Vérifier que les éléments sont égaux
                if (!mouvement_tab_user.get(i).equals(mouvement_tab_ref.get(i))) {
                    // Les éléments ne sont pas égaux, afficher un message d'erreur
                    same = false;
                    titlePopUp = "Désolé !";
                    textPopUp = "Vous avez échoué, les mouvements ne correspondent pas !";
                }
            }

            if (elapsedTimeMs > 5000) {
                same = false;
                titlePopUp = "Désolé !";
                textPopUp = "Vous avez échoué, le temps est écoulé !\nVous avez mis "+elapsedTimeMs+" ms";
            }

            if(same){
                score+=1;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(Defi_slide.this);
            builder.setMessage(textPopUp).setTitle(titlePopUp);
            builder.setCancelable(false);
            builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent;
                    if(mode == 1){
                        intent = new Intent(Defi_slide.this, TrainingGameManager.class);
                    }else {
                        intent = new Intent(Defi_slide.this, OnePlayerGameManager.class);
                    }
                    intent.putExtra("PLAYER_NAME", player_name);
                    intent.putExtra("PLAYER_SCORE", score);
                    intent.putExtra("CURRENT_DEFIS", nb_defi);
                    startActivity(intent);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}



