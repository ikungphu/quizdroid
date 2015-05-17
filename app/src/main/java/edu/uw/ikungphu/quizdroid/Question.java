package edu.uw.ikungphu.quizdroid;


import java.util.ArrayList;
import java.util.List;

public class Question {
    public String question;
    public List<String> answers;
    public int correctIndex;

    public Question(String question, int index) {
        this.question= question;
        this.correctIndex = index;
        answers = new ArrayList<String>();
    }

    public void addAnswer(String answer) {
        answers.add(answer);
    }
}
