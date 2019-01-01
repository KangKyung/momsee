package com.example.jiinheo.study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class activity_broadcasting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcasting);
    }

    public void onClick (View v)
    {
        Intent intent = new Intent();
        intent.setAction("com.example.jiinheo.study.Broadcasting.action.FILE_DOWNLOADED");
        intent.putExtra("FILE_NAME", "superdroid.png");
        sendBroadcast(intent);
    }
}
