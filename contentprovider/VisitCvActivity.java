package com.example.sidedeleteproject.contentprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.sidedeleteproject.R;

import java.util.ArrayList;


public class VisitCvActivity extends AppCompatActivity implements View.OnClickListener {

    Button mAddBtn;
    Button mDeleteBtn;
    Button mQueryBtn;
    Button mUpdateBtn;

    TextView mTextView;


    StudentObserver studentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_cv);
        mAddBtn = (Button) findViewById(R.id.content_add_btn);
        mDeleteBtn = (Button) findViewById(R.id.content_delete_btn);
        mQueryBtn = (Button) findViewById(R.id.content_query_btn);
        mUpdateBtn = (Button) findViewById(R.id.content_update_btn);

        mAddBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mQueryBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);

        mTextView = (TextView) findViewById(R.id.tv);

        studentObserver = new StudentObserver(new Handler(Looper.getMainLooper()));

        getContentResolver().registerContentObserver(Uri.parse("content://com.example.testpproject/student"), true, studentObserver);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.content_add_btn:
                insert();
                break;
            case R.id.content_delete_btn:
                delete();
                break;
            case R.id.content_query_btn:
                query();
                break;
            case R.id.content_update_btn:
                update();
                break;
        }
    }

    private void update() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.testpproject/student");
        Uri uri2 = ContentUris.withAppendedId(uri, 123);
        ContentValues contentValues = new ContentValues();
        contentValues.put("stuId", 666);
        contentValues.put("stuName", "韩琦66");
        contentValues.put("stuGender", "男66");
        contentResolver.update(uri2, contentValues, null, null);
    }

    private void delete() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.testpproject/student");
        Uri uri2 = ContentUris.withAppendedId(uri, 123);
        contentResolver.delete(uri2, null, null);

    }

    private void insert() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = Uri.parse("content://com.example.testpproject/student");
        ContentValues contentValues = new ContentValues();
        contentValues.put("stuId", 123);
        contentValues.put("stuName", "韩琦");
        contentValues.put("stuGender", "男");
        contentValues.put("stuAge", 28);
        contentResolver.insert(uri, contentValues);
    }

    // 测试用
    private void query() {
        ContentResolver contentResolver = getContentResolver();
        Uri selectUri = Uri.parse("content://com.example.testpproject/student");
        Uri uri2 = ContentUris.withAppendedId(selectUri, 1);
        Cursor cursor = contentResolver.query(uri2, null, null, null, null);
        ArrayList<Student> arrayList = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0) {
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("stuId"));
            String name = cursor.getString(cursor.getColumnIndex("stuName"));
            String gender = cursor.getString(cursor.getColumnIndex("stuGender"));
            int age = cursor.getInt(cursor.getColumnIndex("stuAge"));

            arrayList.add(new Student(id, name, gender, age));
        }


        mTextView.setText(arrayList.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(studentObserver);
    }
}
