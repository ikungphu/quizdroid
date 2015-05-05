package edu.uw.ikungphu.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;


public class QuestionFragment extends Fragment {

    private String topic, topicRes;
    private int numQuestions, questionNum, correct, indexSelected;
    private OnFragmentInteractionListener fragListener;
    RadioGroup answers;
    Button submit;
    View myInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            topicRes = getArguments().getString("topicRes");
            numQuestions = getArguments().getInt("numQuestions");
            questionNum = getArguments().getInt("questionNum");
            correct = getArguments().getInt("correct");
        }
        if (savedInstanceState != null) {
            indexSelected = savedInstanceState.getInt("indexSelected");
        } else {
            indexSelected = -1;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("indexSelected", indexSelected);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Intent launchIntent = getIntent();
        //topic = launchIntent.getStringExtra("topic");
        //topicRes = launchIntent.getStringExtra("topicRes");
        //numQuestions = launchIntent.getIntExtra("numQuestions", -1);
        //questionNum = launchIntent.getIntExtra("questionNum", -1);
        //correct = launchIntent.getIntExtra("correct", -1);
        myInflater = inflater.inflate(R.layout.activity_quiz_question, container, false);
        answers = (RadioGroup) myInflater.findViewById(R.id.answers);

        //setTitle(topic + "Question " + questionNum);

        TextView correctText = (TextView) myInflater.findViewById(R.id.correctText);
        correctText.setText("You have " + correct + " out of " + (questionNum - 1) + " correct.");

        int idQuestion = getResources().getIdentifier(topicRes + "_q" + questionNum, "string", getActivity().getPackageName());
        String question = getResources().getString(idQuestion);
        TextView questionText = (TextView) myInflater.findViewById(R.id.questionText);
        questionText.setText(question);

        submit = (Button) myInflater.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexSelected = answers.indexOfChild(myInflater.findViewById(answers.getCheckedRadioButtonId()));

                if (indexSelected >= 0) {

                    int idCorrectIndex = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a", "integer", getActivity().getPackageName());
                    int indexCorrect = getResources().getInteger(idCorrectIndex);

                    Bundle bundle = new Bundle();
                    bundle.putString("topic", topic);
                    bundle.putString("topicRes", topicRes);
                    bundle.putInt("indexSelected", indexSelected);
                    bundle.putInt("indexCorrect", indexCorrect);
                    bundle.putInt("numQuestions", numQuestions);
                    bundle.putInt("questionNum", questionNum);

                    if (1 + indexSelected == indexCorrect) {
                        bundle.putInt("correct", correct + 1);
                    } else {
                        bundle.putInt("correct", correct);
                    }

                    if (fragListener != null) {
                        fragListener.onFragmentInteraction(new Uri.Builder().fragment("overview").build(), bundle);
                    }
                }
            }

        });

        for (int i = 1; i <= getResources().getInteger(R.integer.questions); i++) {
            int idAnswer = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a" + i,
                    "string", getActivity().getPackageName());
            String answer = getResources().getString(idAnswer);
            int idAnswerButton = getResources().getIdentifier("answer" + i, "id", getActivity().getPackageName());
            TextView answerButton = (TextView) myInflater.findViewById(idAnswerButton);
            answerButton.setText(answer);
        }

        return myInflater;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri, Bundle bundle);
    }
}