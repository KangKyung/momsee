package com.example.jiinheo.api;

import android.os.AsyncTask;

/**
 * Created by JIIN HEO on 2018-11-14.
 */

public class OpenWeatherAPITask extends AsyncTask<Integer,Void,Weather> {
    @Override
    public Weather doInBackground(Integer...params){
    OpenWeatherAPITask client = new OpenWeatherAPITask();

    int lat = params[0];
    int Ion = params[1];
        //API 호출
    Weather w;
        w = client.getWeather(lat,Ion);
        //System.out.println("Weather : "+w.getTemperature());

    //작업 후 리턴
    return w;
}
}
