package com.example.jiinheo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String mSendPicUrl = "/data/pics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼에 사진 불러오기와 불러올 폴더명을 출력
        Button loadPicUrlBtn = (Button) findViewById(R.id.load_pic_url_btn);
        String loadUrlStr = "사진불러오기 : " + mSendPicUrl;
        loadPicUrlBtn.setText(loadUrlStr);
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.load_pic_url_btn:
            {
                //사진불러오기 액티비티를 실행하기 위한 인텐트 생성
                Intent intent = new Intent();
                //명시적 인텐트 방식으로 실행될 b 액티비티를 설정
                intent.setClass(this, ActivityB.class);
                //불러올 사진 폴더명을 인텐트 엑스트에 추가
                intent.putExtra("PIC_URL", mSendPicUrl);
                //사진 불러오기 b액티비티를 실행
                startActivityForResult(intent,0);
        }
        }
    }
}
