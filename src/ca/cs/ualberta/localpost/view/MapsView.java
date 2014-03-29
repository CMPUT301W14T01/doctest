/**
 * 
 */
package ca.cs.ualberta.localpost.view;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MapsView extends FragmentActivity implements OnCameraChangeListener, OnInfoWindowClickListener{
	
	/** Source: http://wptrafficanalyzer.in/blog/android-geocoding-showing-user-input-location-on-google-map-android-api-v2/
	 * 	and Google Play Services demos
	 * **/
	
	private GoogleMap googleMap;
	private LatLng latLng;
	private Address address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_view);
		
		// If map is not already setUp, then set it up
		setUpMapIfNeeded();		
		
		// Initialize the marker
		initMarker();
		
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
				
				// Minimize the keyboard after the 'Find' button is pressed
				// Source: http://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
			}
		};
		
		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);		
	}

	/**
	 * Initialize the marker when the map view is opened
	 */
	private void initMarker() {
		LocationManager service = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        String provider = getBestProvider(service);
        Location location = service.getLastKnownLocation(provider);
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        
    	googleMap.clear();
        
        Marker googleMarker = googleMap.addMarker(new MarkerOptions()
											.position(latLng)
											.title("I'm here")
											.draggable(true)
											.visible(true));

        googleMarker.showInfoWindow();
                
		// Pointing map view at my current location
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
	
		// Get an address for current marker
		List<Address> addresses = addresses(1);
		address = (Address) addresses.get(0);
	}
	
	
	/**
	 * Method that gets the address of the marker the user clicked on
	 * @param listener
	 * @see com.google.android.gms.maps.GoogleMap#setOnMarkerClickListener(com.google.android.gms.maps.GoogleMap.OnMarkerClickListener)
	 */
	public final void setOnMarkerClickListener(OnMarkerClickListener listener) {
		googleMap.clear();
		googleMap.addMarker(new MarkerOptions()
											.position(latLng)
											.title("Clicking worked")
											.draggable(true)
											.visible(true)).showInfoWindow();
		
		// Get address from the marker that the user clicked
		List<Address> addresses = addresses(1);
		address = (Address) addresses.get(0);
	}

	/**
	 * Function that creates a list of addresses based on search query location 
	 * @return a list of i addresses
	 */
	private List<Address> addresses(int numberOfAddresses) {
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		List<Address> addresses = null;
		try {
			addresses = geocoder.getFromLocation(latLng.latitude,
					latLng.longitude, numberOfAddresses);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addresses;
	}

	/**
	 * Method that returns the address of the marker after the user moved it
	 * @param listener
	 * @see com.google.android.gms.maps.GoogleMap#setOnMarkerDragListener(com.google.android.gms.maps.GoogleMap.OnMarkerDragListener)
	 */
	public final void setOnMarkerDragListener(OnMarkerDragListener listener) {
		googleMap.setOnMarkerDragListener(listener);
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
				
		        int i;
		        // Adding Markers on Google Map for each matching address
				for(i = 0;i<addresses.size();i++){				
					
					address = (Address) addresses.get(i);
					
			        // Creating an instance of GeoPoint, to display in Google Map
			        latLng = new LatLng(address.getLatitude(), address.getLongitude());
			        
			        String addressText = String.format("%s, %s",
	                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
	                        address.getCountryName());
			        
			        googleMap.addMarker(new MarkerOptions().position(latLng)
			        		.title(addressText).draggable(true).visible(true)).showInfoWindow();			        		        
				}
				
				// If search return one location, zoom to it, else have just a view of the map
		        if(i==1) {
		        	// Zooms camera with animation to the highest possible zoom at the current map location
					googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
		        }
		        else {
		        	googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
		        }
			}		
		}
		
		@Override
	    protected void onResume() {
	        super.onResume();
	        setUpMapIfNeeded();
	    }
		
		@Override
	    public void onPause() {
	        super.onPause();
	    }
		
		private void setUpMapIfNeeded() {
	        // Do a null check to confirm that we have not already instantiated the map.
	        if (googleMap == null) {
	            // Try to obtain the map from the SupportMapFragment.
	            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView))
	                    .getMap();
	            // Check if we were successful in obtaining the map.
	            if (googleMap != null) {
	                setUpMap();
	            }
	        }
	    }
		
		 /**
	     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	     * just add a marker near Africa.
	     * <p>
	     * This should only be called once and when we are sure that {@link #mMap} is not null.
	     */
	    private void setUpMap() {			

	        // Setting the click event listener for the marker
			googleMap.setMyLocationEnabled(true);
			//googleMap.setOnCameraChangeListener(this);
			googleMap.setOnInfoWindowClickListener(this);
	    }

	    /**
	     * Not yet implemented as the use for it has not been decided upon
	     */
		@Override
		public void onCameraChange(CameraPosition position) {
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(googleMap.getMinZoomLevel()));			
		}	
		
		@Override
		public void onInfoWindowClick(Marker marker) {
			Intent returnIntent = new Intent();
			Gson gson = new Gson();
			String string = gson.toJson(address);
			returnIntent.putExtra("address", string);
			setResult(RESULT_OK, returnIntent);
			//Log.e("Coordinates", string);
			alertDialog();			
		}
		
		/**
		 * Dialog box to determine whether the user is sure about the selection
		 */
		private void alertDialog() {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setMessage(R.string.dialog_message)
			       .setTitle(R.string.dialog_title)
			       .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               finish();
		           }})
		           .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   // Close dialog
		        	   dialog.cancel();
		           }});
			
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		/**
		 * Method that finds a good supplier
		 * Source: http://proquest.safaribooksonline.com.login.ezproxy.library.ualberta.ca/book/programming/android/9780132776622/9dot-determining-locations-and-using-maps/ch09?query=((android+google+maps))&reader=html&imagepage=#X2ludGVybmFsX0h0bWxWaWV3P3htbGlkPTk3ODAxMzI3NzY2MjIlMkZjaDA5bGV2MXNlYzEmcXVlcnk9KChhbmRyb2lkJTIwZ29vZ2xlJTIwbWFwcykp
		 * @param locationManager
		 * @return the location manager with the best supplier
		 */
		private String getBestProvider(LocationManager locationManager){
		    Criteria criteria = new Criteria();
		    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		    criteria.setPowerRequirement(Criteria.POWER_LOW);
		    criteria.setCostAllowed(false);
		return locationManager.getBestProvider(criteria, true);
		}
}
