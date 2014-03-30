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

import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import ca.cs.ualberta.localpost.controller.TabsPagerAdapter;
import ca.cs.ualberta.localpost.model.StandardUserModel;

import com.google.gson.Gson;

/**
 * Main code was provided from Android Hive. Code was changed to fix
 * project requirements.
 * @author Team 01
 *
 */
//Some code was provided from:
//http://www.androidhive.info/2013/10/android-tab-layout-with-swipeable-views-1/

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, Observer {
	
	/**Adapters used to create the tabbed views/fragments */
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Fresh", "Lastest", "Greatest" };
	
	//public Bitmap picture;
	/**Creates a model object of StandardUserModel.class */
	private StandardUserModel standardUser;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		Intent intent = new Intent(this,ThreadView.class);
//		startActivity(intent);

		/**Generate The Tab Views*/
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

/*		*//**Location methods *//*
		LocationManager locationManager = (LocationManager) MainActivity.this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);*/
		
		

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**Adds the following items to action bar icons 
	 * 
	 * @param item	Takes in a menu item
	 * @return Returns a action bar with new comment and view user profile buttons.
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addNewComment:
			try {
				standardUser = StandardUserModel.getInstance();
				SharedPreferences app_preferences = getApplicationContext().getSharedPreferences("PREF", MODE_PRIVATE);
				String getUsername = app_preferences.getString("username", "anonymous");
				standardUser.setUsername(getUsername);
				GPSLocation gpsLocation = new GPSLocation(getApplicationContext());
				Address address = gpsLocation.getAddress();
				standardUser.setAddress(address);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Intent myIntent = new Intent(getApplicationContext(),SubmitComment.class);
			myIntent.putExtra("username", standardUser.getUsername());
			Gson gson = new Gson();
			// Serialize address object into string to send over to the activity
			String address = gson.toJson(standardUser.getAddress());
			myIntent.putExtra("location", address);
			startActivity(myIntent);
			return true;
		case R.id.viewUserProfile:
			Intent myIntent2 = new Intent(getApplicationContext(),UserProfile.class);
			startActivity(myIntent2);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
	}
}
