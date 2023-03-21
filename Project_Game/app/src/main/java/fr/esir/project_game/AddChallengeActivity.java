package fr.esir.project_game;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddChallengeActivity extends Activity {
    Defis newDefis;

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

        newDefis = new Defis(cat_edit.getText().toString(),question.getText().toString(),answer_1.getText().toString(),this);
        newDefis.write();

    }
}
