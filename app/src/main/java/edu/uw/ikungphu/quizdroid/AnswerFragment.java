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
import android.widget.TextView;


public class AnswerFragment extends Fragment {

    private String topic, topicRes;
    private int numQuestions, questionNum, correct, indexSelected, indexCorrect;
    private OnFragmentInteractionListener fragListener;

    public AnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = getArguments().getString("topic");
            topicRes = getArguments().getString("topicRes");
            indexSelected = getArguments().getInt("indexSelected");
            indexCorrect = getArguments().getInt("indexCorrect");
            numQuestions = getArguments().getInt("numQuestions");
            questionNum = getArguments().getInt("questionNum");
            correct = getArguments().getInt("correct");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // number of correct answers
        View view = inflater.inflate(R.layout.activity_quiz_answer, container, false);
        TextView correctText = (TextView) view.findViewById(R.id.textCorrect);
        correctText.setText("You have " + correct + " out of " + (questionNum) + " correct.");

        // question text
        int idQuestion = getResources().getIdentifier(topicRes + "_q" + questionNum, "string", getActivity().getPackageName());
        String question = getResources().getString(idQuestion);
        TextView questionText = (TextView) view.findViewById(R.id.question);
        questionText.setText(question);

        // selected answer
        int idSelected = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a" + (indexSelected + 1), "string", getActivity().getPackageName());
        String selectedAnswer = getResources().getString(idSelected);
        TextView textSelectedAnswer = (TextView) view.findViewById(R.id.selectedAnswer);
        textSelectedAnswer.setText("You selected: " + selectedAnswer);

        // correct answer
        int idCorrect = getResources().getIdentifier(topicRes + "_q" + questionNum + "_a" +
                (indexCorrect), "string", getActivity().getPackageName());
        String correctAnswer = getResources().getString(idCorrect);
        TextView correctAnswerText = (TextView) view.findViewById(R.id.correctAnswer);
        correctAnswerText.setText("Correct answer: " + correctAnswer);

        Button next = (Button) view.findViewById(R.id.next);
        if (questionNum < numQuestions) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("topic", topic);
                    bundle.putString("topicRes", topicRes);
                    bundle.putInt("numQuestions", numQuestions);
                    bundle.putInt("questionNum", questionNum++);
                    bundle.putInt("correct", correct);

                }
            });
        } else {
            next.setText(getResources().getString(R.string.finish));
        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}