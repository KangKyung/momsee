package com.tistory.gangzzang.json_test;

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

public class MainActivity extends Activity {

	TextView textView; // 출력할 textView

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.textview); // 텍스트뷰 객체 참조
		new JsonLoadingTask().execute();
	} // onCreate
	
	
	/**
	 * 원격의 데이터를 가지고 JSON 객체를 생성한 다음에 객체에서 데이터 타입별로 데이터를 읽어서 StringBuffer에 추가한다.
	 */
	public String getJsonText() {
		
		// 내부적으로 문자열 편집이 가능한 StringBuffer 생성자
		StringBuffer sb = new StringBuffer();
		
		try {
			String line = getStringFromUrl("http://api.ibtk.kr/openapi/examCertiInfo_api.do?institutetype=01");
			
			/* 넘어오는 데이터 구조 { [ { } ] } JSON 객체 안에 배열안에 내부JSON 객체*/
			
			/* URL 요청시 넘어오는 JSON 데이터 값
			{
			    "result_code": 200,
			    "total_count": 183,
			    "kkt_list": [
			        {
			            "continentname": "아시아",
			            "nationname": "한국",
			            "instituteid": "3431",
			            "institutename": "(유)대영티엠아이",
			            "institutetype": "01",
			            "continentcode": "EA0001",
			            "nationcode": "KOR",
			            "address": "[565-901] 전북 완주군 봉동읍 구미리 837-3번지",
			            "phone": "063-214-9011",
			            "fax": "063-214-0236",
			            "description": "회사구분: 교정기관\r주요제품: 만능재료시험기\r현황: 교정실..현장",
			            "linkageinstituteid": "KOLAS",
			            "linkagepage": "http://www.kolas.go.kr/WebApp/Main/Search/Search0010.aspx?CompanyNo=3431&AccreditNo=3899"
			        },
			        {
			            "continentname": "아시아",
			            "nationname": "한국",
			            "instituteid": "3231",
			            "institutename": "한국건설생활환경시험연구원(시험)",
			            "institutetype": "01",
			            "continentcode": "EA0001",
			            "nationcode": "KOR",
			            "address": "[153-803] 서울 금천구 가산동 459-28",
			            "phone": "02-2102-2500",
			            "fax": "02-838-7891",
			            "description": "회사구분: 법인\r주요제품: 안전인증 및 자율안전, 계측교정, 시험분석, 품질보증, 인증심사업무\r\n\r현황: 만능재료시험기 등 408종 908점",
			            "linkageinstituteid": "KOLAS",
			            "linkagepage": "http://www.kolas.go.kr/WebApp/Main/Search/Search0010.aspx?CompanyNo=3231&AccreditNo=3051"
			        },
			       ... 생략
			    ]
			}
			*/
			
			// 원격에서 읽어온 데이터로 JSON 객체 생성
			JSONObject object = new JSONObject(line);
			
			// "kkt_list" 배열로 구성 되어있으므로 JSON 배열생성
			JSONArray Array = new JSONArray(object.getString("kkt_list"));
		
			for (int i = 0; i < Array.length(); i++) {
				// bodylist 배열안에 내부 JSON 이므로 JSON 내부 객체 생성
				JSONObject insideObject = Array.getJSONObject(i);

				// StringBuffer 메소드 ( append : StringBuffer 인스턴스에 뒤에 덧붙인다. )
				// JSONObject 메소드 ( get.String(), getInt(), getBoolean() .. 등 : 객체로부터 데이터의 타입에 따라 원하는 데이터를 읽는다. )
				
				sb.append("대륙 : ").append(insideObject.getString("continentname")).append("\n");
				sb.append("국가 : ").append(insideObject.getString("nationname")).append("\n");
				sb.append("기관아이디 : ").append(insideObject.getString("instituteid")).append("\n");
				sb.append("기관명 : ").append(insideObject.getString("institutename")).append("\n");
				sb.append("기관구분 : ").append(insideObject.getString("institutetype")).append("\n");
				sb.append("대륙코드 : ").append(insideObject.getString("continentcode")).append("\n");
				sb.append("국가코드 : ").append(insideObject.getString("nationcode")).append("\n");
				sb.append("주소 : ").append(insideObject.getString("address")).append("\n");
				sb.append("연락처 : ").append(insideObject.getString("phone")).append("\n");
				sb.append("설명 : ").append(insideObject.getString("description")).append("\n");
				sb.append("소속기구 : ").append(insideObject.getString("linkageinstituteid")).append("\n");
				sb.append("기관홈페이지 : ").append(insideObject.getString("linkagepage")).append("\n");
				sb.append("\n");
				sb.append("\n");
				
			} // for
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	} // getJsonText

	
	// getStringFromUrl : 주어진 URL 페이지를 문자열로 얻는다.
	public String getStringFromUrl(String url) throws UnsupportedEncodingException {
		
		// 입력스트림을 "UTF-8" 를 사용해서 읽은 후, 라인 단위로 데이터를 읽을 수 있는 BufferedReader 를 생성한다.
		BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));
		
		// 읽은 데이터를 저장한 StringBuffer 를 생성한다.
		StringBuffer sb = new StringBuffer();
		
		try {
			// 라인 단위로 읽은 데이터를 임시 저장한 문자열 변수 line
			String line = null;
			
			// 라인 단위로 데이터를 읽어서 StringBuffer 에 저장한다.
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	} // getStringFromUrl

	
	/**
	 *  getInputStreamFromUrl : 주어진 URL 에 대한 입력 스트림(InputStream)을 얻는다.
	 */
	public static InputStream getInputStreamFromUrl(String url) {
		InputStream contentStream = null;
		try {
			// HttpClient 를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(new HttpGet(url));
			contentStream = response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contentStream;
	} // getInputStreamFromUrl

	
	/**
	 *	스레드에서 향상된 AsyncTask 를 이용하여
	 * UI 처리 및 Background 작업 등을 하나의 클래스에서 작업 할 수 있도록 지원해준다.
	 */
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
} // end
