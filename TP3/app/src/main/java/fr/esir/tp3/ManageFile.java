package fr.esir.tp3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ManageFile extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_question5_6_7);
        EditText editText = (EditText) findViewById(R.id.editText_text);
        ListView listView = (ListView) findViewById(R.id.listview_file);
        Button buttonOk = (Button) findViewById(R.id.button_ok);
        Button buttonBack = (Button) findViewById(R.id.button_return_q7);


        getFiles(listView);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomFile(editText.getText().toString());
                getFiles(listView);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fileToDelete = getFilesName(i);
                String content = new String("Fichier vide");
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageFile.this);
                builder.setTitle("Supression du fichier : ");
                content = showContent(fileToDelete);

                builder.setMessage("Le Fichier " + fileToDelete + " sera suprimé du téléphone !\nContenue :\n"+showContent(fileToDelete));
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFile(getFilesName(i));
                        getFiles(listView);
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String fileUpdate = getFilesName(i);


                AlertDialog.Builder builder = new AlertDialog.Builder(ManageFile.this);
                builder.setTitle("Modifier le contenue du fichier du fichier : ");
                builder.setMessage("Ajouter un contenue au fichier " + fileUpdate);

                final EditText editText_dialog = new EditText(ManageFile.this);
                builder.setView(editText_dialog);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText_dialog.getText().toString();
                        System.out.println(text);
                        write(text,fileUpdate);
                    }
                });
                builder.setNegativeButton("Cancel", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void createCustomFile(String filenameCustom){
        // Create part
        //File chemin = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(this.getFilesDir(),filenameCustom+".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getFiles(ListView listView){
        String[] files =  this.fileList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);
        listView.setAdapter(adapter);
    }

    public String getFilesName(int position){
        String[] files =  this.fileList();
        ArrayList<String> fileList = new ArrayList<>();
        for(String currentFile : files){
            fileList.add(currentFile);
        }
        return fileList.get(position);
    }

    public String showContent(String filename){
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
            return sb.toString();
            //textview.setText(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Fichier vide";
    }

    public boolean deleteFile(String fileToDelete){
        File file = new File(this.getFilesDir(),fileToDelete);
        if(file.delete()){
            return true;
        }
        return false;
    }

    public void write(String message,String filename){
        try (FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(message.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
