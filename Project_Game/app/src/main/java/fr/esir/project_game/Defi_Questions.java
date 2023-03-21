package fr.esir.project_game;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Defi_Questions implements Defi{

    private String categorie;
    private String question;
    private String answer_player;
    private String answer;
    private Activity Ctx;
    public String challenge_file;

    public Defi_Questions(Activity m_Ctx, String defis){
        this.challenge_file = defis;
        this.Ctx = m_Ctx;
        this.categorie = "Question";
        this.LoadDefi();
    }

    @Override
    public void LoadDefi() {
        // transformation en tableau --> 1 case = 1 défis
        List<String> listDefis = new ArrayList<>();
        listDefis = Arrays.asList(challenge_file.split(";"));

        // on choisit un défis au hasard
        int index = randomInt(listDefis.size());

        // on récupère le défis
        List<String> defi = new ArrayList<>();
        defi = Arrays.asList(listDefis.get(index).split(","));

        // tant que l'on a pas un défi question
        while (!defi.get(0).equals(categorie)) {
            // on choisit un défis au hasard
            index = randomInt(listDefis.size());
            // on récupère le défis
            defi = new ArrayList<>();
            defi = Arrays.asList(listDefis.get(index).split(","));
        }

        // Initialisation du défis
        this.setQuestion(defi.get(1));
        this.setAnswer(defi.get(2));
    }

    public String getAnswer_player() {
        return answer_player;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public boolean getVictoryCondition() {
        return answer.equals(answer_player);
    }

    @Override
    public String getCategorie() {
        return categorie;
    }

    public static int randomInt(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer_player(String answer_player) {
        this.answer_player = answer_player;
    }
}
