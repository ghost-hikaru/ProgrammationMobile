package fr.esir.compass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class Main extends AppCompatActivity implements SensorEventListener {

    // device sensor manager
    private SensorManager SensorManage;
    // define the compass picture that will be use
    private ImageView compassimage;
    // record the angle turned of the compass picture
    private float DegreeStart = 0f;
    TextView DegreeTV,questionText;
    public int targetDegree;
    //private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        //
        compassimage = (ImageView) findViewById(R.id.compass_image);
        // TextView that will display the degree
        DegreeTV = (TextView) findViewById(R.id.degree_text);
        questionText = (TextView) findViewById(R.id.degree_question);
        // initialize your android device sensor capabilities
        SensorManage = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Générer un degré cible aléatoire entre 0 et 359
        Random random = new Random();
        targetDegree = random.nextInt(360);
        questionText.setText("Trouver l'orientation : "+targetDegree);
        //vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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
        DegreeTV.setText("Heading: " + Float.toString(degree) + " degrees");
        // Vibrate every 10 degrees
        /*if (Math.round(degree) % 10 == 0) {
            vibrator.vibrate(50); // Vibrate for 50 milliseconds
        }*/
        if ((int) degree == targetDegree){
            onPause();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Vous êtes dans la bonne direction !").setTitle("Bravo vous avez trouvé")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Action à exécuter lorsque l'utilisateur appuie sur le bouton OK
                            Random random = new Random();
                            targetDegree = random.nextInt(360);
                            questionText.setText("Trouver l'orientation : "+targetDegree);
                            onResume();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // rotation animation - reverse turn degree degrees
        RotateAnimation ra = new RotateAnimation(DegreeStart, -degree,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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