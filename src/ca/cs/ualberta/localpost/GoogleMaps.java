package ca.cs.ualberta.localpost;

/**
 * Demo Code from: http://www.cs.sfu.ca/CourseCentral/276/bfraser/androidExampleCode/AndroidGoogleMapsAPIDemo.zip
 * AND http://www.youtube.com/watch?v=JHTy0Zybefw
 * **/

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMaps extends Activity {

	//private final LatLng LOCATION_EDMONTON = new LatLng(53.5333, -113.5000);
	
	private GoogleMap map;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_main);
		
		MyLocation();
		
		//map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(getLatitude(), getLongitude()), 14.0f) );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void MyLocation() {
		// Check that map is not already instantiated
		if(map == null){
			// Obtain map from MapFragment
			map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();		
			// Check if map was obtained
			if (map != null){
				// Enable MyLocation Layer
				map.setMyLocationEnabled(true);
				
				// Get LocationManager object
				LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
				
				// Create criteria object to retrieve provider
				Criteria criteria = new Criteria();
				
				// Get name of best provider
				String provider = locationManager.getBestProvider(criteria, true);
				
				// Get current location
				Location myLocation = locationManager.getLastKnownLocation(provider);
				
				// Set map type
				map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				
				// get Latitude and Longitude
				double lat = myLocation.getLatitude();
				double lng = myLocation.getLongitude();
				
				// Create a LatLng object of current location
				LatLng latlng = new LatLng(lat, lng);
				
				// Show current location in Google Map
				map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
				map.animateCamera(CameraUpdateFactory.zoomTo(16));				
				
				// Add marker for current location
				map.addMarker(new MarkerOptions().position(latlng).title("I'm here!"));
			}
		
		}
		
	/*	map.setMyLocationEnabled(true);
		Location location;
		location = map.getMyLocation();
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng ll = new LatLng(lat, lng);
		map.addMarker(new MarkerOptions().position(ll).title("I'm here!"));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 20));*/
	}
	
	@Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }
    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }
    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }
}