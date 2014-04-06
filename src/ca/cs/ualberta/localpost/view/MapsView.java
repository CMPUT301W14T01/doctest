/**
 * 
 */
package ca.cs.ualberta.localpost.view;
import java.io.IOException;
import java.io.Serializable;
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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ca.cs.ualberta.localpost.controller.Serialize;
import ca.cs.ualberta.localpost.model.CommentModel;
import ca.cs.ualberta.localpost.model.RootCommentModel;
import ca.cs.ualberta.localpost.model.StandardUserModel;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MapsView extends FragmentActivity implements OnInfoWindowClickListener, OnMarkerDragListener, OnMapClickListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Source: http://wptrafficanalyzer.in/blog/android-geocoding-showing-user-input-location-on-google-map-android-api-v2/
	 * 	and Google Play Services demos
	 * **/

	private GoogleMap googleMap;
	private Marker googleMarker;
	private LatLng latLng;
	private Address address;
	StandardUserModel user;
	private ArrayList<CommentModel> commentThread;
	private RootCommentModel commentModel;

	private String MAP_VIEW_TYPE = "mapviewtype";
	private String THREAD_VIEW = "threadview";
	private String SUBMIT_VIEW = "submitvew";
	private String EDIT_COMMENT_VIEW = "editview";
	private String EDIT_USER_LOCATION_VIEW = "userlocationview";
	private String EDIT_COMMENT_MODEL = "editcomment";
	private String THREAD_COMMENT_MODEL = "threadcommentmodel";
	String INTENT_OBJECT;
	private String INTENT_PURPOSE;

	Gson gson = new Gson();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps_view);

		// If map is not already setUp, then set it up
		setUpMapIfNeeded();		

		// Initialize the marker
		// TODO Change to make it display the new user selected location
		try {
			user = Serialize.loaduser(getApplicationContext());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Intent extrasData = getIntent();

		INTENT_PURPOSE = extrasData.getStringExtra(MAP_VIEW_TYPE);

		// The following checks conditions to determine how to display the map
		// depending on where the intent was sent from and with what purpose
		if (INTENT_PURPOSE.equals(THREAD_VIEW)){
			// Retrieve the comment model passed through intent
			INTENT_OBJECT = extrasData.getStringExtra(THREAD_COMMENT_MODEL);

			// Convert GSON string to ArrayList Type
			ArrayList<String> arrayComments =
					gson.fromJson(INTENT_OBJECT, new TypeToken<ArrayList<String>>(){}.getType());

			Log.e("Pass4", "Got here");
			Log.e("Pass5", String.valueOf(arrayComments.size()));

			//			RootCommentModel fromJson1 = gson.fromJson(arrayComments.get(0), RootCommentModel.class);
			//			Log.e("Pass6", String.valueOf(fromJson1.getAddress().getAddressLine(0)));
			//			Log.e("Pass7", arrayComments.get(0));
			//			Log.e("Pass8", String.valueOf(fromJson1.getAddress()));
			//			RootCommentModel fromJson = gson.fromJson(arrayComments.get(0), RootCommentModel.class);
			//					
			//			Log.e("Pass9", fromJson.getAuthor());
			markerThreadView(arrayComments);
		}
		else if (INTENT_PURPOSE.equals(SUBMIT_VIEW) || INTENT_PURPOSE.equals(EDIT_USER_LOCATION_VIEW)) {
			// Set address to the users default location
			address = user.getAddress();

			// Set coordinates to the users default location
			latLng = new LatLng(address.getLatitude(), address.getLongitude());

			commentMarker();
		}
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

	private void markerThreadView(ArrayList<String> commentThread) {
		googleMap.clear();

		//		ArrayList<String> thread = commentThread.getChildren();
		//		List<Marker> markers = new ArrayList<Marker>();

		Log.e("Thread", "It works bitches");
		//		//Log.e("Thread Author", commentThread.get(0).getAuthor());
		//		
		//		Iterator<CommentModel> it = commentThread.iterator();
		//		
		//		while(it.hasNext()){
		//			CommentModel model = it.next();
		//			Log.e("Model title", model.getTitle());
		//		}

		RootCommentModel fromJson = gson.fromJson(commentThread.get(0), RootCommentModel.class);

		Log.e("Pass6", String.valueOf(fromJson.getAddress().getAddressLine(0)));

		int i;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm MM/dd/yy");
		//		ArrayList<GoogleMap> mapThread = new ArrayList<GoogleMap>();
		//		ArrayList<MarkerOptions> markerOpt = new ArrayList<MarkerOptions>();
		final List<Marker> mMarker = new ArrayList<Marker>();
		latLng = new LatLng(fromJson.getAddress().getLatitude(),fromJson.getAddress().getLongitude());
		//		LatLngBounds bounds = new LatLngBounds.Builder().include(latLng).build();

		LatLngBounds.Builder mapBounds = LatLngBounds.builder();
		mapBounds = mapBounds.include(latLng);
//		final View mapView = getSupportFragmentManager().findFragmentById(R.id.mapView).getView();
//		if (mapView.getViewTreeObserver().isAlive()) {
			for(i = 1;i<commentThread.size();i++){				

				RootCommentModel comment =  gson.fromJson(commentThread.get(i), RootCommentModel.class);

				address = comment.getAddress();
				//Log.e("Pass7", String.valueOf(comment.getAddress().getAddressLine(0)));
				// Creating an instance of GeoPoint, to display in Google Map
				latLng = new LatLng(address.getLatitude(), address.getLongitude());
				//
				//			String addressText = String.format("%s, %s",
				//					address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
				//							address.getCountryName());
				//
				//			bounds.including(latLng);

				mMarker.add(googleMap.addMarker(new MarkerOptions().position(latLng)
						.title(comment.getAuthor() + " @ " + format.format(new Date(comment.getTimestamp())))));

//				mapBounds = mapBounds.include(latLng);

				//			mapThread.add(googleMap);
			}
//			googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mapBounds.build(), 50));
//		}
		//		googleMap.clear();
		//		
		for (Marker markers: mMarker){
			markers.showInfoWindow();
		}
	}

	/**
	 * Add markers to map
	 */
	private void addMarkersToMap(LatLngBounds bounds) {
		// Pan to see all markers in view.
		// Cannot zoom to bounds until the map has a size.
		final View mapView = getSupportFragmentManager().findFragmentById(R.id.mapView).getView();
		if (mapView.getViewTreeObserver().isAlive()) {
			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation") // We use the new method when supported
				@SuppressLint("NewApi") // We check which build version we are using.
				@Override
				public void onGlobalLayout() {
					LatLngBounds bounds = new LatLngBounds.Builder()
					//					.include(PERTH)
					//					.include(SYDNEY)
					//					.include(ADELAIDE)
					//					.include(BRISBANE)
					//					.include(MELBOURNE)
					.build();
					if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
						mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					} else {
						mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
					googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
				}
			});
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
	 * Method that finds a good supplier
	 * Source: http://proquest.safaribooksonline.com.login.ezproxy.library.ualberta.ca/book/programming/android/9780132776622/9dot-determining-locations-and-using-maps/ch09?query=((android+google+maps))&reader=html&imagepage=#X2ludGVybmFsX0h0bWxWaWV3P3htbGlkPTk3ODAxMzI3NzY2MjIlMkZjaDA5bGV2MXNlYzEmcXVlcnk9KChhbmRyb2lkJTIwZ29vZ2xlJTIwbWFwcykp
	 * @param locationManager
	 * @return the location manager with the best supplier
	 */
	//	private String getBestProvider(LocationManager locationManager){
	//		Criteria criteria = new Criteria();
	//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
	//		criteria.setPowerRequirement(Criteria.POWER_LOW);
	//		criteria.setCostAllowed(false);
	//		return locationManager.getBestProvider(criteria, true);
	//	}

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
		//Log.e("Coordinates", string);
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

	@Override
	public void onMapClick(LatLng point) {
		googleMarker.showInfoWindow();		
	}
}
