package edu.uw.ikungphu.quizdroid;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("message");
        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
    }

}
