package com.example.jiinheo.api;

import android.graphics.Color;

/**
 * Created by JIIN HEO on 2018-11-14.
 */
import java.lang.String;

public class Weather {
    int lat;
    int ion;
    int temperature;
    int cloudy;
    String city;

    public void setLat(int lat){this.lat = lat;}
    public void setIon(int ion){this.ion = ion;}
    public void setTemperature(int t){this.temperature=t;}
    public void setCloudy(int cloudy){this.cloudy=cloudy;}
    public void setCity(String city){this.city=city;}

    public int getLat(){return lat;}
    public int getIon(){return ion;}
    public int getTemparature(){return temperature;}
    public int getCloudy(){return cloudy;}
    public String getCity(){return city;}
}
