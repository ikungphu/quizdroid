package edu.uw.ikungphu.quizdroid;


import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends Application {

    private static QuizApp instance = null;
    private List<Topic> topicList = new ArrayList<Topic>();
    //private TopicRepository topicRepository;

    public QuizApp() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Cannot create more than one QuizApp");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Application", "QuizApp is loaded and running");

        try {
            InputStream inputStream = getAssets().open("questions.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String result = null;
            String line = null;
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            result = builder.toString();
            JSONArray jsonData = new JSONArray(result);

            Log.i("Application", "JSON data: " + jsonData);
            for(int i = 0; i < jsonData.length(); i++) {
                JSONObject topic = jsonData.getJSONObject(i);
                JSONArray questions = topic.getJSONArray("questions");
                String title = topic.getString("title");
                String desc = topic.getString("desc");
                Log.i("Application", title + ": " + desc);
                Topic topicObj = new Topic(title, desc, questions.length());
                //List<Question> questionList = new ArrayList<Question>();

                // Parsing questions
                for(int j = 0; j < questions.length(); j++) {
                    JSONObject question = questions.getJSONObject(j);
                    String text = question.getString("text");
                    int index = Integer.parseInt(question.getString("answer"));
                    JSONArray answers = question.getJSONArray("answers");
                    //String[] answerList = new String[answers.length()];
                    Question quest = new Question(text, index - 1);
                    // Parsing answers
                    Log.i("Application", text + "| answer: " + index);
                    for(int k = 0; k < answers.length(); k++) {
                        //answerList[k] = answers.getString(k);
                        Log.i("Application", k + " " + answers.getString(k));
                        quest.addAnswer(answers.get(k).toString());
                    }
                    //Question quest = new Question(text, answerList, (index - 1));
                    //questionList.add(quest);
                    //Log.i("Application", text + " " + index);
                    topicObj.addQuestion(quest);
                }
                topicList.add(topicObj);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }  catch(JSONException e) {
            e.printStackTrace();
        }
    }

    public List<Topic> getTopics() {return topicList;}

    public List<String> getTopicStrings() {
        List<String> topicStrings = new ArrayList<String>();
        for (Topic topic : topicList) {
            topicStrings.add(topic.topic);
        }
        return topicStrings;
    }

}
