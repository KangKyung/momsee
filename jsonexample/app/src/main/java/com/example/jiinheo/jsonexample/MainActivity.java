package com.example.jiinheo.jsonexample;

import android.os.Bundle;
import android.os. AsyncTask;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends Activity{
    private static String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("");
                urlString = "http://openapi.seoul.go.kr:8088/5a716e6576676a7739314f43574c62/json/DailyAverageAirQuality/1/5/20181113/노원구";
                new ProcessJSON().execute(urlString);
            }
        });
    }
           private class ProcessJSON extends AsyncTask<String, Void, String>{
            protected String doInBackground(String... strings){
                String stream = null;
                String urlString = strings[0];

                HTTPDataHandler hh = new HTTPDataHandler();
                stream = hh.GetHTTPData(urlString);

                // Return the data from specified url
                return stream;
            }


        protected void onPostExecute(String stream){
            TextView tv = (TextView) findViewById(R.id.tv);
            //tv.setText(stream);

            /*
                Important in JSON DATA
                -------------------------
                * Square bracket ([) represents a JSON array
                * Curly bracket ({) represents a JSON object
                * JSON object contains key/value pairs
                * Each key is a String and value may be different data types
             */

            //..........Process JSON DATA................

            if(stream !=null){
                try{
                    // Get the full HTTP Data as JSONObject
                    JSONObject reader= new JSONObject(stream);

                    // Get the JSONObject "coord"...........................
                    JSONObject coord = reader.getJSONObject("coord");
                    // Get the value of key "lon" under JSONObject "coord"
                    String CO = coord.getString("CO");
                    // Get the value of key "lat" under JSONObject "coord"
                    String O3 = coord.getString("O3");

                    tv.setText("We are processing the JSON data....\n\n");
                    tv.setText(tv.getText()+ "\tcoord...\n");
                    tv.setText(tv.getText()+ "\t\tlon..."+ CO + "\n");
                    tv.setText(tv.getText()+ "\t\tlat..."+ O3 + "\n\n");

                    // Get the JSONObject "sys".........................
                    JSONObject sys = reader.getJSONObject("sys");
                    // Get the value of key "message" under JSONObject "sys"
                    String MSRSTE_NM = sys.getString("MSRSTE_NM");
                    // Get the value of key "country" under JSONObject "sys"
                    String country = sys.getString("country");
                    // Get the value of key "sunrise" under JSONObject "sys"
                    String sunrise = sys.getString("sunrise");
                    // Get the value of key "sunset" under JSONObject "sys"
                    String sunset = sys.getString("sunset");

                    tv.setText(tv.getText()+ "\tsys...\n");
                    tv.setText(tv.getText()+ "\t\tmessage..."+ MSRSTE_NM+ "\n");
                    tv.setText(tv.getText()+ "\t\tcountry..."+ country + "\n");
                    tv.setText(tv.getText()+ "\t\tsunrise..."+ sunrise + "\n");
                    tv.setText(tv.getText()+ "\t\tsunset..."+ sunset + "\n\n");

                    // Get the JSONArray weather
                    JSONArray weatherArray = reader.getJSONArray("weather");
                    // Get the weather array first JSONObject
                    JSONObject weather_object_0 = weatherArray.getJSONObject(0);
                    String weather_0_id = weather_object_0.getString("id");
                    String weather_0_main = weather_object_0.getString("main");
                    String weather_0_description = weather_object_0.getString("description");
                    String weather_0_icon = weather_object_0.getString("icon");

                    tv.setText(tv.getText()+ "\tweather array...\n");
                    tv.setText(tv.getText()+ "\t\tindex 0...\n");
                    tv.setText(tv.getText()+ "\t\t\tid..."+ weather_0_id + "\n");
                    tv.setText(tv.getText()+ "\t\t\tmain..."+ weather_0_main + "\n");
                    tv.setText(tv.getText()+ "\t\t\tdescription..."+ weather_0_description + "\n");
                    tv.setText(tv.getText()+ "\t\t\ticon..."+ weather_0_icon + "\n\n");

                    // process other data as this way..............

                }catch(JSONException e){
                    e.printStackTrace();
                }

            } // if statement end
        } // onPostExecute() end
    } // ProcessJSON class end
} // Activity end