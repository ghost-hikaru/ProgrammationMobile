package fr.esir.tp3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FirstActivity extends Activity {
    String filename = "question1.txt";
    String message = "Mme Feuillatre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question3);

        Button button = (Button) findViewById(R.id.button_generation);
        Button buttonBack = (Button) findViewById(R.id.button_return_q3);
        TextView textview = (TextView) findViewById(R.id.textview_text);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createFileAndWrite();
            }
        });

        showContent(textview);

    }

    public void createFileAndWrite(){
        File file = new File(getBaseContext().getFilesDir(),filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
            String messageFinal = "Bonjour "+message;
            fos.write(messageFinal.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void showContent(TextView textview){
        try {
            // Ouvre le fichier en lecture
            FileInputStream fis = openFileInput(filename);
            // Lit les données du fichier
            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                sb.append((char) ch);
            }
            // Ferme le fichier
            fis.close();
            // Affiche les données dans la TextView
            textview.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
