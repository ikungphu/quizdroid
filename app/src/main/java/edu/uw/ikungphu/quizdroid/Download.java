package edu.uw.ikungphu.quizdroid;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import java.util.logging.Handler;

public class Download extends IntentService{
    private DownloadManager downloadManager;
    private long downloadID;

    public Download() {
        super("Download");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        IntentFilter test = new IntentFilter("com.tutorialspoint.CUSTOM_INTENT");

        registerReceiver(receiverDownload, test);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiverDownload);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("download", "onhandleintent");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String url = sharedPreferences.getString("URL", "http://tednewardsandbox.site44.com/questions.json");
        if(isConnected()) {
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Questions");
            request.setTitle("Question File");
            request.setVisibleInDownloadsUi(true);
            downloadID = downloadManager.enqueue(request);
        } else {
            Log.i("Application", "Not Connected");
        }
    }

    public boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++) {
                    Log.i("Application", info[i].getState() + "");
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }

        }
        return false;
    }

    public static void toggleAlarm(Context context, boolean bool) {
        Intent alarmIntent = new Intent(context, CheckQuestions.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 21, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (bool) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            String refreshInterval = sharedPrefs.getString("interval", "10000");
            int intervalInMillis = Integer.parseInt(refreshInterval) * 60000;
            Log.i("DownloadService", "setting alarm to " + refreshInterval);
            // Start the alarm manager to repeat
            alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), intervalInMillis, pendingIntent);
        }
        else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.i("DownloadService", "Stopping alarm");
        }
    }
}

