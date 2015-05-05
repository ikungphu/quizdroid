package edu.uw.ikungphu.quizdroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;

public class FragmentActivity extends ActionBarActivity implements OnFragmentInteractionListener{
    private String topic;
    private int questionNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState != null) {
            questionNum = savedInstanceState.getInt("questionNum");
        } else {
            OverviewFragment of = new OverviewFragment();
            of.setArguments(getIntent().getExtras());

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main, of);
            ft.commit();
        }

        Intent intent = getIntent();
        String[] storage = intent.getStringExtra("topic").split(" ");
        topic = storage[0];
        String topicRes = intent.getStringExtra("topicRes");
        int idNumQuestions = getResources().getIdentifier(topicRes + "_questions", "integer", getPackageName());
        int numQuestions = getResources().getInteger(idNumQuestions);

        if(questionNum > 0) {
            setTitle(topic + " Question " + questionNum);
        } else {
            setTitle("Results");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("questionNum", questionNum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fragment_activity, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri, Bundle bundle) {
        if(uri.getFragment().equals("af") || uri.getFragment().equals("of")) {
            if (bundle.getInt("questionNum") > bundle.getInt("numQuestions")) {
                Intent intent = new Intent(FragmentActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return;
            } else {
                QuestionFragment qf = new QuestionFragment();
                qf.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main, qf);
                ft.commit();
            }
        } else {
            AnswerFragment af = new AnswerFragment();
            af.setArguments(bundle);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main, af);
            ft.commit();
        }

        questionNum = bundle.getInt("questionNum");
        setTitle(topic + " Question " + questionNum);
    }
}
