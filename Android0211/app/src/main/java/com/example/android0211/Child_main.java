package com.example.android0211;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.android0211.Chatting.Child_chatting;

public class Child_main extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabHost tabHost = getTabHost();
        LayoutInflater.from(this).inflate(R.layout.activity_child_main, tabHost.getTabContentView(), true);

        TabHost.TabSpec tabSpec;
        Intent intent;

        intent = new Intent(this, Child_mission.class);
        tabSpec = tabHost.newTabSpec("FirstTab").setIndicator("미션").setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, Child_chatting.class);
        tabSpec = tabHost.newTabSpec("SecondTab").setIndicator("채팅").setContent(intent);
        tabHost.addTab(tabSpec);

        // intent = new Intent(this, Child_mission.class);
        //tabSpec = tabHost.newTabSpec("ThirdTab").setIndicator("게임").setContent(intent);
        //tabHost.addTab(tabSpec);


        tabHost.getTabWidget().setCurrentTab(0);

        //TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Intent intent2 = getIntent();
        String userName = intent2.getStringExtra("userEmail");  //  나중에 이 부분 이메일이 아니라 userName으로 수정하자!!
        String message = "환영합니다,  " + userName + "님!";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        //welcomeMessage.setText(message);
    }

}
