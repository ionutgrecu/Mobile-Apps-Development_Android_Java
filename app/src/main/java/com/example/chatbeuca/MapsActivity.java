package com.example.chatbeuca;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Sydney and move the camera
        LatLng ASE = new LatLng(44.44769680424436, 26.096775869079213);
        mMap.addMarker(new MarkerOptions().position(ASE).title("Marker in ASE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ASE));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ASE).title("sunt la ase yeey").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

        Marker marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();

        LatLng palat = new LatLng(44.42770268701534, 26.087339869078576);
        mMap.addMarker(new MarkerOptions().position(palat).title("Marker in Palatul Parlamentului"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(palat));

        mMap.setMinZoomPreference(14);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        PolylineOptions plo = new PolylineOptions();
        plo.add(ASE);
        plo.add(palat);
        plo.color(Color.BLUE);
        plo.width(20);
        mMap.addPolyline(plo);
    }
}