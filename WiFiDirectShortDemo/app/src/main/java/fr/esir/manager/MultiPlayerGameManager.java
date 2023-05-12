package fr.esir.manager;
//content://com.example.android.wifidirect.fileprovider/gameway/gameway.txt
import static fr.esir.Main.SettingGameActivity.TAG;
import android.app.Activity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import fr.esir.game.Defi_Blindtest;
import fr.esir.game.Defi_Dessin;
import fr.esir.game.Defi_Questions;
import fr.esir.game.Defi_Secouer;
import fr.esir.game.Defi_compass;
import fr.esir.game.Defi_slide;
import fr.esir.End.EndGameMultiActivity;


public class MultiPlayerGameManager extends Activity {
    private static final int SOCKET_TIMEOUT = 10000;
    private String current_name;
    private int current_score;
    private int nb_defi;
    ArrayList<Integer> tab_game;
    private int mode = 2;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        current_name = intent.getStringExtra("PLAYER_NAME");
        current_score = intent.getIntExtra("PLAYER_SCORE", 0);
        tab_game = getIntent().getIntegerArrayListExtra("ArrayList");
        nb_defi = intent.getIntExtra("CURRENT_DEFIS", 0);


        System.out.println("Tableau du multimanager : ");
        for(int i=0;i<tab_game.size();i++){
            System.out.println(tab_game.get(i));
        }

        if(tab_game.size() == nb_defi){
            launchEndGame();
        }else{
            int temp = tab_game.get(nb_defi);
            nb_defi++;
            lauchGame(temp);
        }

    }

    private void lauchGame(int games){
        switch (games) {
            case 0:
                launchDefiQuestions();
                break;
            case 1:
                launchDefiDraw();
                break;
            case 2:
                launchDefiSecouer();
                break;
            case  3:
                launchDefiCompass();
                break;
            case  4:
                launchDefiSlide();
                break;
            case  5:
                launchBlindTest();
                break;
        }
    }

    private void launchBlindTest() {
        // Start BlindTest
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Blindtest.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("MODE",mode);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("ArrayList",tab_game);
        startActivity(intent);
    }

    private void launchDefiSlide() {
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_slide.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("MODE",mode);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("ArrayList",tab_game);
        startActivity(intent);
    }

    public void launchEndGame(){
        // Start EndGame
        Player player = new Player(current_name, current_score, MultiPlayerGameManager.this);
        try {
            player.AddNewPlayer();
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du fichier");
            throw new RuntimeException(e);
        }
        Intent intent = new Intent(MultiPlayerGameManager.this, EndGameMultiActivity.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        startActivity(intent);
    }

    public void launchDefiQuestions(){
        // Start fr.esir.game.Defi_Questions
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Questions.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("MODE",mode);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("ArrayList",tab_game);
        startActivity(intent);
    }

    public void launchDefiSecouer(){
        // Start Shaking Game
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Secouer.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("MODE",mode);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("ArrayList",tab_game);
        startActivity(intent);
    }

    public void launchDefiDraw(){
        // Start Defi_Draw
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_Dessin.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("MODE",mode);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("ArrayList",tab_game);
        startActivity(intent);
    }

    public void launchDefiCompass(){
        // Start fr.esir.game.Defi_compass
        Intent intent = new Intent(MultiPlayerGameManager.this, Defi_compass.class);
        intent.putExtra("PLAYER_NAME", current_name);
        intent.putExtra("PLAYER_SCORE", current_score);
        intent.putExtra("MODE",mode);
        intent.putExtra("CURRENT_DEFIS", nb_defi);
        intent.putExtra("ArrayList",tab_game);
        startActivity(intent);
    }
    /*

    public void sendMessage(String message) {
        if (socket != null) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(message.getBytes());
            } catch (IOException e) {
                Log.e(TAG, "Error sending message: " + e.getMessage());
            }
        }
    }

    private void connectToPeer(final WifiP2pInfo info) {
        InetAddress hostAddress = info.groupOwnerAddress;
        Socket socket = new Socket();
        int port = 8988;

        try {
            socket.bind(null);
            socket.connect(new InetSocketAddress(hostAddress, port), SOCKET_TIMEOUT);

            // Enregistrer la socket pour l'envoi de message
            this.socket = socket;

            // Lancer un thread pour recevoir des messages
            new ReceiveMessageTask().execute();
        } catch (IOException e) {
            Log.e(TAG, "Error connecting to peer: " + e.getMessage());
        }
    }


    private class ReceiveMessageTask extends AsyncTask<Void, String, Void> {
        private InputStream inputStream;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    publishProgress(line);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error receiving message: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            // Le message reçu est stocké dans values[0]
            Log.d(TAG, "Received message: " + values[0]);
            String name_player_J2 = values[values.length-1];
            int score_player_J2 = Integer.parseInt(values[values.length]);


            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
*/
}
