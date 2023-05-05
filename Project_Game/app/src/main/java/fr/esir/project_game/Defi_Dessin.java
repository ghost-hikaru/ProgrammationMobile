package fr.esir.project_game;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Defi_Dessin extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
    private ImageView imageView;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private float startX = -1, startY = -1, lastX = -1, lastY = -1;
    private boolean isDrawing = false;
    private int numPoints = 0;
    private double totalDistance = 0;
    private double averageDistance = 0;
    private double radius = 0;
    private double centre_x, centre_y = 0;
    private boolean isCircle = false;
    private Button clearButton;
    private ArrayList<Pair<Float, Float>> points;
    private TextView text_name_player;
    private TextView text_score_player;
    private TextView text_number_defi;
    private Intent intent;
    private final String current_defi_string = "Défi n° ";
    private int score;
    private int mode;
    private String player_name;
    private int nb_defi;
    private int nb_try;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawdefi);

        initAff();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        imageView.setOnTouchListener(this);
        clearButton.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                points = new ArrayList<>();
                startX = event.getX();
                startY = event.getY();
                lastX = startX;
                lastY = startY;
                isDrawing = true;
                numPoints = 1;
                totalDistance = 0;
                averageDistance = 0;
                isCircle = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDrawing) {
                    float x = event.getX();
                    float y = event.getY();
                    canvas.drawLine(lastX, lastY, x, y, paint);
                    double distance = Math.sqrt(Math.pow(lastX - x, 2) + Math.pow(lastY - y, 2));

                    points.add(new Pair<Float, Float>(x, y));
                    centre_x += x;
                    centre_y += y;
                    lastX = x;
                    lastY = y;
                    numPoints++;
                    totalDistance += distance;
                    averageDistance = totalDistance / numPoints;
                }
                break;

            case MotionEvent.ACTION_UP:
                isDrawing = false;
                double distance = Math.sqrt(Math.pow(lastX - startX, 2) + Math.pow(lastY - startY, 2));
                numPoints++;
                totalDistance += distance;
                averageDistance = totalDistance / numPoints;
                double perimeter = Math.PI * 2 * averageDistance;
                centre_x /= numPoints;
                centre_y /= numPoints;
                radius = totalDistance / (2 * Math.PI * numPoints);

                if (numPoints < 10) {
                    showToast("Dessinez un cercle plus grand");
                }
                //else if (Math.abs(1 - averageDistance / radius) < 0.9) {
                //else if (Math.abs(totalDistance/100 - perimeter) < 0.6 * perimeter){
                else if (isCircleOne() && isCircleTwo()){
                    isCircle = true;
                    score++;
                    AlertDialog.Builder builder = new AlertDialog.Builder(Defi_Dessin.this);
                    builder.setTitle("Bravo, vous avez réussi !");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent;
                            if(mode == 1){
                                intent = new Intent(Defi_Dessin.this, TrainingGameManager.class);
                            }else {
                                intent = new Intent(Defi_Dessin.this, OnePlayerGameManager.class);
                            }
                            intent.putExtra("PLAYER_NAME", player_name);
                            intent.putExtra("PLAYER_SCORE", score);
                            intent.putExtra("CURRENT_DEFIS", nb_defi);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
                else {
                    nb_try++;
                    if (nb_try == 3){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Defi_Dessin.this);
                        builder.setTitle("Vous avez perdu !");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent;
                                if(mode == 1){
                                    intent = new Intent(Defi_Dessin.this, TrainingGameManager.class);
                                }else {
                                    intent = new Intent(Defi_Dessin.this, OnePlayerGameManager.class);
                                }
                                intent.putExtra("PLAYER_NAME", player_name);
                                intent.putExtra("PLAYER_SCORE", score);
                                intent.putExtra("CURRENT_DEFIS", nb_defi);
                                startActivity(intent);
                            }
                        });
                        builder.show();
                    }
                    showToast("Le cercle n'est pas assez parfait, il vous reste " + String.valueOf(3-nb_try) + " tentatives.");
                }
                break;
        }
        imageView.invalidate();
        return true;
    }
    private void initAff(){
        Intent intent = getIntent();
        mode = intent.getIntExtra("MODE",0);

        imageView = (ImageView) findViewById(R.id.drawing_canvas_drawdefi);
        clearButton = (Button) findViewById(R.id.clear_button);

        bitmap = Bitmap.createBitmap(getScreenWidth(), getScreenHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        imageView.setImageBitmap(bitmap);

        text_name_player = (TextView) findViewById(R.id.namePlayer_textview_draw);
        player_name = intent.getStringExtra("PLAYER_NAME");
        text_name_player.setText(player_name);

        text_number_defi = (TextView) findViewById(R.id.title_defi_textview_draw);
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);
        text_number_defi.setText(current_defi_string + String.valueOf(nb_defi));

        text_score_player = (TextView) findViewById(R.id.scorePlayer_textview_draw);
        score = intent.getIntExtra("PLAYER_SCORE", 0);
        if(score<10){
            text_score_player.setText("0"+String.valueOf(score));
        }else{
            text_score_player.setText(String.valueOf(score));
        }

        if (mode == 1){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) clearButton.getLayoutParams();
            params.bottomMargin = 0; // Définir la marge en bas sur 0d
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
            clearButton.setLayoutParams(params); // Définir les nouvelles propriétés de disposition pour le bouton

            Button back_menu = (Button) findViewById(R.id.back_menu_button_draw);
            back_menu.setVisibility(Button.VISIBLE);
            back_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent_back = new Intent(Defi_Dessin.this, TrainingGameManager.class);
                    startActivity(intent_back);
                }
            });

        }
    }

    private boolean isCircleOne(){
        ArrayList<Double> distance_centre = new ArrayList<>();
        double distance_centre_sum = 0;
        for (Pair<Float, Float> point : points){
            double distance = Math.sqrt(Math.pow(point.first - centre_x, 2) + Math.pow(point.second - centre_y, 2));
            distance_centre_sum += distance;
            distance_centre.add(distance);
        }
        double variance = 0;
        double distance_centre_moyenne = distance_centre_sum/numPoints;
        for (double distance : distance_centre){
            variance += Math.pow(distance - distance_centre_moyenne , 2);
        }
        variance /= distance_centre.size();
        double seuil = totalDistance + (2 * Math.sqrt(variance));
        boolean condition = totalDistance < seuil;
        return variance < seuil;
    }

    private boolean isCircleTwo(){
        ArrayList<Double> angles = new ArrayList<>();
        double angle_moy = 0;
        for(int i = 0; i < points.size() - 2; i++){
            Pair<Float, Float> A = points.get(i);
            Pair<Float, Float> B = points.get(i + 1);
            Pair<Float, Float> C = points.get(i + 2);
            double angle = Math.toDegrees(Math.atan2(C.second - B.second, C.first - B.first) - Math.atan2(A.second - B.second, A.first - B.first));
            angle_moy += angle;
            angles.add(Math.abs(angle));
        }
        angle_moy /= angles.size();
        double variance = 0;
        for (double angle : angles){
            variance += Math.pow(angle - angle_moy , 2);
        }
        variance /= angles.size();
        double seuil_angle = 40000;
        boolean condition_angle = variance < seuil_angle;
        return variance < seuil_angle;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_button:
                clearDrawing();
                break;
        }
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearDrawing() {
        canvas.drawColor(Color.WHITE);
        imageView.invalidate();
    }
}
