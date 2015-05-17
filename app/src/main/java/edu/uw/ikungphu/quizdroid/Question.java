package edu.uw.ikungphu.quizdroid;


public class Question {
    public String question;
    public String[] answer;
    public int index;

    public Question(String question, String[] answer, int index) {
        this.question= question;
        this.answer = answer;
        this.index = index;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setAnswer(String[] answer) {
        this.answer = answer;
    }

    public String[] getAnswers() {
        return this.answer;
    }

    public String getCorrectAnswer() {
        return this.answer[index];
    }
}
