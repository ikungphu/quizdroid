package edu.uw.ikungphu.quizdroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class quizOverview extends ActionBarActivity {

    private String topic;
    private String topicRes;
    private int numQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_overview);

        Intent launchIntent = getIntent();
        topic = launchIntent.getStringExtra("topic");
        topicRes = launchIntent.getStringExtra("topicRes");

        setTitle(topic + "Overview");

        int idTopic = getResources().getIdentifier(topicRes + "_overview", "string", getPackageName());
        String overview = getResources().getString(idTopic);
        TextView textOverview = (TextView) findViewById(R.id.textOverview);
        textOverview.setText(overview);

        final int idNumQuestions = getResources().getIdentifier(topicRes + "_questions", "integer", getPackageName());
        numQuestions = getResources().getInteger(idNumQuestions);
        TextView viewNumQuestions = (TextView) findViewById(R.id.numQuestions);
        viewNumQuestions.setText("Questions: " + numQuestions);

        Button beginButton = (Button) findViewById(R.id.beginBtn);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent question = new Intent(quizOverview.this, QuestionFragment.class);
                question.putExtra("topic", topic);
                question.putExtra("topicRes", topicRes);
                question.putExtra("numQuestions", numQuestions);
                question.putExtra("questionNum", 1);
                question.putExtra("correct", 0);

                startActivity(question);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_overview, menu);
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
