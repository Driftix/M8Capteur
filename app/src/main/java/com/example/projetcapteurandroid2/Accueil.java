package com.example.projetcapteurandroid2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import parseretgpx.GPX;
import parseretgpx.Parser;
import parseretgpx.Track;
import parseretgpx.TrackPoint;
import parseretgpx.TrackSeg;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button buttonCarte = (Button)findViewById(R.id.buttonCarte);
        buttonCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, MapsActivity.class);
                startActivity(intent);
            }
        });
        try {
//            InputStream input = getAssets().open("app/src/androidTest/assets");
            InputStream input = getAssets().open("bikeandrun.gpx");
            Log.v("log","oui");

            try {
                GPX gpx = Parser.parse(input);
                for(Track track: gpx.getTracks()){
                    for(TrackSeg trackSeg: track.getTrackSegs()){

                        PolylineOptions polylineOptions = new PolylineOptions().clickable(false);
                        for(TrackPoint trackPoint: trackSeg.getTrackPoints()){
                            Log.v("log",trackPoint.getLatitude() + " / " + trackPoint.getLongitude());
                        }
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}