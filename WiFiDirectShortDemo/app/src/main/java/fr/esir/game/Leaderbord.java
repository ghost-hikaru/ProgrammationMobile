package fr.esir.game;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe fr.esir.game.Leaderbord, permet de gérer le leaderboard
 */
public class Leaderbord {

    String leaderbord_file = "leaderboard.txt";
    Activity Ctx;

    /**
     * Constructeur de la classe fr.esir.game.Leaderbord
     * @param Ctx Contexte de l'activité
     */
    public Leaderbord(Activity Ctx){
        this.Ctx = Ctx;
    }

    /**
     * Lis le fichier leaderboard.txt et retourne le contenu sous forme de string
     * @return String le contenu du fichier leaderboard.txt
     */
    private String read(){
        System.out.println("Début de la lecture du fichier");
        File file = new File(Ctx.getFilesDir() +"/" + leaderbord_file);
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

    /**
     * Permet de récupérer les 4 premiers du classement sous forme de map
     * @return Map<String, String> la map des 4 premiers du classement (nom, score)
     */
    public Map<String, String> getLeaderbord() {
        Map<String, Integer> scoreMap = new HashMap<>();

        // séparer le string en tableau de chaînes de caractères
        String scoreString = read();
        String[] scoreArray = scoreString.split(";");

        // parcourir le tableau de chaînes de caractères
        for (String score : scoreArray) {
            // séparer chaque score en nom et score séparés par une virgule
            String[] scoreSplit = score.split(",");
            String nomJoueur = scoreSplit[0];
            int scoreJoueur = Integer.parseInt(scoreSplit[1]);

            // mettre à jour le map des scores
            if (scoreMap.containsKey(nomJoueur)) {
                int currentScore = scoreMap.get(nomJoueur);
                if (scoreJoueur > currentScore) {
                    scoreMap.put(nomJoueur, scoreJoueur);
                }
            } else {
                scoreMap.put(nomJoueur, scoreJoueur);
            }
        }

        // trier le map par ordre décroissant de score
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(scoreMap.entrySet());
        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        // retourner les 4 premiers scores
        Map<String, String> topFourMap = new LinkedHashMap<>();
        for (int i = 0;  i < sortedEntries.size(); i++) {
            topFourMap.put(sortedEntries.get(i).getKey(), String.valueOf(sortedEntries.get(i).getValue()));
        }

        return topFourMap;
    }

    /**
     * Permet de mettre à jour le score d'un joueur
     * @param name le nom du joueur
     * @param sc le score à ajouter
     */
    public void updateScore(String name, int sc) {
        String scoreString = read();
        StringBuilder sb = new StringBuilder();

        String[] players = scoreString.split(";");
        for (String player : players) {
            String[] parts = player.split(",");
            if (parts[0].equals(name)) {
                int score = Integer.parseInt(parts[1]);
                score += sc; // ajouter "sc" au score actuel
                parts[1] = String.valueOf(score);
            }
            sb.append(parts[0]).append(",").append(parts[1]).append(";");
        }

        updateScoreInFile(sb.toString());
    }

    /**
     * Permet d'ajouter un joueur au leaderboard
     * @param name le nom du joueur
     * @param sc le score du joueur
     */
    public void addPlayer(String name, int sc){
        String newContent = read() + name + "," + sc + ";";
        updateScoreInFile(newContent);
    }

    /**
     * Permet de mettre à jour le fichier leaderboard.txt
     * @param content le contenu à écrire dans le fichier
     */
    private void updateScoreInFile(String content){
        System.out.println(content);
        try (FileOutputStream fos = Ctx.openFileOutput(leaderbord_file, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


