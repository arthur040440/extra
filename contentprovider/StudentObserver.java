package com.example.sidedeleteproject.contentprovider;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by qiqi on 2021/12/4
 * Describe:
 */
public class StudentObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public StudentObserver(Handler handler) {
        super(handler);
    }


    @Override
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        Log.e("====selfChange", selfChange + "");
        Log.e("====uri", uri + "");

    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.e("====selfChange", selfChange + "");

    }
}
