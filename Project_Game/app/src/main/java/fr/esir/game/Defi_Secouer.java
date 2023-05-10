package fr.esir.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.esir.progm.wifidirectdemo.R;

public class Defi_Secouer extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ProgressBar shakeProgressBar;
    private int progress = 0;
    private TextView NbSecouage;
    private TextView text_name_player;
    private TextView text_score_player;
    private TextView text_number_defi;
    private Intent intent;
    private final String current_defi_string = "Défi n° ";
    private int score;
    private String player_name;
    private int nb_defi;
    private int mode;
    private long startTime;
    private float SHAKE_THRESHOLD = 50.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shakedefi);
        intent = getIntent();
        initAff();
        startTime = System.nanoTime();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    private void initAff(){
        mode = intent.getIntExtra("MODE",0);

        shakeProgressBar = findViewById(R.id.shakeProgressBar_shake);
        NbSecouage = findViewById(R.id.nombreDeSecouage_shake);

        text_name_player = (TextView) findViewById(R.id.namePlayer_textview_shake);
        player_name = intent.getStringExtra("PLAYER_NAME");
        text_name_player.setText(player_name);

        text_number_defi = (TextView) findViewById(R.id.title_defi_textview_shake);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);
        text_number_defi.setText(current_defi_string + String.valueOf(nb_defi));

        text_score_player = (TextView) findViewById(R.id.scorePlayer_textview_shake);
        score = intent.getIntExtra("PLAYER_SCORE", 0);
        if(score<10){
            text_score_player.setText("0"+String.valueOf(score));
        }else{
            text_score_player.setText(String.valueOf(score));
        }

        if (mode == 1){
            Button back_menu = (Button) findViewById(R.id.back_menu_button_shake);
            back_menu.setVisibility(Button.VISIBLE);
            back_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_back = new Intent(Defi_Secouer.this, TrainingGameManager.class);
                    startActivity(intent_back);
                }
            });
        }
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float accelerationX = sensorEvent.values[0];
                float accelerationY = sensorEvent.values[1];
                float accelerationZ = sensorEvent.values[2];
                double accelerationMagnitude = Math.sqrt(Math.pow(accelerationX, 2) + Math.pow(accelerationY, 2) + Math.pow(accelerationZ, 2));
                if (accelerationMagnitude > SHAKE_THRESHOLD) {
                    // Augmenter la valeur de la barre en fonction de l'accélération
                    progress += (int) (accelerationMagnitude / SHAKE_THRESHOLD * 10);
                    if (progress >= 500) {
                        long endTime = System.nanoTime();
                        // Calculation of elapsed time in seconds
                        long elapsedTimeMs = (endTime - startTime) / 1000000000;
                        SHAKE_THRESHOLD = 5000000000000000f;
                        progress = 500;
                        AlertDialog.Builder builder;
                        if (elapsedTimeMs < 10000) {
                            score++;
                            builder = new AlertDialog.Builder(Defi_Secouer.this);
                            builder.setTitle("Bravo, vous avez réussi !\nVous avez mis "+elapsedTimeMs+" ms");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent;
                                    if (mode == 1) {
                                        intent = new Intent(Defi_Secouer.this, TrainingGameManager.class);
                                    } else {
                                        intent = new Intent(Defi_Secouer.this, OnePlayerGameManager.class);
                                    }
                                    intent.putExtra("PLAYER_NAME", player_name);
                                    intent.putExtra("PLAYER_SCORE", score);
                                    intent.putExtra("CURRENT_DEFIS", nb_defi);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            builder = new AlertDialog.Builder(Defi_Secouer.this);
                            builder.setTitle("Dommage, vous n'y êtes pas arrivé à temps !\nVous avez mis "+elapsedTimeMs+" ms");
                            builder.setCancelable(false);
                            builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Defi_Secouer.this, Defi_Secouer.class);
                                    intent.putExtra("PLAYER_NAME", player_name);
                                    intent.putExtra("PLAYER_SCORE", score);
                                    intent.putExtra("CURRENT_DEFIS", nb_defi);
                                    startActivity(intent);
                                }
                            });
                        }
                        builder.show();
                    }
                    NbSecouage.setText(String.valueOf(progress/5)+ " %");
                    updateProgressBar();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // Ne rien faire
        }
    };

    private void updateProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                shakeProgressBar.setProgress(progress);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}
//android:progressDrawable="@drawable/phone_shape"
