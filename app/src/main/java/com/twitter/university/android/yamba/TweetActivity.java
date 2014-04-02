package com.twitter.university.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class TweetActivity extends Activity {
    private static final String TAG = "TWEET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "in onCreate: " + this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "in onStart: " + this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "in onResume: " + this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "in onPause: " + this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "in onStop: " + this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "in onDestroy: " + this);
        super.onDestroy();
    }
}
