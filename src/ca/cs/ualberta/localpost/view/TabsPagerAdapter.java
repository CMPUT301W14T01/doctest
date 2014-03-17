package ca.cs.ualberta.localpost.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Adapter for Fragments(Tab Views)
 * Code Skeleton was provided by Android Hive.
 * @author Team 01
 */

/*
 * Source:
 * http://www.androidhive.info/2013/10/android-tab-layout-with-swipeable-views-1/
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager frag){
		super(frag);
	}

	/**
	 * Creates Fragments and links them wtih appropriate .View file
	 */
	@Override
	public Fragment getItem(int arg0) {
		switch(arg0){
		case 0:
			return new FreshestTabView();
		case 1:
			return new GreatestTabView();
		case 2:
			return new LastestTabView();
		}
		return null;
	}
	
	/**
	 * Returns the number of tabs
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
}
