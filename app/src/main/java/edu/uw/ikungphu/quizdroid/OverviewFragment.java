package edu.uw.ikungphu.quizdroid;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class OverviewFragment extends Fragment {

    private String topic;
    private String topicRes;
    private int numQuestions;
    private Activity hostActivity;

    public Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("topic", topic);
        bundle.putString("topicRes", topicRes);
        bundle.putInt("numQuestions", numQuestions);
        bundle.putInt("questionNum", 1);
        bundle.putInt("correct", 0);

        return bundle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topic = getArguments().getString("topicTitle");
            topicRes = getArguments().getString("topicRes");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_quiz_overview, container, false);

        int idTopic = getResources().getIdentifier(topicRes + "_overview", "string", getActivity().getPackageName());
        String overview = getResources().getString(idTopic);
        TextView textOverview = (TextView) view.findViewById(R.id.textOverview);
        textOverview.setText(overview);

        int idNumQuestions = getResources().getIdentifier(topicRes + "_questions", "integer", getActivity().getPackageName());
        numQuestions = getResources().getInteger(idNumQuestions);
        TextView viewNumQuestions = (TextView) view.findViewById(R.id.numQuestions);
        viewNumQuestions.setText("Questions: " + numQuestions);

        Button beginButton = (Button) view.findViewById(R.id.beginBtn);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = createBundle();
                bundle.putString("topic", topic);
                bundle.putString("topicRes", topicRes);
                bundle.putInt("numQuestions", numQuestions);
                bundle.putInt("questionNum", 1);
                bundle.putInt("correct", 0);

                if (hostActivity instanceof SecondActivity) {
                    ((SecondActivity) hostActivity).loadQuestionFrag(bundle);
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}