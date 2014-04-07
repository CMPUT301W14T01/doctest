/**
 * The MIT License (MIT)
 * Copyright (c) 2014 Timotei Albu, David Chau-Tran, Alain Clark, Shawn Anderson, Mickael Zerihoun
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *	this software and associated documentation files (the "Software"), to deal in
 *	the Software without restriction, including without limitation the rights to
 *	use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 *	the Software, and to permit persons to whom the Software is furnished to do so,
 *	subject to the following conditions:
 *	
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *	
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *  
 */

package ca.cs.ualberta.localpost.view;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * MapsView is a class that communicates with several other views to display users or comments location on map
 * Called when user submits or edits a comment; when user location is changed and when the map thread option is selected
 * @author team01
 *
 */
public class MapsView extends FragmentActivity implements OnInfoWindowClickListener, OnMarkerDragListener {

	// Google map object
	private GoogleMap googleMap;
	// Google marker to display on map
	private Marker googleMarker;
	// Coordinates based on latitude and longitude
	private LatLng latLng;
	// Address object containing all info about a certain point on map
	private Address address;
	// Model of the user
	private StandardUserModel user;
	// Array containing all comments
	private ArrayList<String> arrayComments;
	private RootCommentModel commentModel;

	// Variables used for receiving/reading intent from different applications using Google Maps
	private String MAP_VIEW_TYPE = "mapviewtype";
	private String THREAD_VIEW = "threadview";
	private String SUBMIT_VIEW = "submitvew";
	private String EDIT_COMMENT_VIEW = "editview";
	private String EDIT_USER_LOCATION_VIEW = "userlocationview";
	private String EDIT_COMMENT_MODEL = "editcomment";
	private String THREAD_COMMENT_MODEL = "threadcommentmodel";
	private String INTENT_OBJECT;
	private String INTENT_PURPOSE;

	Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_view);

		// If map is not already setUp, then set it up
		setUpMapIfNeeded();		

		// Load current user
		try {
			user = Serialize.loaduser(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Receive intent
		Intent extrasData = getIntent();
		INTENT_PURPOSE = extrasData.getStringExtra(MAP_VIEW_TYPE);

		// The following checks conditions to determine how to display the map
		// depending on where the intent was sent from and with what purpose
		//
		// Thread View
		if (INTENT_PURPOSE.equals(THREAD_VIEW)){
			// Retrieve the comment model passed through intent
			INTENT_OBJECT = extrasData.getStringExtra(THREAD_COMMENT_MODEL);

			// Convert GSON string to ArrayList Type
			arrayComments =
					gson.fromJson(INTENT_OBJECT, new TypeToken<ArrayList<String>>(){}.getType());

			markerThreadView(arrayComments);
		}
		// Evaluates true when submitting a comment
		else if (INTENT_PURPOSE.equals(SUBMIT_VIEW) || INTENT_PURPOSE.equals(EDIT_USER_LOCATION_VIEW)) {
			// Set address to the users default location
			address = user.getAddress();

			// Set coordinates to the users default location
			latLng = new LatLng(address.getLatitude(), address.getLongitude());

			commentMarker();
		}
		// Evaluates true when editing a comment
		else if (INTENT_PURPOSE.equals(EDIT_COMMENT_VIEW)) {
			// Retrieve the comment model passed through intent
			INTENT_OBJECT = extrasData.getStringExtra(EDIT_COMMENT_MODEL);

			// Convert the gson string to a Root Comment Model
			commentModel = gson.fromJson(INTENT_OBJECT, RootCommentModel.class);

			// Open the map at the location where the comment was made
			latLng = new LatLng(commentModel.getAddress().getLatitude(), commentModel.getAddress().getLongitude());
			address = commentModel.getAddress();		
			commentMarker();
		}

		// Getting reference to btn_find of the layout maps_view
		Button btn_find = (Button) findViewById(R.id.btn_find);

		// Defining button click event listener for the find button
		OnClickListener findClickListener = new FindButton();

		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);		
	}	

	/**
	 * Arraylist of comments are sent to be displayed as markers on map
	 * @param commentThread
	 */
	@SuppressLint("SimpleDateFormat")
	private void markerThreadView(ArrayList<String> commentThread) {
		// Counter var and a date format one
		int i;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm MM/dd/yy");

		// Clear all markers on map
		googleMap.clear();

		RootCommentModel fromJson = gson.fromJson(commentThread.get(0), RootCommentModel.class);

		// ArrayList of markers
		final List<Marker> mMarker = new ArrayList<Marker>();
		latLng = new LatLng(fromJson.getAddress().getLatitude(),fromJson.getAddress().getLongitude());

		// Store the location of the Top level comment for zooming purposes
		LatLng rootLocation = latLng;

		mMarker.add(googleMap.addMarker(new MarkerOptions()
		.position(latLng)
		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
		.title(fromJson.getContent() + " @ " + format.format(new Date(fromJson.getTimestamp())))));


		LatLngBounds.Builder mapBounds = LatLngBounds.builder();
		mapBounds = mapBounds.include(rootLocation);

		for(i = 1;i<commentThread.size();i++){				

			RootCommentModel comment =  gson.fromJson(commentThread.get(i), RootCommentModel.class);

			address = comment.getAddress();

			// Creating an instance of GeoPoint, to display in Google Map
			latLng = new LatLng(address.getLatitude(), address.getLongitude());

			mapBounds.include(latLng);

			mMarker.add(googleMap.addMarker(new MarkerOptions()
			.position(latLng)
			.title(comment.getAuthor() + " @ " + format.format(new Date(comment.getTimestamp())))));

		}
		LatLngBounds bounds = mapBounds.build();

		// Calculates where all markers would be and positions camera appropriately.
		// Source: http://stackoverflow.com/a/17825157
		try{
			googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50)); //This line will cause the exception first times when map is still not "inflated"
			System.out.println("Set with padding");
		} catch(IllegalStateException e) {
			e.printStackTrace();
			googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 400, 400, 0));
			System.out.println("Set with wh");
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

	/**
	 * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	 * just add a marker near Africa.
	 * <p>
	 * This should only be called once and when we are sure that {@link #mMap} is not null.
	 */
	private void setUpMap() {	
		// Enable my location
		googleMap.setMyLocationEnabled(true);
		// Setting the click event listener for the marker
		googleMap.setOnInfoWindowClickListener(this);
		googleMap.setOnMarkerDragListener(this);
	}

	/**
	 * Method that instantiates the map if it hasn't been created yet
	 */
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
	 * Initialize the marker when the map view is opened
	 */
	private void commentMarker() {
		// Clear all marker currently on the map
		googleMap.clear();

		googleMarker = googleMap.addMarker(new MarkerOptions()
		.position(latLng)
		.title("I'm here @ " + address.getAddressLine(0))
		.draggable(true)
		.visible(true));

		googleMarker.showInfoWindow();

		// Pointing map view at my current location
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));
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
	 * Looks up a location
	 * @author timotei
	 *
	 */
	private final class FindButton implements OnClickListener {
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
	 * Method that passes an object of the address of the marker back to the previous activity, whose InfoWindow has been clicked		
	 */
	@Override
	public void onInfoWindowClick(Marker marker) {
		Intent returnIntent = new Intent();
		Gson gson = new Gson();
		String string = gson.toJson(address);
		returnIntent.putExtra("address", string);
		setResult(RESULT_OK, returnIntent);
		alertDialog();			
	}

	/**
	 * Called repeatedly while marker is being dragged
	 */
	@Override
	public void onMarkerDrag(Marker arg0) {
		// Do no thing
	}

	/**
	 * Called when a marker has finished being dragged
	 */
	@Override
	public void onMarkerDragEnd(Marker marker) {
		latLng = marker.getPosition();

		googleMap.clear();

		// Get an address for current marker
		List<Address> addresses = addresses(1);
		address = (Address) addresses.get(0);

		marker = googleMap.addMarker(new MarkerOptions()
		.position(latLng)
		.title("I'm here now @ " + address.getAddressLine(0))
		.draggable(true)
		.visible(true));

		marker.showInfoWindow();

		// Pointing map view at my current location
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f));

	}

	/**
	 * Called when a marker starts being dragged
	 */
	@Override
	public void onMarkerDragStart(Marker marker) {
		// Minimize the keyboard after the 'Find' button is pressed
		// Source: http://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
		InputMethodManager inputManager = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE); 

		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
