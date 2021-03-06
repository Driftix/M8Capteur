package com.example.projetcapteurandroid2;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.projetcapteurandroid2.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import parseretgpx.GPX;
import parseretgpx.Parser;
import parseretgpx.Track;
import parseretgpx.TrackPoint;
import parseretgpx.TrackSeg;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            InputStream input = getAssets().open("bikeandrun.gpx");
            Log.v("log","oui");

            try {
                GPX gpx = Parser.parse(input);
                for(Track track: gpx.getTracks()){
                    for(TrackSeg trackSeg: track.getTrackSegs()){

                        PolylineOptions polylineOptions = new PolylineOptions().clickable(false);
                        for(TrackPoint trackPoint: trackSeg.getTrackPoints()){
                            Log.v("log",trackPoint.getLatitude() + " / " + trackPoint.getLongitude());
                            polylineOptions.add(new LatLng(trackPoint.getLatitude(), trackPoint.getLongitude()));
                        }
                        googleMap.addPolyline(polylineOptions);
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}