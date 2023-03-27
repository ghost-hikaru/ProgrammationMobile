package fr.esir.project_game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class AddChallengeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_challenge);

        Button submit_but = (Button)   findViewById(R.id.submit_button_add_challenge);

        submit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation_form();
                finish();
            }
        });
    }

    private void validation_form() {
        EditText cat_edit = (EditText) findViewById(R.id.cat_edittext_add_challenge);
        EditText question = (EditText) findViewById(R.id.question_edittext_add_challenge);
        EditText answer_1 = (EditText) findViewById(R.id.answer1_edittext_add_challenge);

        String oldContent = read();
        try (FileOutputStream fos = openFileOutput("challenge.csv", Context.MODE_PRIVATE)) {
            String newContent = oldContent + cat_edit.getText().toString()+","+question.getText().toString()+","+answer_1.getText().toString()+";";
            fos.write(newContent.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String read(){
        System.out.println("Début de la lecture du fichier");
        File file = new File(getFilesDir() +"/" + "challenge.csv");
        StringBuilder sb = new StringBuilder();
        String st;
        try {
            BufferedReader br= new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null)
                sb.append(st);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Fin de la lecture du fichier");
        System.out.println("Contenu du fichier : "+sb.toString());
        return sb.toString();
    }
}
