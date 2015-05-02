package edu.uw.ikungphu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class quizAnswer extends ActionBarActivity {

    private String topic, topicRes;
    private int numQuestions, questionNum, correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);

        Intent launchIntent = getIntent();
        topic = launchIntent.getStringExtra("topic");
        topicRes = launchIntent.getStringExtra("topicRes");
        int indexSelected = launchIntent.getIntExtra("indexSelected", -1);
        int indexCorrect = launchIntent.getIntExtra("indexCorrect", -1);
        numQuestions = launchIntent.getIntExtra("numQuestions", -1);
        questionNum = launchIntent.getIntExtra("questionNum", -1);
        correct = launchIntent.getIntExtra("correct", -1);

        // menu bar text
        setTitle(topic + "Question " + questionNum);

        // number of correct answers
        TextView correctText = (TextView) findViewById(R.id.textCorrect);
        correctText.setText("You have " + correct + " out of " + (questionNum) + " correct.");

        // question text
        int idQuestion = getResources().getIdentifier(topicRes + "_q" + questionNum, "string", getPackageName());
        String question = getResources().getString(idQuestion);
        TextView questionText = (TextView) findViewById(R.id.question);
        questionText.setText(question);

        // selected answer
        int idSelected = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a" + (indexSelected + 1), "string", getPackageName());
        String selectedAnswer = getResources().getString(idSelected);
        TextView textSelectedAnswer = (TextView) findViewById(R.id.selectedAnswer);
        textSelectedAnswer.setText("You selected: " + selectedAnswer);

        // correct answer
        int idCorrect = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a" +
                (indexCorrect), "string", getPackageName());
        String correctAnswer = getResources().getString(idCorrect);
        TextView correctAnswerText = (TextView) findViewById(R.id.correctAnswer);
        correctAnswerText.setText("Correct answer: " + correctAnswer);

        Button next = (Button) findViewById(R.id.next);
        if (questionNum < numQuestions) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(questionNum < numQuestions) {
                        Intent nextQuestion = new Intent(quizAnswer.this, quizQuestion.class);
                        nextQuestion.putExtra("topic", topic);
                        nextQuestion.putExtra("topicRes", topicRes);
                        nextQuestion.putExtra("numQuestions", numQuestions);
                        nextQuestion.putExtra("questionNum", questionNum + 1);
                        nextQuestion.putExtra("correct", correct);

                        startActivity(nextQuestion);
                        finish();

                    } else {
                        Intent mainActivity = new Intent(quizAnswer.this, MainActivity.class);
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(mainActivity);
                        finish();
                    }
                }
            });
        } else {
            next.setText(getResources().getString(R.string.finish));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_answer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
