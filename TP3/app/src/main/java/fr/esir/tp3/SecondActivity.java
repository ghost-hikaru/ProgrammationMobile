package fr.esir.tp3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends Activity {
    String filename = "question4.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question4);

        EditText editText = (EditText) findViewById(R.id.editText_text);

        Button buttonOk = (Button) findViewById(R.id.button_ok);
        Button buttonBack = (Button) findViewById(R.id.button_return_q4);
        Button buttonCancel = (Button) findViewById(R.id.button_cancel);

        editText.setText("");
        showContentEdit(editText);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write(editText.getText().toString());
                showContentEdit(editText);
                System.out.println("Contenue du fichier : "+editText.getText().toString());
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContentEdit(editText);
            }
        });

    }


    public void write(String message){
        // Write part
        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(message.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showContentEdit(EditText textview){
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
            if (sb.length()==0){
                textview.setText("empty file");
            }else{
                textview.setText(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
