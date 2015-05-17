package edu.uw.ikungphu.quizdroid;


import java.util.ArrayList;
import java.util.List;

public class Topic {
    public String topic;
    public List<Question> questions;
    public String overview;
    public int numQuestions;

    public Topic(String topic, String overview, int numQuestions) {
        this.topic = topic;
        this.overview = overview;
        this.numQuestions = numQuestions;
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}
