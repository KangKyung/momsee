package com.example.jiinheo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        //a액티비티로부터 전달받은 인텐트를 참조
        Intent intent =getIntent();
        //인텐트에 포함된 엑스트라에서 사진 폴더명을 추출
        Bundle bundle = intent.getExtras();
        String picUrl = bundle.getString("PIC_URL");
        //텍스트뷰에  전달받은 사진 폴더 : 와 불러올 폴더명을 출력
        TextView loadPicUrlTextView = (TextView) findViewById(R.id.received_pic_folder_url);
        String picUrlStr="전달받은 사진폴더 : " + picUrl;
        loadPicUrlTextView.setText(picUrlStr);
    }
}
