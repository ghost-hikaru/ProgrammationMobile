package fr.esir.project_game;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Defis{
    Activity Ctx;
    public String challenge_file = "challenge.csv";
    private String categorie;
    private String question;
    private String answer;

    public Defis(String categorie,String question,String answer, Activity Ctx){
        this.Ctx = Ctx;
        this.categorie = categorie;
        this.question = question;
        this.answer = answer;
    }



    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return getCategorie()+","+getQuestion()+","+getAnswer()+";";
    }

    public void createFile(){
        File file = new File(Ctx.getFilesDir(),challenge_file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(){
        String oldContent = read();
        try (FileOutputStream fos = Ctx.openFileOutput(challenge_file, Context.MODE_PRIVATE)) {
            String newContent = oldContent + this;
            fos.write(newContent.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read(){
        File file = new File(Ctx.getFilesDir() +"/" + challenge_file);
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


    public Defis recupDefis(){
        Defis nextdefi = null;
        // Récupération de tout les défis sous forme de string
        String AllDefis  = read();
        // transformation en tableau --> 1 case = 1 défis
        List<String> listDefis = new ArrayList<>();
        listDefis = Arrays.asList(AllDefis.split(";"));
        // on choisit un défis au hasard
        int index = randomInt(0,listDefis.size());
        // on récupère le défis
        List<String> defi = new ArrayList<>();
        defi = Arrays.asList(listDefis.get(index).split(","));
        // Initialisation du défis
        nextdefi.setCategorie(defi.get(0));
        nextdefi.setQuestion(defi.get(1));
        nextdefi.setAnswer(defi.get(2));
        return nextdefi;
    }

    public static int randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}

