package com.example.jiinheo.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.publicappapi_json.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;//출력할 텍스트뷰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView =(TextView) findViewById(R.id.textview);//텍스트뷰 객체 참조
        new JsonLoadingTask().execute();
    }  /* 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
            */
    public String getJsonText() {

        // 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
        StringBuffer sb = new StringBuffer();

        try {
            String line = getStringFromUrl("http://openapi.seoul.go.kr:8088/5a716e6576676a7739314f43574c62/json/DailyAverageAirQuality/1/5/20181113/노원구");

      /* 넘어오는 데이터 구조 { [ { } ] } JSON 객체 안에 배열안에 내부JSON 객체*/
      /* URL 요청시 넘어오는 JSON 데이터 값 */
        }
        JSONObject object = new JSONObject(line);

        JSONArray Array = new JSONArray(object.getString("kkk_list"));

        for(int i=0; i<Array.length(); i++) {
            JSONObject insideObject = Array.getJSONObject(i);


        }
