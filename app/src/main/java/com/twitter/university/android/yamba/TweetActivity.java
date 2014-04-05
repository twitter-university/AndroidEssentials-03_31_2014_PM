package com.twitter.university.android.yamba;

import android.os.Bundle;


// If mutable state is accessed from more than one thread
// all access must be performed holding a single lock
public class TweetActivity extends YambaActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
    }
}
