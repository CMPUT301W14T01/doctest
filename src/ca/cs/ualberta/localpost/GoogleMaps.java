package ca.cs.ualberta.localpost;

/**
 * Demo Code from: http://www.cs.sfu.ca/CourseCentral/276/bfraser/androidExampleCode/AndroidGoogleMapsAPIDemo.zip
 * **/

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import cs.ca.ualberta.localpost.R;

public class GoogleMaps extends FragmentActivity {

	//private final LatLng LOCATION_BURNABY = new LatLng(49.27645, -122.917587);
	//private final LatLng LOCATION_SURRREY = new LatLng(49.187500, -122.849000);
	
	//private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_main);
		
		//map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		//map.addMarker(new MarkerOptions().position(LOCATION_SURRREY).title("Find me here!"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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