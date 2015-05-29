package edu.uw.ikungphu.quizdroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CheckQuestions extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("URL");
        Intent downloadIntent = new Intent(context, Download.class);
        context.startService(downloadIntent);
    }
}
