/**
 * 
 */
package ca.cs.ualberta.localpost.view;
import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.cs.ualberta.localpost.model.CommentModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsView extends FragmentActivity implements OnMarkerClickListener {
	
	/** Source: http://wptrafficanalyzer.in/blog/android-geocoding-showing-user-input-location-on-google-map-android-api-v2/**/
	GoogleMap googleMap;
	MarkerOptions markerOptions;
	LatLng latLng;
	CommentModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_view);
		
		SupportMapFragment supportMapFragment = (SupportMapFragment) 
				getSupportFragmentManager().findFragmentById(R.id.mapView);

		// Getting a reference to the map
		googleMap = supportMapFragment.getMap();
		
		// Getting reference to btn_find of the layout maps_view
        Button btn_find = (Button) findViewById(R.id.btn_find);
        
        // Defining button click event listener for the find button
        OnClickListener findClickListener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// Getting reference to EditText to get the user input location
				EditText etLocation = (EditText) findViewById(R.id.et_location);
				
				// Getting user input location
				String location = etLocation.getText().toString();
				
				if(location!=null && !location.equals("")){
					new GeocoderTask().execute(location);
				}
			}
		};
		
		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);		
		
		
	}
	
	
	// An AsyncTask class for accessing the GeoCoding Web Service
		private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

			@Override
			protected List<Address> doInBackground(String... locationName) {
				// Creating an instance of Geocoder class
				Geocoder geocoder = new Geocoder(getBaseContext());
				List<Address> addresses = null;
				
				try {
					// Getting a maximum of 3 Address that matches the input text
					addresses = geocoder.getFromLocationName(locationName[0], 3);
				} catch (IOException e) {
					e.printStackTrace();
				}			
				return addresses;
			}
			
			
			@Override
			protected void onPostExecute(List<Address> addresses) {			
		        
		        if(addresses==null || addresses.size()==0){
					Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
				}
		        
		        // Clears all the existing markers on the map
		        googleMap.clear();
				
		        // Adding Markers on Google Map for each matching address
				for(int i=0;i<addresses.size();i++){				
					
					Address address = (Address) addresses.get(i);
					
			        // Creating an instance of GeoPoint, to display in Google Map
			        latLng = new LatLng(address.getLatitude(), address.getLongitude());
			        
			        String addressText = String.format("%s, %s",
	                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
	                        address.getCountryName());

			        markerOptions = new MarkerOptions();
			        markerOptions.position(latLng);
			        markerOptions.title(addressText);
			        markerOptions.draggable(true);

			        googleMap.addMarker(markerOptions);
			        
			        // Locate the first location
			        if(i==0)			        	
						googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng)); 	
				}	
				
				//latLng = markerOptions.getPosition();
				
			}		
		}

		@Override
		public boolean onMarkerClick(Marker marker) {
			Intent intent = new Intent();
			latLng = markerOptions.getPosition();
			intent.putExtra("Lat", latLng.latitude);
			intent.putExtra("Lng", latLng.longitude);
			setResult(RESULT_OK, intent);
			Log.e("Coordinates", "Another string");
			finish();
			return true;
		}
}
