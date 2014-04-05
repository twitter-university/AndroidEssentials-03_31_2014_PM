package com.twitter.university.android.yamba;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class TimelineFragment
        extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int TIMELINE_LOADER = 42;

    static class TimelineBinder implements SimpleCursorAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View v, Cursor cursor, int col) {
            if (v.getId() != R.id.timeline_row_time) { return false; }

            CharSequence s = "ancient";
            long t = cursor.getLong(col);
            if (0 < t) { s = DateUtils.getRelativeTimeSpanString(t); }
            ((TextView) v).setText(s);
            return true;
        }
    }

    private static final String[] FROM = new String[] {
            YambaContract.Timeline.Columns.HANDLE,
            YambaContract.Timeline.Columns.TIMESTAMP,
            YambaContract.Timeline.Columns.TWEET
    };

    private static final int[] TO = new int[] {
            R.id.timeline_row_handle,
            R.id.timeline_row_time,
            R.id.timeline_row_tweet
    };


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),
                YambaContract.Timeline.URI,
                null,
                null,
                null,
                YambaContract.Timeline.Columns.TIMESTAMP + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Log.d("LOADER", "NEW CURSOR!!!");
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ((SimpleCursorAdapter) getListAdapter()).swapCursor(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View v = super.onCreateView(inflater, container, state);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0);
        adapter.setViewBinder(new TimelineBinder());
        setListAdapter(adapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

        return v;
    }
}
