package com.example.jiinheo.momsee;

import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";

    LocationManager manager;
    MapView mapView;
    SupportMapFragment mapFragment;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        LatLng seoul = new LatLng(37.52487,126.92723);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(seoul).title("원하는 위치에 마커를 표시했습니다.");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(seoul));

    }
}
