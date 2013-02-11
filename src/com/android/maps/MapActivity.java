
package com.android.maps;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends android.support.v4.app.FragmentActivity {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private String provider;
    private UiSettings mUiSettings;
    public LatLngBounds bounds;
    public final double latitude = 54;
    public final double longitude = -5.9;
    public LatLngBounds pubsnme;
    public LatLng pubs = new LatLng(latitude, longitude);
    public LatLng pubs1 = new LatLng(latitude, longitude);
    public Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
          .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!enabled) {
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          startActivity(intent);
        } 

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
          System.out.println("Provider " + provider + " has been selected.");
          onLocationChanged(location);
          
          //doSomething();
          
          mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(pubsnme, 500, 500, 10));
          location = locationManager.getLastKnownLocation(provider);
        } 
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(pubsnme, 500, 500, 10));
    }

 
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(52,-6);
            }
        }
    }

    public void onLocationChanged(Location location) {
      double lat = (double) (location.getLatitude());
      double lng = (double) (location.getLongitude());
      setUpMap(lat, lng);

      LatLngBounds temp = new LatLngBounds(new LatLng(lat, lng), pubs);
      pubsnme = temp;
      
      Marker tempmarker = mMap.addMarker(new MarkerOptions()
      .position(new LatLng(lat, lng))
      .title("San Francisco")
      .snippet("Population: 776733"));
      marker = tempmarker;
      
      mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(pubsnme, 500, 500, 10));
      Toast.makeText(this, " latitude " + lat + "longditude" + lng, Toast.LENGTH_SHORT).show(); 
    }
    
    
    private void setUpMap(double lat, double lng) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mUiSettings = mMap.getUiSettings();
    }
    
    private void doSomething(){
        double saddrlat = 53.10, saddrlon = -6.26, daddrlat = 60.50, daddrlon = -6.26;
        
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + saddrlat + "," + saddrlon + "&daddr=" + daddrlat + "," + daddrlon));
        
        startActivity(intent);
        }
    
    public void setMyLocationButtonEnabled(View v) {
        mUiSettings.setMyLocationButtonEnabled(((CheckBox) v).isChecked());
    }

    
    public void onStatusChanged(String provider, int status, Bundle extras) {
      // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
      Toast.makeText(this, "Enabled new provider " + provider,
          Toast.LENGTH_SHORT).show();

    }

    public void onProviderDisabled(String provider) {
      Toast.makeText(this, "Disabled provider " + provider,
          Toast.LENGTH_SHORT).show();
    }
}
