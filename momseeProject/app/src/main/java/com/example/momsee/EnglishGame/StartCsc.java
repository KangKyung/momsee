package com.example.momsee.EnglishGame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.momsee.R;

/**
 * Created by rudgn on 2019-01-10.
 */

public class StartCsc extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_englishgame_startstudy);

        findViewById(R.id.main1).setOnClickListener(myClick);
        findViewById(R.id.main2).setOnClickListener(myClick);
//        findViewById(R.id.main3).setOnClickListener(myClick);
        findViewById(R.id.main4).setOnClickListener(myClick);
        findViewById(R.id.main5).setOnClickListener(myClick);
        findViewById(R.id.main6).setOnClickListener(myClick);
        findViewById(R.id.main7).setOnClickListener(myClick);

    }

    Button.OnClickListener myClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.main1:
                    startActivity(new Intent(StartCsc.this, ActivityStudy.class));
                    break;

                case R.id.main2:
                    startActivity(new Intent(StartCsc.this, ActivityStudy3.class));
                    break;

                case R.id.main4:
                    startActivity(new Intent(StartCsc.this, ActivityStudy5.class));
                    break;

                //test
                case R.id.main5:
                    startActivity(new Intent(StartCsc.this, ActivityStudy2.class));
                    break;

                //도움말
                case R.id.main6:
                    startActivity(new Intent(StartCsc.this, About.class));
                    break;

                //exit
                case R.id.main7:
                    finish();
                    break;

            }
        }
    };

}

