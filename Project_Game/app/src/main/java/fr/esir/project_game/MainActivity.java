package fr.esir.project_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity {
    private static final int PICK_MP3_REQUEST = 1;
    String player_lead = "leaderboard.txt";
    String chellenge_file = "challenge.csv";

    private MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        //SetFiles();
        //delete();
        
        Button play_but = (Button) findViewById(R.id.button_play_home);
        Button train_but = (Button) findViewById(R.id.button_train_home);
        Button challenge_but = (Button) findViewById(R.id.button_add_challenge_home);
        Button leader_but = (Button) findViewById(R.id.button_leaderboard_home);
        Button add_music_but = (Button) findViewById(R.id.button_add_music_home);
        Button training_but = (Button) findViewById(R.id.button_training_home);
        //ImageButton Logo_button = (ImageButton)findViewById(R.id.music_logo_home);

        /*Logo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.music);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release();
                        mediaPlayer=null;
                    }
                });
                mediaPlayer.start();
            }
        });*/

        add_music_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mpeg");
                startActivityForResult(intent, PICK_MP3_REQUEST);
            }
        });



        play_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                Intent intent = new Intent(MainActivity.this, SettingGameActivity.class);
                startActivity(intent);
            }
        });

        training_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                Intent intent = new Intent(MainActivity.this, TrainingGameManager.class);
                intent.putExtra("PLAYER_NAME","Entrainement");
                startActivity(intent);
            }
        });

        train_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Début d'une nouvelle partie 1 joueur");
                builder.setMessage("Veuillez entrer votre nom de joueur : ");

                final EditText editText_dialog = new EditText(MainActivity.this);
                builder.setView(editText_dialog);

                builder.setPositiveButton("Commencer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name_player = editText_dialog.getText().toString();
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        Intent intent = new Intent(MainActivity.this, OnePlayerGameManager.class);
                        intent.putExtra("PLAYER_NAME",name_player);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });

        challenge_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddChallengeActivity.class);
                startActivity(intent);
            }
        });

        leader_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

        //TextView text = (TextView) findViewById(R.id.top_right_textview_home);
        //text.setText(ShowContent());

    }


    public void SetFiles() {
        String[] files = this.fileList();
        boolean existe = false;
        int i = 0;
        if (files.length > 0) {
            while (!existe) {
                if (files[i].equals(player_lead)) {
                    existe = true;
                } else {
                    i++;
                }
            }
            if (!existe) {
                File file = new File(this.getFilesDir(), player_lead);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


            boolean existeChallenge = false;
            int j = 0;
            while (!existeChallenge) {
                if (files[j].equals(chellenge_file)) {
                    existeChallenge = true;
                } else {
                    j++;
                }
            }
            if (!existeChallenge) {
                File file = new File(this.getFilesDir(), chellenge_file);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        File file = new File(this.getFilesDir(), chellenge_file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String ShowContent() {
        try {
            FileInputStream fis = openFileInput(chellenge_file);

            StringBuilder sb = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                sb.append((char) ch);
            }
            fis.close();
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Fichier vide";
    }

    public boolean delete(){
        File file = new File(this.getFilesDir(),player_lead);
        if(file.delete()){
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_MP3_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);

            try {
                InputStream is = getContentResolver().openInputStream(uri);
                File file = new File(Environment.getExternalStorageDirectory() + "/raw/" + "my_music.mp3");
                FileOutputStream fos = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

}
