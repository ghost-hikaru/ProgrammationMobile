package fr.esir.manager;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import fr.esir.LeaderBoard.Leaderbord;

public class Player {
    Activity Ctx;
    String player_lead = "leaderboard.txt";
    private String name_player;
    private int score_player;

    public Player(String name,int score,Activity Ctx){
        name_player = name;
        score_player = score;
        this.Ctx = Ctx;
    }

    public Player(String name,Activity Ctx){
        name_player = name;
        score_player = 0;
        this.Ctx = Ctx;
    }
    public String getName_player() {
        return name_player;
    }

    public void setName_player(String name_player) {
        this.name_player = name_player;
    }

    public int getScore_player() {
        return score_player;
    }

    public void setScore_player(int score_player) {
        this.score_player = this.score_player+score_player;
    }

    @Override
    public String toString() {
        return getName_player()+","+getScore_player()+";";

    }

    /**
     * Ajoute un nouveau joueur dans le leaderboard
     * Si le joueur existe déjà, on met à jour son score
     * Sinon on ajoute le joueur
     * @throws IOException
     */
    public void AddNewPlayer() throws IOException {
        Leaderbord leaderbord = new Leaderbord(Ctx);
        if(existe(this.getName_player()) == null){
            leaderbord.addPlayer(this.getName_player(), this.getScore_player());

        }else{
            Player player = existe(this.getName_player());
            leaderbord.updateScore(player.getName_player(), this.getScore_player());
        }
    }

    public Player existe(String name_research){
        Player retourPlayer = new Player("",0,null);
        String allPlayer  = read();
        // transformation en tableau --> 1 case = 1 player
        List<String> listPlayer ;
        listPlayer = Arrays.asList(allPlayer.split(";"));

        for(int i=0;i<listPlayer.size();i++){
            List<String> player ;
            player = Arrays.asList(listPlayer.get(i).split(","));

            if(Objects.equals(player.get(0), name_research)){

                retourPlayer.setName_player(player.get(0));
                retourPlayer.setScore_player(Integer.parseInt(player.get(1)));

                return retourPlayer;
            }
        }

        return null;
    }

    public String read(){
        File file = new File(Ctx.getFilesDir() +"/" + player_lead);
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

    public Player recupDataPlayer(String name_player){
        Player currentPlayer = null;
        String allPlayer  = read();
        // transformation en tableau --> 1 case = 1 player
        List<String> listPlayer = new ArrayList<>();
        listPlayer = Arrays.asList(allPlayer.split(";"));

        for(int i=0;i<listPlayer.size();i++){
            List<String> player = new ArrayList<>();
            player = Arrays.asList(listPlayer.get(i).split(","));

            if(player.get(0)== name_player){
                currentPlayer.setName_player(this.getName_player());
                currentPlayer.setScore_player(this.getScore_player());
                return currentPlayer;
            }
        }
        return null;
    }
}
