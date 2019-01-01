package com.example.jiinheo.momsee;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TextView;

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

        intent = new Intent(this, child_chatting.class);
        tabSpec = tabHost.newTabSpec("SecondTab").setIndicator("채팅 목록").setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, Maps.class);
        tabSpec = tabHost.newTabSpec("ThirdTab").setIndicator("자식 위치").setContent(intent);
        tabHost.addTab(tabSpec);


        tabHost.getTabWidget().setCurrentTab(0);

        TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Intent intent2 = getIntent();
        String userName = intent2.getStringExtra("userEmail");  //  나중에 이 부분 이메일이 아니라 userName으로 수정하자!!
        String message = "환영합니다, " + userName + "님!";
        welcomeMessage.setText(message);
    }
}
