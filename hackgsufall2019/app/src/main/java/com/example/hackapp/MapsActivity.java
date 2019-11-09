package com.example.hackapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final LatLng PERTH = new LatLng(33.820386, -84.2307692);
    private static final LatLng SYDNEY = new LatLng(33.822267, -84.231973);
    private static final LatLng BRISBANE = new LatLng(34.2029363, -84.2317686);


    private Marker mPerth;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle extras = getIntent().getExtras();
        final String apartmentsListMap = extras.getString("zipCodeValue");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Add some markers to the map, and add a data object to each marker.
        ArrayList<LatLng> cityList = new ArrayList<LatLng>();
        cityList.add(PERTH);
        cityList.add(SYDNEY);
        cityList.add(BRISBANE);
        createNewMarkers(cityList);
        mMap.setOnMarkerClickListener(this);
    }

    private void createNewMarkers(ArrayList<LatLng> citiesList) {
        Iterator<LatLng> iter  = citiesList.iterator();
        while (iter.hasNext()) {
            mPerth = mMap.addMarker(new MarkerOptions()
                    .position(iter.next())
                    .title("Perth"));
            mPerth.setTag(0);
        }
    }


    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    "Place" +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        return;
    }
}
