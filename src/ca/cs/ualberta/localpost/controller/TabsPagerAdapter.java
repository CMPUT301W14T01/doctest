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

package ca.cs.ualberta.localpost.controller;

import ca.cs.ualberta.localpost.view.FreshestTabView;
import ca.cs.ualberta.localpost.view.GreatestTabView;
import ca.cs.ualberta.localpost.view.LastestTabView;
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
	 * @param arg0 index for tab
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
