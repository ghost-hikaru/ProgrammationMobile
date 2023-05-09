package fr.esir.project_game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class Defi_compass extends AppCompatActivity implements SensorEventListener {
    private TextView text_name_player,text_score_player,text_number_defi, text_degree, text_content;
    private final String current_defi_string = "Défi n° ";
    private int score;
    private String player_name;
    private int nb_defi;
    private int mode;

    // device sensor manager
    private SensorManager SensorManage;
    // define the compass picture that will be use
    private ImageView compassimage;
    // record the angle turned of the compass picture
    private float DegreeStart = 0f;
    // target
    private int targetDegree;
    private long startTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compassdefi);
        initAff();
        startTime = System.nanoTime();
    }

    private void initAff() {
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
        text_content = findViewById(R.id.content_defi_textview_compass);
        text_degree = findViewById(R.id.textview_current_degree);
        //
        compassimage = (ImageView) findViewById(R.id.compass_image);
        //
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Générer un degré cible aléatoire entre 0 et 359
        Random random = new Random();
        targetDegree = random.nextInt(360);
        text_content.setText("Trouver l'orientation : "+targetDegree+"\nEn moins de 10 secondes !");

        if (mode == 1){
            Button back_button = findViewById(R.id.back_button_compass);
            back_button.setVisibility(Button.VISIBLE);

            back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_back = new Intent(Defi_compass.this, TrainingGameManager.class);
                    startActivity(intent_back);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // to stop the listener and save battery
        SensorManage.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // code for system's orientation sensor registered listeners
        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // get angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        text_degree.setText(Float.toString(degree));
        // Vibrate every 10 degrees
        /*if (Math.round(degree) % 10 == 0) {
            vibrator.vibrate(50); // Vibrate for 50 milliseconds
        }*/
        if ((int) degree == targetDegree){
            long endTime = System.nanoTime();
            // Calculation of elapsed time in milliseconds
            long elapsedTimeMs = (endTime - startTime) / 1000000;
            AlertDialog.Builder builder;
            if (elapsedTimeMs < 10000) {
                onPause();
                score += 1;
                builder = new AlertDialog.Builder(Defi_compass.this);
                builder.setMessage("Vous avez trouvé la bonne direction !\nVous avez mis "+elapsedTimeMs+" ms").setTitle("Félicitation");
                builder.setCancelable(false);
                builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent;
                        if (mode == 1) {
                            intent = new Intent(Defi_compass.this, TrainingGameManager.class);
                        } else {
                            intent = new Intent(Defi_compass.this, OnePlayerGameManager.class);
                        }
                        intent.putExtra("PLAYER_NAME", player_name);
                        intent.putExtra("PLAYER_SCORE", score);
                        intent.putExtra("CURRENT_DEFIS", nb_defi);
                        startActivity(intent);

                    }
                });
            } else {
                builder = new AlertDialog.Builder(Defi_compass.this);
                builder.setMessage("Vous avez trouvé la bonne direction ! Mais pas à temps :(\nVous avez mis "+elapsedTimeMs+" ms").setTitle("Dommage");
                builder.setCancelable(false);
                builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent;
                        if (mode == 1) {
                            intent = new Intent(Defi_compass.this, TrainingGameManager.class);
                        } else {
                            intent = new Intent(Defi_compass.this, OnePlayerGameManager.class);
                        }
                        intent.putExtra("PLAYER_NAME", player_name);
                        intent.putExtra("PLAYER_SCORE", score);
                        intent.putExtra("CURRENT_DEFIS", nb_defi);
                        startActivity(intent);

                    }
                });
            }
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // rotation animation - reverse turn degree degrees
        RotateAnimation ra = new RotateAnimation(DegreeStart, -degree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // set the compass animation after the end of the reservation status
        ra.setFillAfter(true);
        // set how long the animation for the compass image will take place
        ra.setDuration(210);
        // Start animation of compass image
        compassimage.startAnimation(ra);
        DegreeStart = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
