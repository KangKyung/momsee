package com.example.jiinheo.json3;

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

public class MainActivity extends Activity implements OnClickListener {

    //출력 영역
    TextView textView;
    EditText et_webpage_src; //출력

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼 클릭 대기 : 시작
        Button btn = (Button)findViewById(R.id.button_call);
        btn.setOnClickListener(this);
        //버튼 클릭 대기 : 끝
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "대기 오염도", Toast.LENGTH_SHORT).show();
        //버튼 누르면 toast 뜨고

        et_webpage_src = (EditText) findViewById(R.id.webpage_src);
        textView=(TextView)  findViewById(R.id.textView);

        new JsonLoadingTask().execute(); //Async스레드를 시작

    }//onClick() ----------------

    // AsyncTask 를 이용 UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
    /*
     [보충]

     AsyncTask 클래스는 AsyncTask<Params, Progress, Result>와 같이 제네릭을 이용하여 작성

        Params은 실행 시에 작업에 전달되는 값의 타입
        Progress는 작업의 진행 정도를 나타내는 값의 타입
        Result는 작업의 결과 값를 나타내는 값의 타입.
        앞의 모든 타입을 모두 사용할 필요는 없다. 만약 필요하지 않은 타입 있다면 Void로 표시

     AsyncTask 클래스의 메소드

        doInBackground() 메소드는 작업 스레드에서 자동적으로 실행
        onPreExecute(), onPostExecute(), onProgressUpdate()는 UI쓰레드에서 실행
        doInBackground()가 반환하는 값은 onPostExecute()로 보내진다.
        doInBackground()도중에 언제든지 publishProgress()를 호출하여서 UI스레드에서 onProgressUpdate()를 실행가능
        언제든지 작업을 취소할수있다.
     */

    private class JsonLoadingTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... strs) {
            return getJsonText(); //제이슨을 추출해서 텍스트에 가져옴
        } // doInBackground : 백그라운드 작업을 진행한다.

        @Override//앞에서 했던 값을 받아서 edittext 에 보여줌
        protected void onPostExecute(String[] result) {
            textView.setText(result[0]);

        } // onPostExecute : 백그라운드 작업이 끝난 후 UI 작업을 진행한다.
    } // JsonLoadingTask

    /**
     * 원격으로부터 JSON형태의 문서를 받아서
     * JSON 객체를 생성한 다음에 객체에서 필요한 데이터 추출
     */
    public String[] getJsonText() {
        StringBuffer sb = new StringBuffer();
        String[] arraysum = new String [5];
        try {
            //주어진 URL 문서의 내용을 문자열로 얻는다.
            //여기서 getStringFrourl의 함수를 써서 거기있는 페이지를 문자열로 얻음
            String jsonPage = getStringFromUrl("http://openAPI.seoul.go.kr:8088/5a716e6576676a7739314f43574c62/json/DailyAverageAirQuality/1/5/20130228/노원구 ");

            //읽어들인 JSON포맷의 데이터를 JSON객체로 변환
            JSONObject json = new JSONObject(jsonPage);
            JSONObject json1=json.getJSONObject("DailyAverageAirQuality");
            //row의 값은 배열로 구성 되어있으므로 JSON 배열생성

            JSONArray jArr = json1.getJSONArray("row");


            //배열의 크기만큼 반복하면서, ksNo과 korName의 값을 추출함
            for (int i=0; i<jArr.length(); i++){
                //i번째 배열 할당
                json=jArr.getJSONObject(i);
                //값을 추출함

                String NO2 = json.optString("NO2");
                String CO = json.optString("CO");
                String SO2 = json.getString("SO2");
                String PM10 = json.getString("PM10");
                String PM25 = json.getString("PM25");

                //Stringq버퍼에 출력할 값을 저장
                /*sb.append(NO2+"\n");
                sb.append(CO+"\n");
                sb.append(SO2+"\n");
                sb.append(PM10+"\n");
                sb.append(CPM25+"\n");
                sb.append("\n");*/

                arraysum[0]=NO2;
                arraysum[1]=CO;
                arraysum[2]=SO2;
                arraysum[3]=PM10;
                arraysum[4]=PM25;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return arraysum;
    }//getJsonText()-----------


    // getStringFromUrl : 주어진 URL의 문서의 내용을 문자열로 반환
    public String getStringFromUrl(String pUrl){

        BufferedReader bufreader=null;
        HttpURLConnection urlConnection = null;

        StringBuffer page=new StringBuffer(); //읽어온 데이터를 저장할 StringBuffer객체 생성

        try {
            //[Type1]
            /*
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(pUrl));
            InputStream contentStream = response.getEntity().getContent();
            */
            //[Type2]
            URL url= new URL(pUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = urlConnection.getInputStream();
            InputStreamReader rr =new InputStreamReader(contentStream,"UTF-8");
            bufreader = new BufferedReader(rr);

            String line = null;

            //버퍼의 웹문서 소스를 줄단위로 읽어(line), Page에 저장함
            while((line = bufreader.readLine())!=null){
                Log.d("line:",line);
                page.append(line);
            }

        } catch (Exception e) {
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