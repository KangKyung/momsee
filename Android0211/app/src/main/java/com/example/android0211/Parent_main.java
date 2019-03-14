package com.example.android0211;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.Toast;

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

        //intent = new Intent(this, child_chatting.class);
        tabSpec = tabHost.newTabSpec("SecondTab").setIndicator("채팅 목록").setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, Maps.class);
        tabSpec = tabHost.newTabSpec("ThirdTab").setIndicator("자식 위치").setContent(intent);
        tabHost.addTab(tabSpec);


        tabHost.getTabWidget().setCurrentTab(0);

        //받았어 이메일을
        String email=getIntent().getStringExtra("email");
        String message = "환영합니다,  " + email + "님!";
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

        //보냈어 이메일을
        Intent intent1 = new Intent(getApplicationContext(),activity_child_info.class);
        intent1.putExtra("email",email);
        startActivity(intent1);


    }
}
