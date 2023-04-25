package fr.esir.compass;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Main extends AppCompatActivity implements SensorEventListener {

    private ImageView compassImage;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] lastAccelerometer = new float[3];
    private float[] lastMagnetometer = new float[3];
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];
    private Vibrator vibrator;
    private float currentDegree = 0f;
    private TextView degreeText,questionText;
    public int targetDegree;
    private boolean isCalibrated = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        // Garde l'écran allumé
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Initialise la vue de la boussole
        compassImage = findViewById(R.id.compass_image);
        degreeText = findViewById(R.id.degree_text);
        questionText = findViewById(R.id.degree_question);

        // Initialise le SensorManager et les capteurs
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Générer un degré cible aléatoire entre 0 et 359
        Random random = new Random();
        targetDegree = random.nextInt(360);
        questionText.setText("Trouver l'orientation : "+targetDegree);

        // Initialise le bouton de calibration
        Button calibrateButton = findViewById(R.id.calibrate_button);
        calibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCalibrated = true;
                Toast.makeText(Main.this, "Calibrated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, accelerometer);
        sensorManager.unregisterListener(this, magnetometer);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        String orientation_text = "";
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, lastAccelerometer, 0, event.values.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, lastMagnetometer, 0, event.values.length);
        }

        if (isCalibrated && SensorManager.getRotationMatrix(rotationMatrix, null, lastAccelerometer, lastMagnetometer)) {
            float[] rotationMatrixZ = new float[9];
            SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, rotationMatrixZ);
            SensorManager.getOrientation(rotationMatrixZ, orientation);
            float azimuthInRadians = orientation[0];
            float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);

            RotateAnimation rotateAnimation = new RotateAnimation(currentDegree, -azimuthInDegrees,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);

            rotateAnimation.setDuration(100);
            rotateAnimation.setFillAfter(true);
            compassImage.startAnimation(rotateAnimation);

            currentDegree = -azimuthInDegrees;
            if (currentDegree >= 337.5 || currentDegree < 22.5) {
                orientation_text = "N";
            } else if (currentDegree >= 22.5 && currentDegree < 67.5) {
                orientation_text = "NE";
            } else if (currentDegree >= 67.5 && currentDegree < 112.5) {
                orientation_text = "E";
            } else if (currentDegree >= 112.5 && currentDegree < 157.5) {
                orientation_text = "SE";
            } else if (currentDegree >= 157.5 && currentDegree < 202.5) {
                orientation_text = "S";
            } else if (currentDegree >= 202.5 && currentDegree < 247.5) {
                orientation_text = "SW";
            } else if (currentDegree >= 247.5 && currentDegree < 292.5) {
                orientation_text = "W";
            } else if (currentDegree >= 292.5 && currentDegree < 337.5) {
                orientation_text = "NW";
            }
            String degree = String.format("%.0f", currentDegree) + (char) 0x00B0 + " " + orientation_text; // Add degree symbol to the end
            degreeText.setText(degree);

            // Vibrate every 10 degrees
            if (Math.round(currentDegree) % 10 == 0) {
                vibrator.vibrate(50); // Vibrate for 50 milliseconds
            }

            if ((int) currentDegree == targetDegree){
                System.out.println("MEME DEGREE");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Vous êtes dans la bonne direction !").setTitle("Bravo vous avez trouvé")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action à exécuter lorsque l'utilisateur appuie sur le bouton OK
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }


}
