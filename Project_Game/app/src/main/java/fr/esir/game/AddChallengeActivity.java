package fr.esir.game;

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

import fr.esir.progm.wifidirectdemo.R;

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
        EditText question = (EditText) findViewById(R.id.question_edittext_add_challenge);
        EditText answer_1 = (EditText) findViewById(R.id.answer1_edittext_add_challenge);

        String oldContent = read();
        try (FileOutputStream fos = openFileOutput("challenge.csv", Context.MODE_PRIVATE)) {
            String newContent = oldContent + question.getText().toString()+","+answer_1.getText().toString().toLowerCase().replaceAll(" ","")+";";
            fos.write(newContent.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String read(){
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
        return sb.toString();
    }
}
