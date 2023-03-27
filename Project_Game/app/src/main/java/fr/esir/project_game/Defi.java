package fr.esir.project_game;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public interface Defi {

    public void LoadDefi();

    public String getQuestion();

    public boolean getVictoryCondition();

    public String getCategorie();

    public String getAnswer();

    public String getAnswer_player();

    public void setAnswer_player(String answer_player);

    public void setQuestion(String question);

    public void setAnswer(String answer);
}
