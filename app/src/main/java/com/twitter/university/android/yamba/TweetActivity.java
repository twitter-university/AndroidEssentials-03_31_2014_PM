package com.twitter.university.android.yamba;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


// If mutable state is accessed from more than one thread
// all access must be performed holding a single lock
public class TweetActivity extends Activity {
    private static final String TAG = "TWEET";

    private static class Poster extends AsyncTask<String, Void, Integer> {

        private final Context ctxt;
        public Poster(Context ctxt) {
            this.ctxt = ctxt;
        }

        @Override
        protected Integer doInBackground(String... tweet) {
            String msg = tweet[0];

            try { Thread.sleep(1000 * 10); }
            catch (InterruptedException e) { }

            return Integer.valueOf(R.string.tweet_success);
        }

        @Override
        protected void onPostExecute(Integer status) {
            Toast.makeText(ctxt, status.intValue(), Toast.LENGTH_LONG).show();
            poster = null;
        }
    }

    static Poster poster;


    private int okColor;
    private int warnColor;
    private int errColor;

    private int tweetLenMax;
    private int warnMax;
    private int errMax;

    private Button submitButton;
    private TextView countView;
    private EditText tweetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        Resources rez = getResources();
        okColor = rez.getColor(R.color.ok);
        warnColor = rez.getColor(R.color.warn);
        errColor = rez.getColor(R.color.error);

        tweetLenMax = rez.getInteger(R.integer.tweet_limit);
        warnMax = rez.getInteger(R.integer.warn_limit);
        errMax = rez.getInteger(R.integer.err_limit);

        submitButton = (Button) findViewById(R.id.tweet_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) { post(); }
        });

        countView = (TextView) findViewById(R.id.tweet_count);

        tweetView = ((EditText) findViewById(R.id.tweet_tweet));
        tweetView.addTextChangedListener(
            new TextWatcher() {
                @Override
                public void afterTextChanged(Editable editable) { setCount(); }

                @Override
                public void beforeTextChanged(CharSequence cs, int i, int i2, int i3) {  }

                @Override
                public void onTextChanged(CharSequence cs, int i, int i2, int i3) { }
            } );
    }

    @Override
    protected void onPause() {
        super.onPause();
        poster.cancel(true);
    }

    void setCount() {
        int n = tweetView.getText().length();

        submitButton.setEnabled(checkTweetLen(n));

        n = tweetLenMax - n;

        int color;
        if (n > warnMax) { color = okColor; }
        else if (n > errMax) { color = warnColor; }
        else  { color = errColor; }

        countView.setText(String.valueOf(n));
        countView.setTextColor(color);
    }

    void post() {
        if (null != poster) { return; }

        String tweet = tweetView.getText().toString();
        if (!checkTweetLen(tweet.length())) { return; }

        tweetView.setText("");

        poster = new Poster(getApplicationContext());
        poster.execute(tweet);
    }

    private boolean checkTweetLen(int n) {
        return (errMax <= n) && (tweetLenMax > n);
    }
}
