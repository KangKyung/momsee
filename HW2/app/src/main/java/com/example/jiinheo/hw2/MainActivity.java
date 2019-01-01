package com.example.jiinheo.hw2;
import com.example.jiinheo.hw2.MainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;

import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View.OnClickListener;

import java.lang.String;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    //출력 영역
    EditText et_webpage_src;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 클릭 대기 : 시작
        Button btn = (Button) findViewById(R.id.date_bt);
        btn.setOnClickListener(this);
        //버튼 클릭 대기 : 끝
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getApplicationContext(), "대기질 정보", Toast.LENGTH_SHORT).show();
        textView = (TextView) findViewById(R.id.textView);
        new JsonLoadingTask().execute(); //Async스레드를 시작
    }


    private class JsonLoadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strs) {
            return getJsonText();
        } // doInBackground : 백그라운드 작업을 진행한다.
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask
    /**
     * 원격으로부터 JSON형태의 문서를 받아서
     * JSON 객체를 생성한 다음에 객체에서 필요한 데이터 추출
     */
    public String getJsonText() {

        StringBuffer bf = new StringBuffer();
        try {

            //주어진 URL 문서의 내용을 문자열로 얻는다.
            String jsonPage = getStringFromUrl("http://openAPI.seoul.go.kr:8088/5a716e6576676a7739314f43574c62/json/GeoInfoFallAvrgTemp/1/5/20181113/노원구");

            //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
            JSONObject json = new JSONObject(jsonPage);
            JSONObject json1 = json.getJSONObject("DailyAverageAirQuality");
            //row의 값은 배열로 구성 되어있으므로 JSON 배열생성

            JSONArray jArr = json1.getJSONArray("row");

            //배열의 크기만큼 반복하면서, ksNo과 korName의 값을 추출함
            for (int i=0; i<jArr.length(); i++){

                //i번째 배열 할당
                json = jArr.getJSONObject(i);

                //ksNo,korName의 값을 추출함
                String NO2 = json.optString("NO2");
                String O3 = json.optString("O3");
                String CO = json.optString("CO");
                String SO2 = json.getString("SO2");
                String PM10 = json.getString("PM10");
                String PM25 = json.getString("PM25");

                //StringBuffer 출력할 값을 저장
                bf.append(NO2+"\n");
                bf.append(O3+"\n");
                bf.append(CO+"\n");
                bf.append(SO2+"\n");
                bf.append(PM10+"\n");
                bf.append(PM25+"\n");
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return bf.toString();
    }//getJsonText()-----------


    // getStringFromUrl : 주어진 URL의 문서의 내용을 문자열로 반환
    public String getStringFromUrl(String pUrl){

        BufferedReader bufreader=null;
        HttpURLConnection urlConnection = null;

        StringBuffer page=new StringBuffer();

        try {
            URL url = new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();
            InputStreamReader rr = new InputStreamReader(contentStream, "UTF-8");
            bufreader = new BufferedReader(rr);
            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while((line = bufreader.readLine())!=null){
                Log.d("line:",line);
                page.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //자원해제
            try {
                bufreader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return page.toString();
    }// getStringFromUrl()-------------------------

} // end Class