package com.example.sidedeleteproject.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.testpproject.sqlite.DbManager;

import java.util.Arrays;


/**
 * Created by tzhang on 2016/9/13.
 * <p>
 * http://www.cnblogs.com/linjiqin/archive/2011/05/28/2061396.html
 * <p>
 * 应用程序之间共享数据的途径，Content Provider 主要的功能就是存储并检索数据以及向其他应用程序提供访问数据的接口
 * <p>
 * 虽然使用其他方法也可以对外共享数据，但数据访问方式会因数据存储的方式而不同，如：采用文件方式对外共享数据，
 * 需要进行文件操作读写数据；采用sharedpreferences共享数据，需要使用sharedpreferences API读写数据。
 * 而使用ContentProvider共享数据的好处是统一了数据访问方式（sqlite）
 * <p>
 * 每一条数据记录都包括一个 "_ID" 数值字段，改字段唯一标识一条数据(创建表时注意)
 * <p>
 * 每一个Content Provider 都对外提供一个能够唯一标识自己数据集(data set)的公开URI, 如果一个Content Provider
 * 管理多个数据集，其将会为每个数据集分配一个独立的URI。所有的Content Provider 的URI 都以"content://" 开头，
 * 其中"content:"是用来标识数据是由Content Provider管理的 schema
 * <p>
 * content://contacts/people/45
 * Uri person = ContentUris.withAppendedId(People.CONTENT_URI,  45);
 */
public class MyContentProvider extends ContentProvider {


    private static final int STUDENT = 1;
    private static final int STUDENT_ITEM = 2;

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.elife.classproject/studnet
        URI_MATCHER.addURI("com.example.testpproject", "student", STUDENT);
        URI_MATCHER.addURI("com.example.testpproject", "student/#", STUDENT_ITEM);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = DbManager.getInstance().db;

        Cursor cursor;
        switch ((URI_MATCHER.match(uri))) {
            case STUDENT:

                cursor = db.query("student", projection, selection, selectionArgs, null, null, sortOrder);

//                DbManager.getInstance().closeDb(db);
                return cursor;

            case STUDENT_ITEM:
                long id = ContentUris.parseId(uri);

                Log.e("=======", id + "");

                String where = "id=" + id;
                if (!TextUtils.isEmpty(selection)) {
                    where = selection + " and " + where;
                }
                cursor = db.query("student", projection, where, selectionArgs, null, null, sortOrder);

                //DbManager.getInstance().closeDb(db);
                return cursor;
            default:
                throw new IllegalArgumentException("Uri不存在");

        }

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case STUDENT:
                return "vnd.android.cursor.dir/student";

            case STUDENT_ITEM:
                return "vnd.android.cursor.item/student";

            default:
                throw new IllegalArgumentException("Uri不存在");
        }
    }

    // 返回新插入数据Uri地址
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        SQLiteDatabase db = DbManager.getInstance().openDb(false);
        switch ((URI_MATCHER.match(uri))) {
            case STUDENT:
                // 特别说一下第二个参数为空时，将自动插入一个NULL。
                long rowid = db.insert("student", null, contentValues);// _ID
                // content://com.elife.classproject/studnet/rowid
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到代表新增记录的Uri,变化的那一行的Uri

                Log.e("=======", insertUri.toString());
                // 通知监听器关于数据更新的信息
                getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;
            default:
                throw new IllegalArgumentException("Uri不存在");
        }
    }

    // 返回受影响的行数
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = DbManager.getInstance().openDb(false);
        int count;
        Log.e("===uri", uri.toString());

        switch ((URI_MATCHER.match(uri))) {
            case STUDENT:
                count = db.delete("student", selection, selectionArgs);
                return count;
            case STUDENT_ITEM:
                long id = ContentUris.parseId(uri);
                String where = "stuId=" + id;
                if (!TextUtils.isEmpty(selection)) {
                    where = selection + " and " + where;
                }
                Log.e("===where", where);
                Log.e("===selectionArgs", Arrays.toString(selectionArgs));

                count = db.delete("student", where, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = DbManager.getInstance().openDb(false);
        int count;

        switch ((URI_MATCHER.match(uri))) {
            case STUDENT:
                count = db.update("student", contentValues, selection, selectionArgs);
                return count;
            case STUDENT_ITEM:
                long id = ContentUris.parseId(uri);
                String where = "stuId=" + id;
                if (!TextUtils.isEmpty(selection)) {
                    where = selection + " and " + where;
                }

                Log.e("===selection", selection + "");
                Log.e("===where", where + "");

                count = db.update("student", contentValues, where, selectionArgs);
                Log.e("===count", count + "");
                return count;
        }
        return 0;
    }
}
