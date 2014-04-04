package com.twitter.university.android.yamba;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by bmeike on 4/3/14.
 */
public class YambaService extends IntentService {
    private static final String PARAM_TWEET = "YambaService.TWEET";

    public static void post(Context ctxt, String tweet) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(PARAM_TWEET, tweet);
        ctxt.startService(i);
    }


    private volatile Handler hdlr;

    public YambaService() { super("YSVC"); }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        hdlr = new Handler();
    }

    // run on a different thread.
    public void onHandleIntent(Intent intent) {
        String tweet = intent.getStringExtra(PARAM_TWEET);

        try { Thread.sleep(1000 * 10); }
        catch (InterruptedException e) { }

        hdlr.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(YambaService.this, R.string.tweet_success, Toast.LENGTH_LONG).show();
            }
        });
    }
}
