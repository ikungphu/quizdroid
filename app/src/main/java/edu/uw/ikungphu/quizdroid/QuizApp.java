package edu.uw.ikungphu.quizdroid;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizApp extends Application {

    private static QuizApp instance = null;
    private List<Topic> topicList = new ArrayList<Topic>();
    //private TopicRepository topicRepository;
    AlarmManager alarm;
    Intent intent;
    PendingIntent pendingIntent;
    private String url;
    private AlarmManager alarmManager;
    private boolean alarmSet;
    private int interval;


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

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //String url = sharedPreferences.getString("URL", "http://tednewardsandbox.site44.com/questions.json");

        File myFile = new File(getFilesDir().getAbsolutePath(), "/data.json");
        String json = "";

        if(myFile.exists()) {
            try {
                FileInputStream fileInputStream = openFileInput("data.json");
                json = readJSONFile(fileInputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
                try {
                    InputStream inputStream = getAssets().open("data.json");
                    json = readJSONFile(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        //int time = Integer.parseInt(sharedPreferences.getString("frequency", "10"));

        //Log.i("Alert", url + " " + time);
        alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        intent = new Intent();
        intent.setAction("com.tutorialspoint.CUSTOM_INTENT");
        intent.putExtra("message", url);
        pendingIntent = PendingIntent.getBroadcast(QuizApp.this, 0, intent, 0);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pendingIntent);

        try {
            //InputStream inputStream = getAssets().open("questions.json");
            //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //StringBuilder builder = new StringBuilder();
            //String result = null;
            //String line = null;
            //while((line = reader.readLine()) != null) {
            //   builder.append(line + "\n");
            //}
            //result = builder.toString();
            JSONArray jsonData = new JSONArray(json);

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
        } catch(JSONException e) {
            e.printStackTrace();
        }

        Download.toggleAlarm(this, true);

        alarmSet = false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        url = sharedPreferences.getString("url", "http://tednewardsandbox.site44.com/questions.json");
        interval = Integer.parseInt(sharedPreferences.getString("interval", "1")) * 60000;
        alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);


        BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(QuizApp.this, url, Toast.LENGTH_SHORT).show();
            }
        };

        registerReceiver(alarmReceiver, new IntentFilter("humzam.washington.edu.getData"));

        Intent intent = new Intent();
        intent.setAction("humzam.washington.edu.getData");
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        changeUrl(url, interval);
        //quiz.setTopics(questions);
    }

    public void changeUrl(String url, int interval) {
        this.url = url;
        this.interval = interval;
        if (alarmSet) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            alarmSet = false;
        }
        alarmSet = true;
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 1000, interval, pendingIntent);
    }

    public String readJSONFile(InputStream inputStream) throws IOException {
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();

        return new String(buffer, "UTF-8");
    }

    public List<Topic> getTopics() {return topicList;}

    public static QuizApp getInstance() {
        return instance;
    }

    public List<String> getTopicStrings() {
        List<String> topicStrings = new ArrayList<String>();
        for (Topic topic : topicList) {
            topicStrings.add(topic.topic);
        }
        return topicStrings;
    }

    public void writeToFile(String data) {
        try {
            File file = new File(getFilesDir().getAbsolutePath(), "data.json");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
