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
    private OnFragmentInteractionListener fragListener;


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
        //setContentView(R.layout.activity_quiz_overview);
        //Intent launchIntent = getIntent();
        //topic = launchIntent.getStringExtra("topic");
        //topicRes = launchIntent.getStringExtra("topicRes");
        //setTitle(topic + "Overview");

        View myInflater = inflater.inflate(R.layout.activity_quiz_overview, container, false);

        int idTopic = getResources().getIdentifier(topicRes + "_overview", "string", getActivity().getPackageName());
        String overview = getResources().getString(idTopic);
        TextView textOverview = (TextView) myInflater.findViewById(R.id.textOverview);
        textOverview.setText(overview);

        int idNumQuestions = getResources().getIdentifier(topicRes + "_questions", "integer", getActivity().getPackageName());
        numQuestions = getResources().getInteger(idNumQuestions);
        TextView viewNumQuestions = (TextView) myInflater.findViewById(R.id.numQuestions);
        viewNumQuestions.setText("Questions: " + numQuestions);

        Button beginButton = (Button) myInflater.findViewById(R.id.beginBtn);
        beginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = createBundle();

                if (fragListener != null) {
                    fragListener.onFragmentInteraction(new Uri.Builder().fragment("overview").build(), bundle);
                }
            }
        });
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