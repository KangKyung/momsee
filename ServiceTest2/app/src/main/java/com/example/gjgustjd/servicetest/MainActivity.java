package com.example.gjgustjd.servicetest;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        startService(new Intent(this,OverlayService.class));
    }
    public void Event(View v){
        switch(v.getId()) {
            case R.id.momsee: {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.example.jiinheo.momsee");
                startActivity(intent);
            }
            case R.id.katalk:{
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.kakao.talk");
                startActivity(intent);
            }
            case R.id.everytime:{
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.everytime.v2");
                startActivity(intent);

            }
        }
    }

}
