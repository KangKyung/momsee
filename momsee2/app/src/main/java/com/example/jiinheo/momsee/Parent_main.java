package com.example.jiinheo.momsee;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;

public class Parent_main extends TabActivity {
TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_parent_main, tabHost.getTabContentView(), true);

        TabHost.TabSpec tabSpec;
        Intent intent;

        intent = new Intent(this, activity_child_info.class);
        tabSpec = tabHost.newTabSpec("FirstTab").setIndicator("자식 목록").setContent(intent);
        tabHost.addTab(tabSpec);


        tabHost.getTabWidget().setCurrentTab(0);
    }
}
