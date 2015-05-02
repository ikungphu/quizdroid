package edu.uw.ikungphu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class quizQuestion extends ActionBarActivity {

    private String topic, topicRes;
    private int numQuestions, questionNum, correct;
    RadioGroup answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        Intent launchIntent = getIntent();
        topic = launchIntent.getStringExtra("topic");
        topicRes = launchIntent.getStringExtra("topicRes");
        numQuestions = launchIntent.getIntExtra("numQuestions", -1);
        questionNum = launchIntent.getIntExtra("questionNum", -1);
        correct = launchIntent.getIntExtra("correct", -1);

        answers = (RadioGroup) findViewById(R.id.answers);

        setTitle(topic + "Question " + questionNum);

        TextView correctText = (TextView) findViewById(R.id.correctText);
        correctText.setText("You have " + correct + " out of " + (questionNum - 1) + " correct.");

        int idQuestion = getResources().getIdentifier(topicRes + "_q" + questionNum, "string", getPackageName());
        String question = getResources().getString(idQuestion);
        TextView questionText = (TextView) findViewById(R.id.questionText);
        questionText.setText(question);

        for (int i = 1; i <= getResources().getInteger(R.integer.questions); i++) {
            int idAnswer = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a" + i,
                    "string", getPackageName());
            String answer = getResources().getString(idAnswer);
            int idAnswerButton = getResources().getIdentifier("answer" + i, "id", getPackageName());
            TextView answerButton = (TextView) findViewById(idAnswerButton);
            answerButton.setText(answer);
        }

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexSelected = answers.indexOfChild(findViewById(answers.getCheckedRadioButtonId()));

                if (indexSelected >= 0) {
                    Intent answer = new Intent(quizQuestion.this, quizAnswer.class);

                    int idCorrectIndex = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a", "integer", getPackageName());
                    int correctIndex = getResources().getInteger(idCorrectIndex);

                    answer.putExtra("topic", topic);
                    answer.putExtra("topicRes", topicRes);
                    answer.putExtra("indexSelected", indexSelected);
                    answer.putExtra("indexCorrect", correctIndex);
                    answer.putExtra("numQuestions", numQuestions);
                    answer.putExtra("questionNum", questionNum);

                    if (1 + indexSelected == correctIndex) {
                        answer.putExtra("correct", correct + 1);
                    } else {
                        answer.putExtra("correct", correct);
                    }

                    startActivity(answer);
                    finish();
                }
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_question, menu);
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
