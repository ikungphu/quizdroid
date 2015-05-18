package edu.uw.ikungphu.quizdroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

public class SecondActivity extends ActionBarActivity {
    private String topicString;
    private int numQuestions, questionNum;
    private int index;

    //private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        index = getIntent().getIntExtra("position", -1);
        Topic topic = ((QuizApp) getApplication()).getTopics().get(index);

        if (savedInstanceState != null) {
            questionNum = savedInstanceState.getInt("questionNum");
        } else {
            questionNum = -1;
            OverviewFragment of = new OverviewFragment();
            of.setArguments(getIntent().getExtras());

            Bundle arguments = new Bundle();
            arguments.putInt("position", index);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.container, of);
            ft.commit();
        }

        //Intent intent = getIntent();
        //String[] storage = intent.getStringExtra("topic").split(" ");
        //topicString = storage[0];
        //String topicRes = intent.getStringExtra("topicRes");
        //int idNumQuestions = getResources().getIdentifier(topicRes + "_questions", "integer", getPackageName());
        //numQuestions = getResources().getInteger(idNumQuestions);
        //bundle = intent.getExtras();

        topicString = topic.topic.split(" ")[0];

        if(questionNum == -1) {
            setTitle(topicString + " Overview");
        } else {
            setTitle(topicString + " Question yo" );
        }

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("questionNum", questionNum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    public void loadAnswerFrag(Bundle info) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AnswerFragment answerFragment = new AnswerFragment();
        answerFragment.setArguments(info);
        ft.replace(R.id.container, answerFragment);
        ft.commit();
        questionNum = info.getInt("questionNum");
        setTitle(topicString + " Question " + questionNum + 1);
    }

    public void loadQuestionFrag(Bundle info) {
        if(info.getInt("questionNum") > info.getInt("numQuestions")) {
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
            finish();
            return;
        } else {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            QuestionFragment questionFragment = new QuestionFragment();
            questionFragment.setArguments(info);
            ft.replace(R.id.container, questionFragment);
            ft.commit();
        }
        questionNum = info.getInt("questionNum");
        setTitle(topicString + " Question " + questionNum + 1);
    }
}